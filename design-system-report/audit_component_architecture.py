from __future__ import annotations

import json
import re
from pathlib import Path


ROOT = Path("/Users/krausekelsen/Documents/AndroidProjects/MyFirstApplication")
COMPONENTS_ROOT = ROOT / "maracuya/src/main/java/cruxui/android/maracuya/ui/components"
OUT = ROOT / "design-system-report/tmp/component-architecture.json"

REQUIRED_LAYERS = (
    "tokens",
    "tokensResolver",
    "tokensRender",
    "tokensRenderResolver",
    "variant",
    "variantResolver",
    "behavior",
    "behaviorResolver",
)


def read(path: Path) -> str:
    return path.read_text(encoding="utf-8")


def is_public_mrcy(file_path: Path) -> bool:
    text = read(file_path)
    name = file_path.stem
    return re.search(rf"(?m)^fun\s+{re.escape(name)}\s*\(", text) is not None


def layer_files(component_name: str, component_dir: Path) -> dict[str, list[str]]:
    files = sorted(component_dir.rglob("*.kt"))
    all_component_files = sorted(COMPONENTS_ROOT.rglob(f"{component_name}*.kt"))

    def matching(pattern: str, roots: list[Path] | None = None) -> list[str]:
        source = roots if roots is not None else files
        regex = re.compile(pattern)
        return [
            str(path.relative_to(ROOT))
            for path in source
            if regex.search(path.name)
        ]

    layers = {
        "tokens": matching(rf"^{re.escape(component_name)}Tokens\.kt$"),
        "tokensResolver": matching(rf"^{re.escape(component_name)}TokensResolver\.kt$", all_component_files),
        "tokensRender": matching(rf"^{re.escape(component_name)}TokensRender\.kt$"),
        "tokensRenderResolver": matching(rf"^{re.escape(component_name)}TokensRenderResolver\.kt$"),
        "variant": matching(rf"^{re.escape(component_name)}Variant\.kt$"),
        "variantResolver": matching(rf"^{re.escape(component_name)}VariantResolver\.kt$"),
        "behavior": matching(rf"^{re.escape(component_name)}Behavior\.kt$"),
        "behaviorResolver": matching(rf"^{re.escape(component_name)}BehaviorResolver\.kt$"),
    }
    return layers


def signal_counts(mrcy_path: Path) -> dict[str, int | bool]:
    text = read(mrcy_path)
    lines = text.splitlines()
    return {
        "mrcyLines": len(lines),
        "whenBlocks": len(re.findall(r"\bwhen\s*\(", text)),
        "rememberState": bool(re.search(r"\bremember\b|\bmutableStateOf\b", text)),
        "requires": len(re.findall(r"\brequire\s*\(", text)),
        "privateComposableHelpers": len(re.findall(r"(?m)^@Composable\s*\nprivate\s+fun\s+", text)),
        "internalResolverCall": "Resolver.resolve" in text or "Resolver." in text,
    }


def complexity(component_name: str, layers: dict[str, list[str]], signals: dict[str, int | bool]) -> str:
    missing_count = sum(1 for layer in REQUIRED_LAYERS if not layers[layer])
    mrcy_lines = int(signals["mrcyLines"])
    when_blocks = int(signals["whenBlocks"])
    private_helpers = int(signals["privateComposableHelpers"])

    if missing_count == 0:
        return "alineado"
    if mrcy_lines >= 220 or when_blocks >= 4 or private_helpers >= 2:
        return "alta"
    if mrcy_lines >= 110 or missing_count >= 5:
        return "media"
    return "baja"


def estimate_days(level: str) -> tuple[float, float]:
    if level == "alineado":
        return (0.0, 0.0)
    if level == "alta":
        return (2.5, 3.5)
    if level == "media":
        return (1.5, 2.0)
    return (0.75, 1.25)


def public_non_mrcy_composables() -> list[dict[str, str]]:
    result: list[dict[str, str]] = []
    for file_path in sorted(COMPONENTS_ROOT.rglob("*.kt")):
        if file_path.name.endswith("Mrcy.kt"):
            continue
        text = read(file_path)
        matches = re.finditer(r"(?m)^@Composable\s*\nfun\s+([A-Za-z0-9_]+)\s*\(", text)
        for match in matches:
            result.append(
                {
                    "name": match.group(1),
                    "file": str(file_path.relative_to(ROOT)),
                }
            )
    return result


def main() -> None:
    components = []
    for mrcy_path in sorted(COMPONENTS_ROOT.rglob("*Mrcy.kt")):
        if not is_public_mrcy(mrcy_path):
            continue

        name = mrcy_path.stem.removesuffix("Mrcy")
        layers = layer_files(name, mrcy_path.parent)
        signals = signal_counts(mrcy_path)
        level = complexity(name, layers, signals)
        days_min, days_max = estimate_days(level)
        missing = [layer for layer in REQUIRED_LAYERS if not layers[layer]]

        components.append(
            {
                "name": f"{name}Mrcy",
                "path": str(mrcy_path.relative_to(ROOT)),
                "status": "current" if level == "alineado" else "needs_migration",
                "complexity": level,
                "presentLayers": [layer for layer in REQUIRED_LAYERS if layers[layer]],
                "missingLayers": missing,
                "layers": layers,
                "signals": signals,
                "estimateDays": {"min": days_min, "max": days_max},
            }
        )

    migration = [component for component in components if component["status"] == "needs_migration"]
    total_min = sum(component["estimateDays"]["min"] for component in migration)
    total_max = sum(component["estimateDays"]["max"] for component in migration)
    review_min = 3.0
    review_max = 4.0

    payload = {
        "methodology": (
            "Public top-level composables whose file and function end in Mrcy under ui/components. "
            "Internal helpers such as BottomTextMrcy are excluded. Public non-Mrcy composables are reported "
            "separately as architecture-decision candidates."
        ),
        "requiredLayers": list(REQUIRED_LAYERS),
        "totals": {
            "publicMrcyComponents": len(components),
            "currentArchitecture": len(components) - len(migration),
            "needsMigration": len(migration),
            "publicNonMrcyComposables": len(public_non_mrcy_composables()),
            "migrationDaysMin": round(total_min, 2),
            "migrationDaysMax": round(total_max, 2),
            "reviewAndRegressionDaysMin": review_min,
            "reviewAndRegressionDaysMax": review_max,
            "totalDaysMin": round(total_min + review_min, 2),
            "totalDaysMax": round(total_max + review_max, 2),
        },
        "components": components,
        "publicNonMrcyComposables": public_non_mrcy_composables(),
    }

    OUT.parent.mkdir(parents=True, exist_ok=True)
    OUT.write_text(json.dumps(payload, indent=2, ensure_ascii=False), encoding="utf-8")
    print(json.dumps(payload["totals"], indent=2))


if __name__ == "__main__":
    main()
