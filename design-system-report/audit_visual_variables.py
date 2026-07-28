from __future__ import annotations

import json
import re
from collections import Counter
from pathlib import Path


ROOT = Path("/Users/krausekelsen/Documents/AndroidProjects/MyFirstApplication")
COMPONENTS_ROOT = ROOT / "maracuya/src/main/java/cruxui/android/maracuya/ui/components"
OUT = ROOT / "design-system-report/tmp/visual-variables.json"

NUMERIC_DP = re.compile(r"\b\d+(?:\.\d+)?\.dp\b")
NUMERIC_SP = re.compile(r"\b\d+(?:\.\d+)?\.sp\b")


def read(path: Path) -> str:
    return path.read_text(encoding="utf-8")


def is_relevant_line(line: str) -> bool:
    stripped = line.strip()
    return bool(stripped) and not stripped.startswith("import ") and not stripped.startswith("//")


def categorize(line: str) -> str:
    lower = line.lower()
    if "borderstroke" in lower or ".border" in lower or "border(" in lower:
        return "border"
    if "roundedcornershape" in lower or "cornerradius" in lower or "shape" in lower:
        return "radius"
    if "padding" in lower or "spacedby" in lower or "spacer" in lower or "offset" in lower:
        return "spacing"
    if (
        "width" in lower
        or "height" in lower
        or "size" in lower
        or "heightin" in lower
        or "widthin" in lower
        or "defaultminsize" in lower
    ):
        return "sizing"
    return "visual"


def visual_matches(path: Path) -> list[dict[str, object]]:
    result: list[dict[str, object]] = []
    for line_number, line in enumerate(read(path).splitlines(), start=1):
        if not is_relevant_line(line):
            continue
        matches = NUMERIC_DP.findall(line) + NUMERIC_SP.findall(line)
        if not matches:
            continue
        result.append(
            {
                "file": str(path.relative_to(ROOT)),
                "line": line_number,
                "category": categorize(line),
                "values": matches,
                "snippet": line.strip(),
            }
        )
    return result


def all_component_groups() -> list[dict[str, object]]:
    groups: list[dict[str, object]] = []
    for mrcy_path in sorted(COMPONENTS_ROOT.rglob("*Mrcy.kt")):
        name = mrcy_path.stem.removesuffix("Mrcy")
        if name == "BottomText":
            files = [mrcy_path]
        else:
            files = sorted(mrcy_path.parent.rglob("*.kt"))

        matches: list[dict[str, object]] = []
        token_refs = 0
        partial_spacing_refs = 0
        for path in files:
            text = read(path)
            token_refs += len(re.findall(r"\b[A-Za-z0-9]+Tokens\b|\b[A-Za-z0-9]+Defaults\b", text))
            partial_spacing_refs += len(re.findall(r"\b[A-Za-z0-9]+Spacings\b", text))
            matches.extend(visual_matches(path))

        categories = Counter(str(match["category"]) for match in matches)
        hardcoded_refs = sum(len(match["values"]) for match in matches)
        if hardcoded_refs >= 18 or len(categories) >= 4:
            level = "alta"
            estimate = {"min": 2.5, "max": 3.5}
        elif hardcoded_refs >= 8 or len(categories) >= 3:
            level = "media"
            estimate = {"min": 1.5, "max": 2.0}
        elif hardcoded_refs > 0:
            level = "baja"
            estimate = {"min": 0.75, "max": 1.25}
        else:
            level = "sin_hallazgo"
            estimate = {"min": 0.0, "max": 0.0}

        groups.append(
            {
                "name": f"{name}Mrcy",
                "path": str(mrcy_path.relative_to(ROOT)),
                "filesAudited": [str(path.relative_to(ROOT)) for path in files],
                "hardcodedVisualRefs": hardcoded_refs,
                "categories": dict(categories),
                "partialSpacingTokenRefs": partial_spacing_refs,
                "tokenOrDefaultRefs": token_refs,
                "complexity": level,
                "estimateDays": estimate,
                "sampleEvidence": matches[:5],
            }
        )
    return groups


def main() -> None:
    unique_matches: list[dict[str, object]] = []
    for path in sorted(COMPONENTS_ROOT.rglob("*.kt")):
        unique_matches.extend(visual_matches(path))

    unique_categories = Counter(str(match["category"]) for match in unique_matches)
    component_groups = all_component_groups()
    affected = [group for group in component_groups if int(group["hardcodedVisualRefs"]) > 0]
    high = [group for group in affected if group["complexity"] == "alta"]
    medium = [group for group in affected if group["complexity"] == "media"]
    low = [group for group in affected if group["complexity"] == "baja"]

    architecture_min = 4.0
    architecture_max = 5.0
    regression_min = 3.0
    regression_max = 4.0
    component_min = sum(float(group["estimateDays"]["min"]) for group in affected)
    component_max = sum(float(group["estimateDays"]["max"]) for group in affected)

    payload = {
        "methodology": (
            "Audits numeric dp/sp literals under ui/components and groups the 13 Mrcy component files. "
            "References to existing Spacings objects are treated as partial mitigation, not as a complete "
            "generic visual token system."
        ),
        "totals": {
            "mrcyComponentFiles": len(component_groups),
            "affectedComponents": len(affected),
            "uniqueHardcodedVisualRefs": sum(len(match["values"]) for match in unique_matches),
            "uniqueEvidenceLines": len(unique_matches),
            "highComplexity": len(high),
            "mediumComplexity": len(medium),
            "lowComplexity": len(low),
            "categories": dict(unique_categories),
            "architectureDesignDaysMin": architecture_min,
            "architectureDesignDaysMax": architecture_max,
            "componentMigrationDaysMin": round(component_min, 2),
            "componentMigrationDaysMax": round(component_max, 2),
            "regressionDaysMin": regression_min,
            "regressionDaysMax": regression_max,
            "totalDaysMin": round(architecture_min + component_min + regression_min, 2),
            "totalDaysMax": round(architecture_max + component_max + regression_max, 2),
        },
        "components": component_groups,
        "evidence": unique_matches,
    }
    OUT.parent.mkdir(parents=True, exist_ok=True)
    OUT.write_text(json.dumps(payload, indent=2, ensure_ascii=False), encoding="utf-8")
    print(json.dumps(payload["totals"], indent=2, ensure_ascii=False))


if __name__ == "__main__":
    main()
