from __future__ import annotations

import json
import re
from pathlib import Path


ROOT = Path("/Users/krausekelsen/Documents/AndroidProjects/MyFirstApplication")
WRAPPERS_ROOT = ROOT / "maracuya/src/main/java/cruxui/android/maracuya/wrappers"
OUT = ROOT / "design-system-report/tmp/wrapper-architecture.json"

TARGET_ARCHITECTURE_MARKERS = (
    "WrapperTokens",
    "WrapperTokensResolver",
    "WrapperVariant",
    "WrapperVariantResolver",
    "WrapperBehavior",
    "WrapperBehaviorResolver",
    "WrapperRender",
    "WrapperRenderResolver",
)


def read(path: Path) -> str:
    return path.read_text(encoding="utf-8")


def wrapper_family(path: Path) -> str:
    relative = path.relative_to(WRAPPERS_ROOT)
    parts = relative.parts
    if parts[0] == "core":
        return "core"
    if parts[0] == "theme":
        return "theme"
    if len(parts) >= 3 and parts[0] == "components":
        return "/".join(parts[1:-1])
    return parts[0]


def file_signals(path: Path) -> dict[str, int]:
    text = read(path)
    return {
        "obtainStyledAttributes": len(re.findall(r"\bobtainStyledAttributes\s*\(", text)),
        "styleableRefs": len(re.findall(r"\bR\.styleable\.", text)),
        "mutableStateOf": len(re.findall(r"\bmutableStateOf\b", text)),
        "setEnabledOverrides": len(re.findall(r"\boverride\s+fun\s+setEnabled\s*\(", text)),
        "contentOverrides": len(re.findall(r"\boverride\s+fun\s+Content\s*\(", text)),
        "registryObjects": len(re.findall(r"object\s+[A-Za-z0-9]+TokensOverrideRegistry\b", text)),
        "parserObjects": len(re.findall(r"object\s+[A-Za-z0-9]+AttributeParser\b", text)),
        "attributeModels": len(re.findall(r"data\s+class\s+[A-Za-z0-9]+Attributes\b|data\s+class\s+[A-Za-z0-9]+Config\b", text)),
        "directRegistryResolve": len(re.findall(r"TokensOverrideRegistry\.resolve", text)),
        "whenBlocks": len(re.findall(r"\bwhen\s*\(", text)),
        "publicSetters": len(re.findall(r"\bfun\s+set[A-Z][A-Za-z0-9_]*\s*\(", text)),
    }


def estimate_family(signals: dict[str, int], file_count: int, has_wrapper: bool) -> tuple[str, dict[str, float]]:
    score = (
        signals["styleableRefs"]
        + signals["mutableStateOf"]
        + signals["publicSetters"]
        + signals["whenBlocks"]
        + signals["directRegistryResolve"] * 2
        + file_count
    )
    if not has_wrapper:
        return "base", {"min": 0.75, "max": 1.0}
    if score >= 45:
        return "alta", {"min": 2.5, "max": 3.5}
    if score >= 22:
        return "media", {"min": 1.5, "max": 2.25}
    return "baja", {"min": 0.75, "max": 1.25}


def main() -> None:
    files = sorted(WRAPPERS_ROOT.rglob("*.kt"))
    family_map: dict[str, list[Path]] = {}
    for path in files:
        family_map.setdefault(wrapper_family(path), []).append(path)

    families = []
    global_text = "\n".join(read(path) for path in files)
    has_target_markers = {marker: marker in global_text for marker in TARGET_ARCHITECTURE_MARKERS}
    for family, family_files in sorted(family_map.items()):
        aggregate = {
            "obtainStyledAttributes": 0,
            "styleableRefs": 0,
            "mutableStateOf": 0,
            "setEnabledOverrides": 0,
            "contentOverrides": 0,
            "registryObjects": 0,
            "parserObjects": 0,
            "attributeModels": 0,
            "directRegistryResolve": 0,
            "whenBlocks": 0,
            "publicSetters": 0,
        }
        for path in family_files:
            signals = file_signals(path)
            for key, value in signals.items():
                aggregate[key] += value

        has_wrapper = any(path.name.endswith("Wrp.kt") for path in family_files)
        complexity, estimate = estimate_family(aggregate, len(family_files), has_wrapper)
        families.append(
            {
                "family": family,
                "files": [str(path.relative_to(ROOT)) for path in family_files],
                "fileCount": len(family_files),
                "hasWrapperView": has_wrapper,
                "complexity": complexity,
                "estimateDays": estimate,
                "signals": aggregate,
            }
        )

    migration_families = [family for family in families if family["family"] != "core"]
    design_min = 3.0
    design_max = 4.0
    regression_min = 3.0
    regression_max = 4.0
    migration_min = sum(float(family["estimateDays"]["min"]) for family in migration_families)
    migration_max = sum(float(family["estimateDays"]["max"]) for family in migration_families)

    payload = {
        "methodology": (
            "Audits wrappers as XML exposure families. Each family is evaluated for repeated parser, "
            "attribute model, registry, mutable state and direct Content orchestration patterns. "
            "The target architecture markers are checked globally and are currently absent."
        ),
        "targetArchitectureMarkers": has_target_markers,
        "totals": {
            "ktFiles": len(files),
            "families": len(families),
            "xmlComponentFamilies": len(migration_families),
            "wrapperViews": sum(1 for path in files if path.name.endswith("Wrp.kt")),
            "attributeParsers": sum(1 for path in files if path.name.endswith("AttributeParser.kt")),
            "attributeModels": sum(1 for path in files if path.name.endswith("Attributes.kt") or path.name.endswith("Config.kt")),
            "tokensOverrideRegistries": sum(1 for path in files if path.name.endswith("TokensOverrideRegistry.kt")),
            "obtainStyledAttributes": sum(family["signals"]["obtainStyledAttributes"] for family in families),
            "styleableRefs": sum(family["signals"]["styleableRefs"] for family in families),
            "mutableStateOf": sum(family["signals"]["mutableStateOf"] for family in families),
            "contentOverrides": sum(family["signals"]["contentOverrides"] for family in families),
            "setEnabledOverrides": sum(family["signals"]["setEnabledOverrides"] for family in families),
            "targetMarkersPresent": sum(1 for present in has_target_markers.values() if present),
            "architectureDesignDaysMin": design_min,
            "architectureDesignDaysMax": design_max,
            "familyMigrationDaysMin": round(migration_min, 2),
            "familyMigrationDaysMax": round(migration_max, 2),
            "regressionDaysMin": regression_min,
            "regressionDaysMax": regression_max,
            "totalDaysMin": round(design_min + migration_min + regression_min, 2),
            "totalDaysMax": round(design_max + migration_max + regression_max, 2),
        },
        "families": families,
    }
    OUT.parent.mkdir(parents=True, exist_ok=True)
    OUT.write_text(json.dumps(payload, indent=2, ensure_ascii=False), encoding="utf-8")
    print(json.dumps(payload["totals"], indent=2, ensure_ascii=False))


if __name__ == "__main__":
    main()
