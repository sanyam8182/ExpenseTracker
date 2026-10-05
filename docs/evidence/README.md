# Evidence

Gate 0 spikes decide what the app is allowed to do (for example, which Google permissions it needs, or whether bank alerts can be read on Samsung). Each result is written down here so the decision can be checked later.

## Rules

- **One file per spike run**, named `SPIKE-NN-short-name-YYYY-MM-DD.md`, copied from `_template.md`. A repeat run is a new dated file, never an edit of an old one.
- **Record what happened, not what was expected.** If a step failed or behaved oddly, say so. A "pass" needs the steps and observations that show it.
- **Name the exact device, Android build and app build.** Samsung behaviour changes between One UI versions.
- **Result is one of:** `pass`, `pass with limits` (say what the limits are), `blocked` (say what unblocks it), or `fail`.
- **This repository is public.** Never commit real account numbers, names, phone numbers, OTPs, UPI handles, email addresses, tokens or screenshots showing them. Run alert text through `tools/redact_alert.py` and read it again.
- **Raw material stays local.** `docs/evidence/raw/` and `docs/evidence/fixtures/` are ignored by git on purpose.
- After a run, update the decision or Jira issue that depends on it, and link the evidence file.

## Files

- `_template.md` - copy this for each run.
- `spike-03-collection-guide.md` - how to collect and redact bank alert samples.
