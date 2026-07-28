from __future__ import annotations

import json
import re
from collections import Counter
from pathlib import Path


ROOT = Path("/Users/krausekelsen/Documents/AndroidProjects/MyFirstApplication")
PACKAGE_ROOT = ROOT / "maracuya/src/main/java/cruxui/android/maracuya"
OUT_JSON = ROOT / "design-system-report/tmp/kdoc-coverage.json"

CLASS_DECLARATION_RE = re.compile(
    r"^(?P<indent>\s*)"
    r"(?P<prefix>(?:(?:public|internal|private|protected|data|sealed|enum|annotation|value|open|abstract)\s+)*)"
    r"(?P<kind>class|object|interface)\s+"
    r"(?P<name>[A-Za-z_][A-Za-z0-9_]*)\b"
)

MRCY_FUNCTION_RE = re.compile(
    r"^(?P<indent>\s*)"
    r"(?:(?:public|internal|private)\s+)?"
    r"fun\s+(?P<name>[A-Za-z_][A-Za-z0-9_]*Mrcy)\b"
)

TEMPLATE_CHECKS = {
    "Descripción": re.compile(r"descripci[oó]n\s*:", re.IGNORECASE),
    "Uso": re.compile(r"\buso\s*:", re.IGNORECASE),
    "@author": re.compile(r"@author\b", re.IGNORECASE),
    "@since": re.compile(r"@since\b", re.IGNORECASE),
    "@version": re.compile(r"@version\b", re.IGNORECASE),
    "@param": re.compile(r"@param\b", re.IGNORECASE),
    "@see": re.compile(r"@see\b", re.IGNORECASE),
}

PARTIAL_EFFORT_BY_WEIGHT = {
    "A": 30,
    "B": 20,
    "C": 10,
}


def extract_kdoc_before(lines: list[str], declaration_index: int) -> str | None:
    index = declaration_index - 1
    while index >= 0 and (
        not lines[index].strip()
        or lines[index].lstrip().startswith("@")
        or lines[index].lstrip().startswith("//")
    ):
        index -= 1

    if index < 0 or not lines[index].strip().endswith("*/"):
        return None

    end = index
    while index >= 0 and "/**" not in lines[index]:
        index -= 1

    if index < 0:
        return None
    return "\n".join(lines[index : end + 1])


def normalize_kdoc(kdoc: str) -> str:
    text = re.sub(r"^\s*/\*\*", "", kdoc)
    text = re.sub(r"\*/\s*$", "", text)
    lines = [re.sub(r"^\s*\* ?", "", line) for line in text.splitlines()]
    return "\n".join(lines)


def template_reasons(kdoc: str | None) -> list[str]:
    if not kdoc:
        return list(TEMPLATE_CHECKS)

    clean = normalize_kdoc(kdoc)
    return [label for label, pattern in TEMPLATE_CHECKS.items() if not pattern.search(clean)]


def template_status(kdoc: str | None) -> str:
    if not kdoc:
        return "missing_kdoc"
    if not template_reasons(kdoc):
        return "template_compliant"
    return "partial_kdoc"


def normalize_kind(prefix: str, kind: str) -> str:
    if "enum " in prefix:
        return "enum class"
    if "annotation " in prefix:
        return "annotation class"
    if "value " in prefix:
        return "value class"
    if "data " in prefix:
        return "data class"
    if "sealed " in prefix:
        return "sealed class"
    return kind


def visibility(prefix: str) -> str:
    words = set(prefix.split())
    for modifier in ("private", "protected", "internal", "public"):
        if modifier in words:
            return modifier
    return "public"


def relative_path(path: Path) -> str:
    return str(path.relative_to(ROOT))


def folder_bucket(path: Path) -> str:
    parts = path.relative_to(PACKAGE_ROOT).parts
    if not parts:
        return "root"
    if parts[0] == "tokens" and len(parts) > 1:
        return f"tokens/{parts[1]}"
    if parts[0] == "ui" and len(parts) > 2 and parts[1] == "components":
        return f"ui/components/{parts[2]}"
    if parts[0] == "ui" and len(parts) > 1:
        return f"ui/{parts[1]}"
    if parts[0] == "wrappers" and len(parts) > 1:
        return f"wrappers/{parts[1]}"
    if parts[0] == "utils" and len(parts) > 1:
        return f"utils/{parts[1]}"
    if parts[0] == "atoms" and len(parts) > 1:
        return f"atoms/{parts[1]}"
    return parts[0]


def estimate_weight(item: dict[str, object]) -> tuple[str, int, str]:
    path = str(item["path"])
    name = str(item["name"])
    kind = str(item["kind"])

    if str(item.get("visibility")) == "private":
        return ("C", 15, "Declaración privada o helper interno.")
    if "/ui/previews/" in path:
        return ("C", 15, "Preview/demo o soporte visual de bajo riesgo.")
    if "/utils/" in path or kind in {"enum class", "interface"}:
        return ("C", 15, "Utilidad, enum o contrato simple.")
    if (
        "/ui/components/" in path
        or "/wrappers/" in path
        or "/theme/" in path
        or "/atoms/" in path
        or "/compositions/" in path
        or name.endswith("Mrcy")
    ):
        return ("A", 45, "Superficie pública o cercana al consumo del DS.")
    return ("B", 30, "Tokens, modelos, resolvers o infraestructura documentable.")


def has_recent_composable_annotation(lines: list[str], function_index: int) -> bool:
    for index in range(function_index - 1, max(-1, function_index - 8), -1):
        stripped = lines[index].strip()
        if not stripped:
            continue
        if "@Composable" in stripped:
            return True
        if stripped.startswith("@"):
            continue
        break
    return False


def enrich_item(item: dict[str, object], kdoc: str | None) -> dict[str, object]:
    status = template_status(kdoc)
    weight, base_minutes, reason = estimate_weight(item)
    effort_minutes = 0
    if status == "missing_kdoc":
        effort_minutes = base_minutes
    elif status == "partial_kdoc":
        effort_minutes = PARTIAL_EFFORT_BY_WEIGHT[weight]

    item.update(
        {
            "has_kdoc": kdoc is not None,
            "template_compliant": status == "template_compliant",
            "template_status": status,
            "missing_template_fields": template_reasons(kdoc),
            "weight": weight,
            "base_minutes": base_minutes,
            "effort_minutes": effort_minutes,
            "weightReason": reason,
        }
    )
    return item


def status_totals(items: list[dict[str, object]]) -> dict[str, int]:
    counter = Counter(str(item["template_status"]) for item in items)
    return {
        "total": len(items),
        "template_compliant": counter["template_compliant"],
        "partial_kdoc": counter["partial_kdoc"],
        "missing_kdoc": counter["missing_kdoc"],
        "missing_template": counter["partial_kdoc"] + counter["missing_kdoc"],
    }


def audit() -> dict[str, object]:
    class_like: list[dict[str, object]] = []
    component_functions: list[dict[str, object]] = []

    for path in sorted(PACKAGE_ROOT.rglob("*.kt")):
        lines = path.read_text(encoding="utf-8").splitlines()
        for index, line in enumerate(lines):
            class_match = CLASS_DECLARATION_RE.match(line)
            if class_match:
                prefix = class_match.group("prefix")
                kdoc = extract_kdoc_before(lines, index)
                item = {
                    "path": relative_path(path),
                    "folder": folder_bucket(path),
                    "line": index + 1,
                    "indent": len(class_match.group("indent")),
                    "kind": normalize_kind(prefix, class_match.group("kind")),
                    "visibility": visibility(prefix),
                    "name": class_match.group("name"),
                }
                class_like.append(enrich_item(item, kdoc))

            function_match = MRCY_FUNCTION_RE.match(line)
            if function_match and has_recent_composable_annotation(lines, index) and "private fun" not in line:
                kdoc = extract_kdoc_before(lines, index)
                item = {
                    "path": relative_path(path),
                    "folder": folder_bucket(path),
                    "line": index + 1,
                    "kind": "fun",
                    "visibility": "public",
                    "name": function_match.group("name"),
                    "component_function": True,
                }
                component_functions.append(enrich_item(item, kdoc))

    missing_template_class_like = [item for item in class_like if not item["template_compliant"]]
    missing_template_components = [item for item in component_functions if not item["template_compliant"]]
    all_missing_template = missing_template_class_like + missing_template_components

    review_base_minutes = sum(int(item["effort_minutes"]) for item in all_missing_template)
    review_minutes = round(review_base_minutes * 0.25)
    total_minutes = review_base_minutes + review_minutes

    by_folder: dict[str, dict[str, object]] = {}
    for item in class_like:
        folder = str(item["folder"])
        row = by_folder.setdefault(
            folder,
            {
                "folder": folder,
                "total": 0,
                "template_compliant": 0,
                "partial_kdoc": 0,
                "missing_kdoc": 0,
                "missing_template": 0,
                "missing_weight_A": 0,
                "missing_weight_B": 0,
                "missing_weight_C": 0,
                "effort_minutes": 0,
            },
        )
        status = str(item["template_status"])
        row["total"] = int(row["total"]) + 1
        row[status] = int(row[status]) + 1
        if status != "template_compliant":
            row["missing_template"] = int(row["missing_template"]) + 1
            row[f"missing_weight_{item['weight']}"] = int(row[f"missing_weight_{item['weight']}"]) + 1
            row["effort_minutes"] = int(row["effort_minutes"]) + int(item["effort_minutes"])

    result = {
        "methodology": (
            "Counts named class-like Kotlin declarations under "
            "maracuya/src/main/java/cruxui/android/maracuya, including nested declarations and wrappers: "
            "class, data class, enum class, sealed class, value class, annotation class, object and interface. "
            "Public @Composable *Mrcy functions are reported separately as API functions. A declaration only "
            "counts as template-compliant when its KDoc follows the requested fields: Descripción, Uso, "
            "@author, @since, @version, @param and @see. Any other KDoc is counted as partial debt."
        ),
        "template_checks": list(TEMPLATE_CHECKS),
        "weight_model": {
            "A": {"minutes_create": 45, "minutes_upgrade_partial": 30, "meaning": "Superficie pública o cercana al consumo del DS."},
            "B": {"minutes_create": 30, "minutes_upgrade_partial": 20, "meaning": "Tokens, modelos, resolvers o infraestructura documentable."},
            "C": {"minutes_create": 15, "minutes_upgrade_partial": 10, "meaning": "Utilidades, enums simples, previews o soporte de bajo riesgo."},
            "review": "25% adicional para consistencia, formato, @see, hover en IDE y revisión cruzada.",
        },
        "class_like_totals": {
            **status_totals(class_like),
            "missing_template_by_weight": dict(Counter(item["weight"] for item in missing_template_class_like)),
            "missing_template_by_kind": dict(Counter(item["kind"] for item in missing_template_class_like)),
        },
        "component_function_totals": status_totals(component_functions),
        "combined_totals": status_totals(class_like + component_functions),
        "estimate": {
            "base_effort_minutes": review_base_minutes,
            "review_minutes": review_minutes,
            "total_minutes": total_minutes,
            "total_hours": round(total_minutes / 60, 1),
            "business_days_6h": round(total_minutes / 360, 1),
        },
        "by_folder": sorted(
            by_folder.values(),
            key=lambda row: (-int(row["missing_template"]), -int(row["effort_minutes"]), str(row["folder"])),
        ),
        "missing_template_class_like": missing_template_class_like,
        "missing_template_component_functions": missing_template_components,
    }

    OUT_JSON.parent.mkdir(parents=True, exist_ok=True)
    OUT_JSON.write_text(json.dumps(result, indent=2, ensure_ascii=False), encoding="utf-8")
    return result


if __name__ == "__main__":
    print(json.dumps(audit(), indent=2, ensure_ascii=False))
