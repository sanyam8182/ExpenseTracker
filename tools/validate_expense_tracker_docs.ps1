$ErrorActionPreference = 'Stop'

$root = Split-Path -Parent $PSScriptRoot
$requirementsPath = Join-Path $root 'Expense_Tracker_Requirements.md'
$backlogPath = Join-Path $root 'Expense_Tracker_Jira_Backlog.md'
$requirements = Get-Content -Raw -Encoding UTF8 $requirementsPath
$backlog = Get-Content -Raw -Encoding UTF8 $backlogPath
$errors = [System.Collections.Generic.List[string]]::new()
$emDash = [char]0x2014

function Require([bool]$condition, [string]$message) {
    if (-not $condition) { $errors.Add($message) }
}

Require ($requirements -match 'Version 0\.2') 'Requirements must declare version 0.2.'
Require ($requirements -match 'canonical product source of truth') 'Requirements must declare canonical authority.'
Require ($backlog -match 'Expense_Tracker_Requirements\.md` is canonical') 'Backlog must point to the Markdown authority.'
Require ($requirements -match '\| ID \| Decision in v0\.2 \| Approval status \| Disposition \|') 'Decision register needs an Approval status column.'
Require ($requirements -match '## 8\. Decision applicability and deferred scope') 'Requirements must include the decision-applicability matrix.'
Require ($requirements -match 'same-owner recovery') 'Requirements must define same-owner recovery before the blocked owner-phone state.'
# Approval status comes from the decision register; everything else must agree with it.
$registerStatus = @{}
foreach ($rm in [regex]::Matches($requirements, '(?m)^\| (D-\d+) \| .*? \| \*\*(Approved by user|Proposed)')) { $registerStatus[$rm.Groups[1].Value] = $rm.Groups[2].Value }
Require ($registerStatus.Count -ge 11) 'Decision register must list D-01 through D-11.'
Require ($registerStatus['D-01'] -eq 'Approved by user') 'D-01 must be explicitly approved.'
$approvedIds = @($registerStatus.Keys | Where-Object { $registerStatus[$_] -eq 'Approved by user' } | Sort-Object)
$proposedIds = @($registerStatus.Keys | Where-Object { $registerStatus[$_] -eq 'Proposed' } | Sort-Object)
$stated = [regex]::Match($backlog, 'Only (D-\d+(?:(?:, | and )D-\d+)*) (?:is|are) approved by the user')
Require $stated.Success 'Backlog must state which decisions are approved.'
if ($stated.Success) {
    $statedIds = @([regex]::Matches($stated.Groups[1].Value, 'D-\d+') | ForEach-Object { $_.Value } | Sort-Object)
    Require (($statedIds -join ',') -eq ($approvedIds -join ',')) "Backlog approved decisions ($($statedIds -join ', ')) do not match the register ($($approvedIds -join ', '))."
}

$requirementAcIds = [regex]::Matches($requirements, '(?m)^\| AC (\d+) \|') | ForEach-Object { [int]$_.Groups[1].Value } | Sort-Object -Unique
$backlogMatrixAcIds = [regex]::Matches($backlog, '(?m)^\| AC (\d+) \|') | ForEach-Object { [int]$_.Groups[1].Value } | Sort-Object -Unique
Require ($requirementAcIds.Count -eq 76 -and $requirementAcIds[0] -eq 1 -and $requirementAcIds[-1] -eq 76) 'Requirements must contain AC 01 through AC 76 exactly once in the acceptance table.'
Require ($backlogMatrixAcIds.Count -eq 76 -and $backlogMatrixAcIds[0] -eq 1 -and $backlogMatrixAcIds[-1] -eq 76) 'Backlog coverage matrix must contain AC 01 through AC 76.'

$us021 = [regex]::Match($backlog, '(?ms)^### US-021\s+\p{Pd}.*?(?=^### |^## |\z)').Value
foreach ($id in @('TASK-109','US-006','US-009','US-010','US-013','US-015','US-020','US-022','US-023')) {
    Require ($us021 -match [regex]::Escape($id)) "US-021 must be blocked by $id."
}
Require ($us021 -match 'AC 75' -and $us021 -match 'AC 76') 'US-021 must include outlier and Gate 3 ground-truth criteria.'
Require ($backlog -match 'US-012\s+\p{Pd}[\s\S]*?Gate 1 \+ Gate 2 \(mixed\)[\s\S]*?AC 01 \[Gate 2\][\s\S]*?AC 39 \[Gate 1\]') 'US-012 must show mixed-gate labels beside its acceptance criteria.'
Require ($backlog -match 'SPIKE-03\s+\p{Pd}[\s\S]*?Delivery order:\*\* 0') 'SPIKE-03 must be first in the delivery order.'
Require (-not ($backlog -match '(?m)^\*\*Priority:\*\*')) 'Priorities must remain unset; use Delivery order for sequencing.'
Require ($backlog -match 'A mixed story closes only at its last gate, Gate 2') 'Mixed-story completion must be defined as closing at Gate 2.'
Require ($backlog -match 'Split-criterion ownership is explicit') 'Split-criterion ownership note is missing.'

$indexSection = [regex]::Match($backlog, '(?ms)^## Issue index\s*(.*?)^## Issue definitions').Groups[1].Value
$indexMatches = [regex]::Matches($indexSection, '(?m)^\| ([A-Z]+-\d+) \| (Epic|Story|Task|Subtask) \| ([^|]+) \|')
$issues = @{}
foreach ($m in $indexMatches) {
    $issues[$m.Groups[1].Value] = [pscustomobject]@{ Type = $m.Groups[2].Value; Parent = $m.Groups[3].Value.Trim() }
}
Require ($issues.Count -eq 58) 'Issue index must contain all 58 planned issues.'
$decisionFieldSection = [regex]::Match($backlog, '(?ms)^## Decision field map\s*(.*)$').Groups[1].Value
$decisionFieldRows = [regex]::Matches($decisionFieldSection, '(?m)^\| ((?:EPIC|SPIKE|TASK|US|SUB)-\d+) \| ([^|]+) \|')
$decisionFieldIds = @($decisionFieldRows | ForEach-Object { $_.Groups[1].Value })
Require ($decisionFieldIds.Count -eq 58 -and (@($decisionFieldIds | Sort-Object -Unique).Count -eq 58)) 'Decision field map must contain exactly one row for every issue.'
foreach ($id in $issues.Keys) { Require ($decisionFieldIds -contains $id) "$id is missing from the Decision field map." }

foreach ($id in $issues.Keys) {
    $meta = $issues[$id]
    if ($meta.Type -eq 'Epic') {
        Require ($meta.Parent -in @($emDash,'None','-')) "$id must not have a parent."
    } elseif ($meta.Type -in @('Story','Task')) {
        Require ($meta.Parent -match '^EPIC-\d+$') "$id must have an Epic parent."
        Require $issues.ContainsKey($meta.Parent) "$id parent $($meta.Parent) is missing."
        if ($issues.ContainsKey($meta.Parent)) { Require ($issues[$meta.Parent].Type -eq 'Epic') "$id parent $($meta.Parent) must be an Epic." }
    } elseif ($meta.Type -eq 'Subtask') {
        Require ($meta.Parent -match '^US-\d+$') "$id must have a Story parent."
        Require $issues.ContainsKey($meta.Parent) "$id parent $($meta.Parent) is missing."
        if ($issues.ContainsKey($meta.Parent)) { Require ($issues[$meta.Parent].Type -eq 'Story') "$id parent $($meta.Parent) must be a Story." }
    }
}

$edges = @{}
foreach ($id in $issues.Keys) {
    $block = [regex]::Match($backlog, "(?ms)^(?:##|###|####) $([regex]::Escape($id))\s+\p{Pd}.*?(?=^(?:##|###|####) [A-Z]+-\d+\s+\p{Pd}|\z)").Value
    Require ($block.Length -gt 0) "$id issue definition is missing."
    $parentLine = [regex]::Match($block, '(?m)^\*\*Parent:\*\* ([^\r\n]+)').Groups[1].Value.Trim()
    if ($parentLine -eq 'None') { $parentLine = $emDash }
    Require ($parentLine -eq $issues[$id].Parent) "$id definition parent does not match the issue index."
    $sourceLine = [regex]::Match($block, '(?m)^\*\*Source requirements:\*\* ([^\r\n]+)').Groups[1].Value
    foreach ($ref in ([regex]::Matches($sourceLine, '[A-Z]{2,5} \d{2}') | ForEach-Object { $_.Value })) {
        Require ($requirements -match "(?m)^### .*\b$([regex]::Escape($ref))\b") "$id references missing requirement $ref."
    }
    $blockedLine = [regex]::Match($block, '(?m)^\*\*Blocked by:\*\* ([^\r\n]+)').Groups[1].Value.Trim()
    $deps = @()
    if ($blockedLine -and $blockedLine -ne 'None') {
        $deps = $blockedLine.Split(',') | ForEach-Object { $_.Trim() } | Where-Object { $_ }
        foreach ($dep in $deps) { Require $issues.ContainsKey($dep) "$id dependency $dep is missing." }
    }
    $edges[$id] = @($deps)
}

$visitState = @{}
function Visit-Dependency([string]$node) {
    if ($visitState[$node] -eq 1) { $errors.Add("Dependency cycle detected at $node."); return }
    if ($visitState[$node] -eq 2) { return }
    $visitState[$node] = 1
    foreach ($dep in $edges[$node]) { if ($issues.ContainsKey($dep)) { Visit-Dependency $dep } }
    $visitState[$node] = 2
}
foreach ($id in $issues.Keys) { Visit-Dependency $id }

# Decision-tag consistency: headings are the source of truth for the section list;
# AC rows (requirement column + inline tags) give the AC list; the backlog map follows from Source acceptance.
$secTags = [ordered]@{}
foreach ($hm in [regex]::Matches($requirements, '(?m)^###? (.*)$')) {
    $h = $hm.Groups[1].Value
    $key = [regex]::Match($h, '([A-Z]{3,4} \d\d|Gate \d)')
    $tags = @([regex]::Matches($h, '\[(D-\d+), proposed\]') | ForEach-Object { $_.Groups[1].Value })
    if ($key.Success -and $tags.Count -gt 0) { $secTags[$key.Value] = $tags }
}
foreach ($k in $secTags.Keys) { foreach ($t in $secTags[$k]) { Require ($proposedIds -contains $t) "$k carries tag $t, which is not a proposed decision in the register." } }
$matrixIds = @([regex]::Matches($requirements, '(?m)^\| \[(D-\d+), proposed\] \|') | ForEach-Object { $_.Groups[1].Value } | Sort-Object)
Require (($matrixIds -join ',') -eq ($proposedIds -join ',')) "Matrix rows ($($matrixIds -join ', ')) must match the proposed decisions ($($proposedIds -join ', '))."
$expectedAcs = @{}
foreach ($am in [regex]::Matches($requirements, '(?m)^\| (AC (\d+)) \| (.*?) \| (.*) \|?$')) {
    $acId = 'AC {0:D2}' -f [int]$am.Groups[2].Value
    $found = @{}
    foreach ($tok in [regex]::Matches($am.Groups[3].Value, '[A-Z]{3,4} \d\d')) {
        if ($secTags.Contains($tok.Value)) { foreach ($d in $secTags[$tok.Value]) { $found[$d] = $true } }
    }
    foreach ($tag in [regex]::Matches($am.Groups[4].Value, '\[(D-\d+), proposed\]')) { $found[$tag.Groups[1].Value] = $true }
    foreach ($d in $found.Keys) {
        if (-not $expectedAcs.ContainsKey($d)) { $expectedAcs[$d] = @{} }
        $expectedAcs[$d][$acId] = $true
    }
}
foreach ($mm in [regex]::Matches($requirements, '(?m)^\| \[(D-\d+), proposed\] \| (.*?) \| (.*?) \| (.*) \|?$')) {
    $d = $mm.Groups[1].Value
    $expectedSecs = @($secTags.Keys | Where-Object { $secTags[$_] -contains $d }) -join '; '
    Require ($mm.Groups[2].Value -eq $expectedSecs) "Matrix sections for $d do not match heading tags (expected: $expectedSecs)."
    if ($d -ne 'D-10') {
        $matrixAcs = @([regex]::Matches($mm.Groups[3].Value, '\d+') | ForEach-Object { 'AC {0:D2}' -f [int]$_.Value } | Sort-Object)
        $wantAcs = @()
        if ($expectedAcs.ContainsKey($d)) { $wantAcs = @($expectedAcs[$d].Keys | Sort-Object) }
        Require (($matrixAcs -join ',') -eq ($wantAcs -join ',')) "Matrix AC list for $d does not match the AC rows that cite its tagged sections."
    }
}

$mapRows = @{}
foreach ($r in $decisionFieldRows) { $mapRows[$r.Groups[1].Value] = @($r.Groups[2].Value.Split(',') | ForEach-Object { $_.Trim() } | Where-Object { $_ -and $_ -ne 'None' } | Sort-Object) }
foreach ($id in $issues.Keys) {
    $block = [regex]::Match($backlog, "(?ms)^(?:##|###|####) $([regex]::Escape($id))\s+\p{Pd}.*?(?=^(?:##|###|####) [A-Z]+-\d+\s+\p{Pd}|^## Acceptance coverage matrix|\z)").Value
    $srcAc = [regex]::Match($block, '(?m)^\*\*Source acceptance:\*\* ([^\r\n]+)').Groups[1].Value
    $issueAcs = @([regex]::Matches($srcAc, 'AC (\d+)') | ForEach-Object { 'AC {0:D2}' -f [int]$_.Groups[1].Value })
    $want = @{}
    foreach ($d in $expectedAcs.Keys) { foreach ($a in $issueAcs) { if ($expectedAcs[$d].ContainsKey($a)) { $want[$d] = $true } } }
    if (($id -in @('EPIC-07', 'US-021')) -and ($proposedIds -contains 'D-10')) { $want['D-10'] = $true }
    $wantList = @($want.Keys | Sort-Object)
    if ($mapRows.ContainsKey($id)) {
        Require (($mapRows[$id] -join ',') -eq ($wantList -join ',')) "Decision map for $id does not match its Source acceptance and the requirements matrix (expected: $($wantList -join ', '))."
    }
}

# Every Story, Task and Subtask must carry at least one issue-specific criterion beyond the copied ACs.
foreach ($id in $issues.Keys) {
    if ($issues[$id].Type -eq 'Epic') { continue }
    $ib = [regex]::Match($backlog, "(?ms)^(?:##|###|####) $([regex]::Escape($id))\s+\p{Pd}.*?(?=^(?:##|###|####) [A-Z]+-\d+\s+\p{Pd}|^## Acceptance coverage matrix|\z)").Value
    $specific = [regex]::Matches($ib, '(?m)^\s*- \[ \] (?!\*\*AC )').Count
    Require ($specific -ge 1) "$id has no issue-specific acceptance criterion beyond the copied ACs."
}

Require ($PSVersionTable.PSVersion.Major -ge 5) 'PowerShell 5.1 or newer is required to run the validator.'
Require (-not (Test-Path (Join-Path $root 'Expense_Tracker_Review_Findings.md'))) 'The superseded review must be archived, not left at repository root.'
Require (Test-Path (Join-Path $root 'docs/archive/Expense_Tracker_Review_Findings_v0.1.md')) 'Archived review file is missing.'

if ($errors.Count -gt 0) {
    $errors | ForEach-Object { "ERROR: $_" }
    exit 1
}

Write-Output 'Expense Tracker document checks passed: authority, decision tags, AC coverage, issue hierarchy, requirement references, dependency closure/cycles, decision-field map, matrix/heading/map agreement, mixed-gate tagging, US-021 closure, SPIKE-03 ordering, PowerShell runtime and archive state.'
