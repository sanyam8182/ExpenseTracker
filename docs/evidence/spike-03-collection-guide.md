# SPIKE-03 collection guide: bank alert samples

**Spike:** SPIKE-03, "Collect five-bank fixtures and define supported matrix" (Jira SCRUM-20). **Status:** waiting for your samples.
**Why:** the app only claims support for a bank, channel and message format that real samples prove. Without real alerts nobody can write or test a parser, and the Gate 3 targets (D-12) cannot be sized.

## 1. What I need

For each of the five banks you actually use (HDFC, SBI, SBM, Kotak, Axis) and each channel you actually receive (SMS, and the bank or payment app notification), a small set of real alerts. Start small: **5 to 10 per bank and channel** is enough for a first analysis. The Gate 3 plan later needs about 30 per combination, so keep collecting as alerts arrive.

Aim for variety, not volume. One of each kind is worth more than ten of the same:

| Kind | Examples |
| --- | --- |
| Purchase | UPI payment to a shop, card swipe, online card purchase, net banking payment |
| Person-to-person | UPI to a friend (purpose unclear) |
| Money in | Salary, refund, reversal, credit from a friend |
| Not an expense | Own-account transfer, card bill payment, ATM withdrawal, wallet top-up |
| Recurring | EMI or loan debit, SIP, insurance premium, subscription, auto-debit mandate |
| Trouble | Failed payment, pending payment, payment request or reminder |
| Other currency | A card purchase in USD or another currency |
| Noise | Balance alert, promotion, one-time password (see the rule below) |

## 2. Privacy rules (read first)

- **One-time passwords (OTPs): do not collect them.** If an OTP message is the only example of "noise", replace every digit of the code with `X` before it leaves your phone. The app will never store an OTP (RAW 01).
- **Blank out before sharing:** full account and card numbers (keep the last four digits only if the bank prints them), your name and other people's names, phone numbers, email addresses, UPI handles (`name@bank`), reference or transaction numbers (replace with `REF123456`, keep the length), and any balance you don't want known. Keep the **amount, currency, date, time and merchant name**, because the parser depends on them.
- **Keep the original wording and layout.** Do not retype or tidy the message. Only replace the sensitive parts.
- When the redaction tool exists (plan task 14, `tools/redact_alert.py`) run it on every file and then read the result yourself before sharing.
- Raw (unredacted) alerts stay on your computer in `docs/evidence/raw/`. That folder is ignored by git and must never be committed. Do not commit the redacted files either until you have looked at them, because this repository is public.

## 3. How to collect

**SMS:** open the message in Samsung Messages, long-press, choose copy, and paste into a text file. One alert per block, separated by a line with `---`. Include the **sender ID** (the name shown at the top, such as `AX-HDFCBK`) and the **date and time shown** on a line above the text.

**Notifications:** copy-paste does not work for notifications. Two options:
1. Take a screenshot of the notification (before swiping it away) and note the app name. I can read screenshots.
2. Better: once the SPIKE-04 test app (plan tasks 15 and 16) is installed it logs notifications as text, with sensitive parts already blanked.

**App package names:** these identify the app that posted a notification and must come from the real phone, not from display names. With the phone connected and USB debugging on, run in PowerShell:

```powershell
adb shell pm list packages | Select-String -Pattern "hdfc|sbi|yono|sbm|kotak|axis|gpay|google.android.apps.nbu|phonepe|paytm|bhim|amazon|cred|upi"
```

Send me the whole output. I will pick the real names; nothing is guessed (CAP 02).

## 4. File format

Create one file per bank and channel, for example `hdfc-sms.txt`, in `docs/evidence/raw/`:

```
bank: HDFC
channel: sms
sender: AX-HDFCBK
received: 2026-10-03 14:12
kind: purchase
what_i_know: spent at Zomato, it is a food order
---
(paste the message text here, redacted)
---
bank: HDFC
channel: sms
...
```

`kind` and `what_i_know` are your notes about what really happened. They are the ground truth I check the parser against. If you do not know, write `unknown`.

## 5. Ground truth for the capture-rate target

Gate 3 measures how many real alerts the app captured against a named source of truth. Please plan for one of these for the same weeks as your samples:

- a **bank statement** (PDF or CSV from the bank app) for the period, redacted the same way, or
- a log of every alert received, built from the test app.

Choose which one you prefer; I will record it in the evidence file.

## 6. Coverage table (I fill this in as samples arrive)

| Bank | SMS samples | Notification samples | Package names found | Kinds covered | Gaps |
| --- | --- | --- | --- | --- | --- |
| HDFC | | | | | |
| SBI | | | | | |
| SBM | | | | | |
| Kotak | | | | | |
| Axis | | | | | |

## 7. What happens next

1. You collect and redact samples and package names, then tell me where they are.
2. I produce the matrix: for each bank and channel, which fields (amount, currency, direction, merchant, masked account, reference, time) can be read, which formats exist, and what is unsupported (`docs/evidence/spike-03-matrix-<date>.md`).
3. I propose the Gate 3 numbers for D-12 (fixtures per combination, live days, capture-rate target) based on what really exists. You approve or change them. Nothing is claimed as supported until fixtures pass.

Unsupported banks, channels or formats are listed as unsupported. They are never counted as passed.
