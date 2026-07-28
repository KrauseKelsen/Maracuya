from __future__ import annotations

import json
import re
from collections import Counter
from pathlib import Path
from xml.etree import ElementTree as ET


ROOT = Path(__file__).resolve().parents[1]
VALUES_DIR = ROOT / "maracuya" / "src" / "main" / "res" / "values"
OUT = ROOT / "design-system-report" / "tmp" / "xml-values-architecture.json"


def line_count(path: Path) -> int:
    return len(path.read_text(encoding="utf-8").splitlines())


def normalize_family(styleable: str) -> str:
    name = styleable.replace("Wrp", "")
    name = re.sub(r"(?<!^)([A-Z])", r"_\1", name).lower()
    if name == "my_library_theme":
        return "theme"
    if name == "primary_button":
        return "button"
    return name


def estimate_days(styleable_count: int, attr_count: int, duplicate_count: int) -> dict[str, object]:
    analysis_min = 0.75
    analysis_max = 1.0
    split_min = 0.20 * styleable_count + 0.03 * attr_count + 0.10 * duplicate_count
    split_max = 0.30 * styleable_count + 0.05 * attr_count + 0.15 * duplicate_count
    validation_min = 1.0
    validation_max = 1.5
    total_min = round(analysis_min + split_min + validation_min, 2)
    total_max = round(analysis_max + split_max + validation_max, 2)
    return {
        "analysisDaysMin": analysis_min,
        "analysisDaysMax": analysis_max,
        "splitDaysMin": round(split_min, 2),
        "splitDaysMax": round(split_max, 2),
        "validationDaysMin": validation_min,
        "validationDaysMax": validation_max,
        "totalDaysMin": total_min,
        "totalDaysMax": total_max,
        "businessDaysRange": f"{total_min} a {total_max} dias",
    }


def main() -> None:
    values_files = sorted(VALUES_DIR.glob("*.xml"))
    attrs_files = [p for p in values_files if "attr" in p.name]
    styleables = []
    attr_names = []
    formats = Counter()

    for path in attrs_files:
        tree = ET.parse(path)
        for styleable_node in tree.getroot().findall("declare-styleable"):
            name = styleable_node.attrib["name"]
            attrs = []
            for attr_node in styleable_node.findall("attr"):
                attr_name = attr_node.attrib["name"]
                attr_names.append(attr_name)
                attr_format = attr_node.attrib.get("format", "referencia compartida")
                formats[attr_format] += 1
                attrs.append(
                    {
                        "name": attr_name,
                        "format": attr_format,
                        "lineHint": None,
                    }
                )
            styleables.append(
                {
                    "name": name,
                    "family": normalize_family(name),
                    "attrCount": len(attrs),
                    "attrs": attrs,
                }
            )

    repeated_attrs = {name: count for name, count in Counter(attr_names).items() if count > 1}
    family_files = sorted({item["family"] for item in styleables})
    recommended_files = ["attrs_shared.xml"] + [f"attrs_{family}.xml" for family in family_files]
    target_files = len(recommended_files)
    estimate = estimate_days(len(styleables), len(attr_names), len(repeated_attrs))

    payload = {
        "scope": str(VALUES_DIR.relative_to(ROOT)),
        "currentValuesXmlFiles": len(values_files),
        "attrsXmlFiles": len(attrs_files),
        "largestValuesFile": {
            "path": str(max(values_files, key=line_count).relative_to(ROOT)) if values_files else None,
            "lines": max((line_count(path) for path in values_files), default=0),
        },
        "styleableCount": len(styleables),
        "attrOccurrences": len(attr_names),
        "uniqueAttrNames": len(set(attr_names)),
        "repeatedAttrNames": repeated_attrs,
        "repeatedAttrCount": len(repeated_attrs),
        "formatBreakdown": dict(formats),
        "styleables": styleables,
        "recommendedFiles": recommended_files,
        "targetValuesXmlFiles": target_files,
        "fileSplitGap": max(target_files - len(values_files), 0),
        "architectureMarkersPresent": 0,
        "estimate": estimate,
        "risks": [
            "Contratos XML de familias distintas concentrados en un unico archivo.",
            "Atributos reutilizados sin frontera explicita de propiedad.",
            "Cada nuevo wrapper agranda el mismo archivo y aumenta el riesgo de colisiones.",
            "No existe separacion por dominio para theme, button, navigation, label y textfield.",
        ],
    }

    OUT.parent.mkdir(parents=True, exist_ok=True)
    OUT.write_text(json.dumps(payload, indent=2, ensure_ascii=False), encoding="utf-8")
    print(json.dumps(payload, indent=2, ensure_ascii=False))


if __name__ == "__main__":
    main()
