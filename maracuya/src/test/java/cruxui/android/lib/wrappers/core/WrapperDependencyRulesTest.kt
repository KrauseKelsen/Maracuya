package cruxui.android.lib.wrappers.core

import org.junit.Assert.*
import org.junit.Test
import java.io.File

/** Evita que futuras incorporaciones vuelvan a introducir componentes en la infraestructura compartida. */
class WrapperDependencyRulesTest {
    private val sourceRoot = listOf(
        File("src/main/java/cruxui/android/lib/wrappers"),
        File("maracuya/src/main/java/cruxui/android/lib/wrappers"),
    ).first { it.isDirectory }

    @Test fun `nucleo y capacidades no importan componentes concretos`() {
        for (folder in listOf("core", "capabilities", "adapters")) {
            File(sourceRoot, folder).walkTopDown().filter { it.extension == "kt" }.forEach { file ->
                val forbidden = file.readLines().filter { it.startsWith("import ") && ".components." in it }
                assertTrue("${file.name}: $forbidden", forbidden.isEmpty())
            }
        }
    }

    @Test fun `los registros no dependen de Android ni Compose y los modelos no cargan recursos`() {
        File(sourceRoot, "core/registry").walkTopDown().filter { it.extension == "kt" }.forEach { file ->
            assertFalse(file.name, file.readLines().any { it.startsWith("import android.") || it.startsWith("import androidx.") })
        }
        File(sourceRoot, "core/tokens").walkTopDown().filter { it.extension == "kt" }.forEach { file ->
            assertFalse(file.name, file.readLines().any { it.startsWith("import ") && ".core.resources." in it })
        }
    }
}
