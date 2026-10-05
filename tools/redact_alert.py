"""Blank out personal details in bank alert text before it is shared (SPIKE-03).

Keeps amounts, dates, times and merchant names, because the parser depends on them.
It is a safety net, not a guarantee: names inside free text are only caught after a
greeting or a title, so read every output file yourself before sharing it.

Usage: python redact_alert.py alerts.txt [more files]   ->  alerts.redacted.txt
"""
import re
import sys
from collections import Counter
from pathlib import Path

OTP_WORDS = re.compile(r"\b(otp|one[- ]time|verification code|passcode)\b", re.IGNORECASE)
CURRENCY_BEFORE = re.compile(r"(rs\.?|inr|₹)\s*$", re.IGNORECASE)
CODE = re.compile(r"(?<![\dX,.])\d{4,8}(?![\d,]|\.\d)")
MOBILE = re.compile(r"(?<!\d)(?:\+?91[\s-]?|0)?[6-9]\d{4}[\s-]?\d{5}(?!\d)")
LONG_NUMBER = re.compile(r"(?<!\d)\d{10,16}(?!\d)")
EMAIL = re.compile(r"[\w.+-]+@[\w-]+(?:\.[\w-]+)+")
UPI_HANDLE = re.compile(r"[\w.\-]{2,}@[A-Za-z]{2,}\b(?!\.\w)")
NOT_A_NAME = r"(?!(?:Customer|CUSTOMER|Sir|Madam)\b)"
NAME_WORDS = r"[A-Z][a-z]+(?:\s[A-Z][a-z]+)*"
GREETING = re.compile(rf"\b(Dear|Hi|Hello)\s+{NOT_A_NAME}{NAME_WORDS}")
TITLE = re.compile(rf"\b(Mr\.?|Mrs\.?|Ms\.?|Shri|Smt\.?)\s+{NOT_A_NAME}{NAME_WORDS}")


def redact_with_report(text: str) -> tuple[str, Counter]:
    report: Counter = Counter()

    def count(kind: str, replacement: str):
        def sub(_match: re.Match) -> str:
            report[kind] += 1
            return replacement

        return sub

    def mask_long(match: re.Match) -> str:
        report["long_number"] += 1
        digits = match.group(0)
        return "X" * (len(digits) - 4) + digits[-4:]

    def mask_code(match: re.Match) -> str:
        if CURRENCY_BEFORE.search(match.string[: match.start()]):
            return match.group(0)
        report["otp"] += 1
        return "X" * len(match.group(0))

    text = MOBILE.sub(count("mobile", "XXXXXXXXXX"), text)
    text = LONG_NUMBER.sub(mask_long, text)
    if OTP_WORDS.search(text):
        text = CODE.sub(mask_code, text)
    text = EMAIL.sub(count("email", "redacted@example.com"), text)
    text = UPI_HANDLE.sub(count("upi", "REDACTED@upi"), text)
    text = GREETING.sub(lambda m: _name(report, m.group(1)), text)
    text = TITLE.sub(lambda m: _name(report, m.group(1)), text)
    return text, report


def _name(report: Counter, lead: str) -> str:
    report["name"] += 1
    return f"{lead} CUSTOMER"


def redact(text: str) -> str:
    return redact_with_report(text)[0]


def main(paths: list[str]) -> int:
    if not paths:
        print(__doc__)
        return 2
    for name in paths:
        source = Path(name)
        text, report = redact_with_report(source.read_text(encoding="utf-8"))
        target = source.with_name(f"{source.stem}.redacted{source.suffix}")
        target.write_text(text, encoding="utf-8")
        summary = ", ".join(f"{kind}: {n}" for kind, n in sorted(report.items())) or "nothing changed"
        print(f"{source.name} -> {target.name} ({summary}). Read it before sharing.")
    return 0


if __name__ == "__main__":
    sys.exit(main(sys.argv[1:]))
