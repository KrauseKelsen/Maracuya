# Guía de traslado manual a cruxui.android.lib

Guía para actualizar el proyecto del trabajo anterior a `xmlarchitecture` usando el código actual de este proyecto. Preparada el 3 de noviembre de 2026. Puedes enviar carpetas pequeñas o archivos por correo y pegarlos progresivamente.

**Empieza por esta guía.** El orden sirve para organizar la copia; no significa que cada carpeta compile por separado. Es normal que Android Studio marque referencias sin resolver hasta completar los bloques y generar el catálogo.

## 1. Nombres y rutas que vas a utilizar

| Concepto | En esta computadora | En la computadora del trabajo |
| --- | --- | --- |
| Paquete Kotlin y namespace | `cruxui.android.lib` | `cruxui.android.lib` |
| Módulo Android de la librería | `maracuya` | Su nombre real, por ejemplo `lib` |
| Directorio de código | `maracuya/src/main/java/cruxui/android/lib/` | `<modulo>/src/main/java/cruxui/android/lib/` |
| Directorio de recursos | `maracuya/src/main/res/` | `<modulo>/src/main/res/` |
| Módulo nuevo del exportador | `token-exporter` | `token-exporter` en la raíz del proyecto |

En los comandos de esta guía se supone que el módulo del trabajo se llama **`lib`**. Si se llama distinto, cambia `:lib:` por su ruta Gradle real. Si la carpeta física del módulo tiene otro nombre, adapta también las dos rutas de fuentes del exportador. El paquete no cambia por cambiar el nombre del módulo.

No cambies el `applicationId` de la aplicación del trabajo ni su nombre de proyecto. El namespace indicado corresponde a la librería.

## 2. Preparación antes del primer correo

- [ ] Guarda un commit o una copia de respaldo del proyecto del trabajo. Haz la migración en una rama propia, por ejemplo `migracion-xml-compose`.
- [ ] Identifica el módulo Android que contiene `cruxui.android.lib` y apunta su carpeta física.
- [ ] Separa las personalizaciones exclusivas del trabajo: marcas, paletas, componentes propios, publicación y configuración corporativa.
- [ ] Confirma que el proyecto actual del trabajo compila antes de empezar. Así distingues problemas previos de problemas de traslado.
- [ ] Envía `GUIA-TRANSFERENCIA.html` a la otra computadora. Se abre directamente en un navegador, sin conexión. El `.md` es la versión editable del mismo contenido.

**Cómo enviar cada lote:** usa un asunto como `XML-Compose / 04 / atoms/icons`, indica la ruta destino y si el envío contiene la carpeta completa o solo una parte. Si pegas código en el correo, indica el nombre completo de cada archivo y copia todo su contenido, desde `package` hasta la última línea. Reemplaza el archivo completo; no lo agregues al final del anterior. Conserva carpetas anidadas, nombres y mayúsculas.

Marca un lote como completo solo cuando hayas copiado todos sus archivos. El inventario al final permite comprobarlos. Archivos de fuentes tipográficas (`.ttf`, `.otf`) se envían como archivos, no como texto.

## 3. Regla para sustituir una estructura antigua

Para cada carpeta del sistema de diseño, compara sus archivos actuales con el inventario de esta guía. Copiar encima no elimina archivos viejos y puede dejar clases duplicadas.

1. Respalda las modificaciones particulares del trabajo.
2. Copia los archivos actuales completos a su ruta correspondiente.
3. Retira del árbol de fuentes las implementaciones antiguas que el nuevo árbol sustituye. Si quieres conservarlas temporalmente, colócalas fuera de `src`, no en otro paquete dentro del código compilado.
4. Reaplica las personalizaciones del trabajo sobre los contratos actuales y compruébalas al final.

La lista de retiradas de la sección 7 está comprobada contra la revisión base de este repositorio. Como no tenemos aquí la copia del trabajo, puede haber allí otros archivos antiguos o particulares que debas comparar.

## 4. Orden de copiado del código de la librería

Todas las rutas de esta tabla parten de `maracuya/src/main/java/cruxui/android/lib/`. En destino parten de `<modulo>/src/main/java/cruxui/android/lib/`.

| Hecho | Lote | Carpeta o archivos a pasar | Indicaciones |
| --- | --- | --- | --- |
| ☐ | 01 | `tokens/base` | Tipos base: color, tipografía, icono, fuente y anotación de grupos. |
| ☐ | 02 | `tokens/colors`, `tokens/fonts` | Puedes mandar cada carpeta por separado. |
| ☐ | 03 | `tokens/spacings` | Incluye `InteractionSpacings`; no omitir las medidas existentes de otros componentes. |
| ☐ | 04 | `atoms/icons` | Copiar todos los grupos Library; puede ser un archivo por correo. |
| ☐ | 05 | Archivos de primer nivel de `atoms` | Colores, tipografías y catálogo de iconos. No copiar solo los tres archivos sin sus grupos. |
| ☐ | 06 | `theme` | Copiar todos los archivos actuales; retirar `LibraryThemeSet.kt` antiguo como explica la sección 7. |
| ☐ | 07 | `core/text`, `core/components` | Núcleo compartido; incluye las reglas y medidas comunes de TextView. |
| ☐ | 08 | `compositions` | Proveedores Compose del tema. |
| ☐ | 09 | `utils` | Incluir archivos directos y subcarpetas `composeadapters` y `materialadapters`. |
| ☐ | 10 | `wrappers/core` | Dividir por subcarpetas: registro, tokens, recursos, atributos, tema, render y comportamiento. |
| ☐ | 11 | `wrappers/capabilities` | Copiar texto y drawables por separado si hace falta. |
| ☐ | 12 | `wrappers/adapters`, `wrappers/theme` | Puente Compose y proveedor de tema XML. |
| ☐ | 13 | `wrappers/components/textviews` | Dividir por `attributes`, `behavior`, `core`, `tokens`, `tokensrender`, `variant`, `render`; incluir el archivo principal `TextView.kt`. |
| ☐ | 14 | `wrappers/components/buttons`, `labels`, `textfields` | Copiar por componente. Los wrappers existentes también usan contratos actualizados. |
| ☐ | 15 | `ui/components/textviews` | Implementación Compose que comparte el núcleo. |
| ☐ | 16 | Resto de `ui/components` | Una familia por correo: `buttons`, `checkboxes`, `dropdowns`, `inputs`, `labels`, `layouts`, `textfields`, `texts`, `utils`. Dividir `buttons` por subcarpetas. |
| ☐ | 17 | `ui/previews`, `wrappers/previews` | Algunas vistas previas se referencian desde layouts; copiarlas también. |

**No envíes una carpeta `semantics` antigua.** `CorporateIcons` y `CorporateTypography` ahora son aliases generados. También se generan los antiguos tipos de `tokens/icons`, las colecciones y los roles del catálogo. Su ausencia entre las fuentes manuales es intencional.

Para este traslado completo, copia también las familias de componentes que ya existían: sus imports y adaptadores pueden haber cambiado. No basta con agregar únicamente TextView y dejar todos los archivos anteriores intactos.

## 5. Crear Token Exporter desde cero

### 5.1. Crear el módulo como carpeta

En la raíz del proyecto del trabajo, junto a la carpeta del módulo Android, crea esta estructura. No necesita asistente de Android Studio, `AndroidManifest.xml` ni `res`, porque es un módulo Kotlin/JVM de construcción.

```text
proyecto-del-trabajo/
  settings.gradle.kts
  build.gradle.kts
  gradle/libs.versions.toml
  lib/                         # o el nombre real del módulo Android
  token-exporter/
    build.gradle.kts
    README.md
    src/
      catalogSchema/kotlin/schema/
      main/kotlin/exporter/
      test/kotlin/schema/
      test/kotlin/exporter/
```

### 5.2. Copiarlo en lotes pequeños

| Hecho | Envío | Origen y destino relativo dentro de token-exporter |
| --- | --- | --- |
| ☐ | E1 | `build.gradle.kts` y `README.md` |
| ☐ | E2 | `src/catalogSchema/kotlin/schema/` completo; dividir por archivos si hace falta |
| ☐ | E3 | `src/main/kotlin/exporter/catalog/` y `validation/` |
| ☐ | E4 | `src/main/kotlin/exporter/output/` |
| ☐ | E5 | `src/main/kotlin/exporter/android/` |
| ☐ | E6 | `src/main/kotlin/exporter/kotlin/` |
| ☐ | E7 | `src/main/kotlin/exporter/components/` y el archivo directo `ExportLibraryTokens.kt` |
| ☐ | E8 | `src/test/kotlin/schema/` y `src/test/kotlin/exporter/` |

No crees una dependencia de `token-exporter` hacia el módulo Android. El exportador compila directamente una selección de fuentes puras, mientras que el módulo Android consume sus salidas: agregar una dependencia de vuelta crea un ciclo.

### 5.3. Adaptar las dos rutas de fuentes

El `token-exporter/build.gradle.kts` completo está en el anexo A de esta guía, ya adaptado al ejemplo de módulo físico `lib`.

Si copias el archivo original desde este repositorio, cambia **las dos apariciones** de:

```kotlin
kotlin.srcDir("../maracuya/src/main/java")
```

por la ruta física real, por ejemplo:

```kotlin
kotlin.srcDir("../lib/src/main/java")
```

Los filtros `cruxui/android/lib/...` se conservan. No añadas todas las fuentes Android al exportador ni elimines los filtros `include`.

### 5.4. Registrar el módulo y el plugin

En `settings.gradle.kts` del trabajo, conserva sus repositorios y configuración corporativa y agrega, una sola vez:

```kotlin
include(":token-exporter")
```

La declaración existente de `include(":lib")`, o su equivalente real, se conserva. No reemplaces `rootProject.name` ni las credenciales o repositorios corporativos por los de esta computadora.

En la sección `[plugins]` de `gradle/libs.versions.toml`, agrega si falta:

```toml
kotlin-jvm = { id = "org.jetbrains.kotlin.jvm", version.ref = "kotlin" }
```

En el bloque `plugins` del `build.gradle.kts` raíz, agrega si falta:

```kotlin
alias(libs.plugins.kotlin.jvm) apply false
```

Todos los plugins Kotlin deben usar una versión alineada. El anexo C recoge el catálogo actual completo como referencia para integrar aliases y dependencias faltantes, sin duplicar secciones TOML.

### 5.5. Conectar el exportador a la librería Android

En `<modulo>/build.gradle.kts`:

1. Configura `namespace = "cruxui.android.lib"`.
2. Conserva los plugins Android Library, Kotlin Android y Kotlin Compose de la versión actual.
3. Integra las dependencias y las opciones de compilación/pruebas del anexo B.
4. Agrega los imports `javax.inject.Inject` y `org.gradle.process.ExecOperations` al inicio del archivo.
5. Agrega, a nivel superior, el bloque que comienza en `val tokenExporter by configurations.creating` y termina en `androidComponents.onVariants`. Debe quedar fuera de `android {}` y de `dependencies {}`. Se incluye completo en el anexo B.

La dependencia del exportador pertenece a la configuración `tokenExporter`, **no** a `implementation`. El host que consume la librería no debe ejecutar ni empaquetar la herramienta.

El build actual usa el plugin Kotlin Compose; retira el bloque antiguo `composeOptions { kotlinCompilerExtensionVersion = ... }` si venía de la estructura anterior. Conserva `buildFeatures { compose = true }`.

### 5.6. Entorno usado para la verificación local

| Elemento | Configuración actual |
| --- | --- |
| Gradle Wrapper | 8.13 |
| Android Gradle Plugin | 8.13.0 |
| Kotlin y plugins Kotlin | 2.0.21 |
| Java y Kotlin bytecode target | 19 |
| compileSdk de la librería | 36 |
| minSdk de la librería | 28 |

El JDK que ejecuta Gradle debe poder compilar target 19 y ser compatible con Gradle/AGP; usa la configuración del entorno validado o una equivalente comprobada. No basta con tener instalado cualquier JDK. No cambies únicamente Kotlin o AGP de forma aislada.

Si el trabajo usa otras versiones, esta guía no confirma su compatibilidad: alinea el conjunto o valida esa combinación allí. Mantén el acceso a dependencias a través de sus repositorios corporativos. `--offline` solo sirve cuando las dependencias ya están en la caché de esa computadora.

## 6. Pasar los recursos Android y las pruebas

El código Kotlin por sí solo no completa el traslado. Copia desde `maracuya/` al módulo destino, manteniendo estas rutas:

| Hecho | Ruta | Qué revisar |
| --- | --- | --- |
| ☐ | `src/main/res/drawable/` | Todos los assets usados por Library. Se puede dividir por nombres en varios correos. |
| ☐ | `src/main/res/font/` | Todos los archivos tipográficos; adjuntar los binarios. |
| ☐ | `src/main/res/values/` | Incluye `attrs.xml`, textos y acciones. Si el trabajo tiene recursos propios, integrar por nombre, sin duplicar atributos ni styleables. |
| ☐ | `src/main/res/values-en/` | Traducciones actuales. Conservar otros idiomas del trabajo. |
| ☐ | `src/main/res/layout/` | Previews de catálogo, tipografía, TextView y wrappers. Los nombres de clases usan `cruxui.android.lib`. |
| ☐ | `src/main/res/color/` | Comparar el contenido real; no reconstruir selectores antiguos que ahora son fixtures de debug. |
| ☐ | `src/main/AndroidManifest.xml` | El manifiesto actual de la librería es mínimo; integrar sin borrar declaraciones propias necesarias. |
| ☐ | `consumer-rules.pro` y `proguard-rules.pro` | Comparar e integrar las reglas del módulo. |
| ☐ | `src/debug/res/` | Cuatro fixtures para las pruebas; mantenerlos en debug, no en main. |
| ☐ | `src/test/java/cruxui/android/lib/` | Pruebas unitarias; copiar por subcarpetas. |
| ☐ | `src/androidTest/java/cruxui/android/lib/` | Tres archivos de pruebas de interacción y paridad. |
| ☐ | `src/androidTest/AndroidManifest.xml` | Habilita RTL en el APK de pruebas. |

No copiar `build/generated` a `src/main`: sus clases y recursos se recrean al compilar. Si la estructura antigua tenía exportaciones copiadas manualmente, retíralas cuando coincidan con las salidas nuevas para evitar duplicados. No borres recursos exclusivos del trabajo.

La aplicación del trabajo mantiene sus pantallas. Para consumir la librería usa su dependencia real, por ejemplo `implementation(project(":lib"))`. El módulo `app` de esta computadora es una demo; no reemplaces con él la app del trabajo. Puedes trasladar los ejemplos que necesites y actualizar sus imports.

## 7. Archivos antiguos que no debes conservar como implementación manual

Las siguientes rutas son relativas a `src/main/java/cruxui/android/lib/` del módulo del trabajo. Si existen, compara y retira su implementación anterior al completar la copia:

| Ruta anterior | Qué ocurre ahora |
| --- | --- |
| `semantics/CorporateIcons.kt` | Alias generado durante el build. |
| `semantics/CorporateTypography.kt` | Alias generado durante el build. |
| `tokens/icons/AlertsIcons.kt`, `ArrowIcons.kt`, `ExtensionIcons.kt`, `GeneralIcons.kt`, `MoneyIcons.kt`, `PeopleIcons.kt` | Aliases de compatibilidad generados; los valores viven en `atoms/icons`. |
| `theme/LibraryThemeSet.kt` | La declaración actual vive en `theme/LibraryThemes.kt`; mantener ambos duplica el tipo. |
| `wrappers/components/textviews/core/TextViewDefaults.kt` | Defaults y medidas compartidos en `core/components/textviews`. |
| `wrappers/core/render/CompoundDrawableRenderer.kt` | La implementación actual está en `wrappers/capabilities/compounddrawables`. |
| `wrappers/core/theme/AndroidThemeSnapshot.kt` | Se usa el contrato compartido `LibraryThemeSnapshot`. |
| `wrappers/core/tokens/ViewTokenValues.kt` | Sustituido por archivos de fuentes de valores separados en `wrappers/core/tokens`. |

Si existe `src/main/res/color/text_view_demo_states.xml`, ya no forma parte de producción. Los fixtures actuales están en `src/debug/res/`.

**No elimines toda la carpeta `wrappers/core/behavior`:** conserva archivos vigentes, incluidas fachadas de compatibilidad. Usa el inventario actual. Tampoco retires todos los imports `Corporate*` indiscriminadamente: algunos se mantienen mediante aliases generados.

Los aliases preservan usos habituales de código fuente Kotlin, pero no garantizan compatibilidad binaria ni Java con una librería anterior. Recompila la app y los consumidores; revisa usos Java si los hay.

## 8. Puntos de comprobación: cuándo compilar

Ejecuta los comandos desde la raíz del proyecto del trabajo. En Windows usa `gradlew.bat` (en PowerShell, `.\gradlew.bat`); en macOS/Linux, `./gradlew`. Sustituye `lib` por el módulo real.

### A. Comprobar que Gradle reconoce el módulo

Cuando hayas integrado la configuración de Gradle:

```sh
./gradlew projects
```

Debe aparecer `:token-exporter` junto a la librería. La sincronización puede requerir descargar plugins. Si falla por repositorios o versiones, resuelve eso antes de interpretar errores de código.

### B. Generar y probar el catálogo

Cuando estén completos `tokens`, `atoms`, `theme` y todos los archivos del exportador, y no queden los tipos manuales retirados:

```sh
./gradlew :token-exporter:generateCatalogApi :token-exporter:test
```

Deben generarse fuentes bajo `token-exporter/build/generated/catalogApi/`. Algunas APIs faltantes antes de este punto son esperables; no las reconstruyas manualmente.

### C. Exportar recursos Android

Cuando también hayas trasladado todos los recursos físicos de iconos y fuentes:

```sh
./gradlew :lib:generateLibraryTokens
```

Verifica `<modulo>/build/generated/libraryTokens/kotlin/cruxui/android/lib/` y `<modulo>/build/generated/libraryTokens/res/`. Deben existir colecciones, aliases, roles, referencias Kotlin y recursos XML.

### D. Compilar la librería completa

Solo después de copiar todo el código, recursos, limpieza y configuración:

```sh
./gradlew :lib:assembleDebug :lib:assembleRelease
```

Es el primer punto para exigir que compile toda la librería. No borres imports ni funciones nuevas para intentar compilar un lote incompleto.

### E. Verificación final desde cero

Después de integrar también pruebas y sus dependencias:

```sh
./gradlew clean :token-exporter:test :lib:testDebugUnitTest :lib:lintDebug :lib:assembleRelease :lib:assembleDebugAndroidTest
```

Compila además el módulo de la aplicación del trabajo, por ejemplo `./gradlew :app:assembleDebug`, ajustando variantes si utiliza flavors. No uses `--offline` como primera opción en una computadora que todavía no tiene las dependencias.

Con un emulador o dispositivo disponible:

```sh
./gradlew :lib:connectedDebugAndroidTest
```

Si Gradle no puede obtener UTP pero ya tienes el APK compilado, puedes ejecutar directamente el APK de pruebas. Ajusta el nombre del APK a la salida real del módulo:

```sh
adb install -r lib/build/outputs/apk/androidTest/debug/lib-debug-androidTest.apk
adb shell am instrument -w -r cruxui.android.lib.test/androidx.test.runner.AndroidJUnitRunner
```

El último comando debe terminar con `OK (8 tests)` para la suite actual. Compilar el APK no significa que esas pruebas se hayan ejecutado. El paquete de pruebas indicado presupone el namespace por defecto; si el trabajo declara `testApplicationId`, usa su valor real.

**Referencia local tras el cambio de paquete:** 14 pruebas del exportador + 62 de la librería aprobadas, 8 instrumentadas aprobadas en API 36, lint con 0 errores, 22 advertencias y 1 sugerencia. AAR release y app debug compilados, app iniciada. Estos resultados corresponden a esta computadora; hay que repetir la verificación en el proyecto del trabajo.

## 9. Pasar la documentación sin perder la estructura

Crea las mismas carpetas en la raíz del proyecto del trabajo, fuera de `src`:

1. **Esta guía:** `transferencias/GUIA-TRANSFERENCIA.html` y, si quieres editarla, su `.md`.
2. **Arquitectura vigente:** copiar los siete `.md` de `arquitectura-xml-compose/`. Léelos en orden: problema → arquitectura → TextView → responsabilidades → tipografía → colores/iconos → paridad.
3. **Exportador:** `token-exporter/README.md`, junto a su `build.gradle.kts`.
4. **Referencias de diseño:** `referencias-arquitectura-xml/` completo. Son contexto y fundamentos; para implementar, manda la documentación vigente.
5. **Resumen para el jefe:** `entregables/Maracuya-avances-XML-Compose.html`. Es autónomo: contiene documentos y capturas, no necesita las carpetas originales para abrirse. Refleja el corte del informe anterior, no los resultados nuevos de esta migración.
6. **README raíz:** integrar en el README del trabajo los enlaces y la sección del paquete; conservar su documentación propia.
7. **Documentación junto al código:** copiar también los `.md` internos de las carpetas de componentes; están enumerados en el inventario.
8. **Guía de app de ejemplo:** `app/src/main/java/com/example/myfirstapplication/guia_verificacion_lib.md` como referencia opcional, por ejemplo en `docs/guia-verificacion-lib.md` del trabajo. Sus rutas de la app de demostración deben adaptarse.

Conservar nombres y posiciones de las carpetas mantiene los enlaces relativos entre Markdown. Copia los documentos como archivos completos; los bloques Kotlin/XML deben conservar sus delimitadores y saltos de línea. La guía HTML y el informe HTML pueden abrirse sin JFG ni otro visor.

## 10. Problemas frecuentes durante el traslado

| Síntoma | Revisar primero |
| --- | --- |
| No aparece `:token-exporter` | `include(":token-exporter")`, ubicación junto al módulo y sincronización de Gradle. |
| No se reconoce `libs.plugins.kotlin.jvm` | Alias en `[plugins]` del TOML y versión Kotlin compartida. |
| El exportador no encuentra `Library*` | Las dos rutas `kotlin.srcDir` y que ya copiaste `atoms`, `tokens` y `theme`. |
| `LibraryTypography`, `LibraryIcons`, roles o `LibraryAssetResources` no resueltos | Ejecutar la generación y verificar que `androidComponents.onVariants` registra las fuentes generadas. |
| `Redeclaration`, clases o recursos duplicados | Tipos antiguos de semantics/iconos/tema o copias manuales de `build/generated` dentro de `src`. |
| Asset inexistente o `resourceName` inválido | Falta un drawable o una fuente; revisar nombres y copiar los recursos restantes. |
| Fallo al inflar un XML | Nombre completo de clase, `attrs.xml`, tipos retirados, recetas registradas y overlay de tema. |
| El generador funciona, pero wrappers o UI no compilan | Lotes incompletos de `core`, `compositions`, `utils`, wrappers o componentes Compose. |
| `Inconsistent JVM-target` o target 19 no soportado | Alinear target Java/Kotlin y JDK que usa Gradle en todos los módulos implicados. |
| Error de descarga de plugin/dependencia | Configuración de repositorios del trabajo y disponibilidad del artefacto; no es un fallo del paquete. |
| La prueba de dependencias no localiza wrappers | Revisar las rutas en `WrapperDependencyRulesTest`; se ejecuta desde el módulo, con fallback para `maracuya` si se ejecuta desde la raíz. Adaptar ese fallback si hace falta. |
| XML y Compose se ven distintos | Tema equivalente, mismas recetas, medidas explícitas, fuente e iconos; comprobar con la galería de paridad. |

## 11. Checklist de cierre

- [ ] Todos los lotes de código, recursos y exportador copiados.
- [ ] Sin implementaciones manuales antiguas duplicadas.
- [ ] Ningún import, declaración de paquete o clase en XML usa `cruxui.android.maracuya`.
- [ ] Exportador y generación Android funcionan desde una compilación limpia.
- [ ] Librería, aplicación y pruebas compiladas en el trabajo.
- [ ] Pruebas ejecutadas y resultados revisados; galería XML/Compose comprobada.
- [ ] Personalizaciones corporativas reintegradas y verificadas.
- [ ] Documentación guardada y HTML abierto en la computadora del trabajo.
- [ ] Commit final de la migración en el proyecto del trabajo.

## Anexo A. build.gradle.kts completo de Token Exporter

Ejemplo listo para un módulo Android cuya **carpeta física sea `lib`**. Si se llama distinto, adapta ambas rutas `../lib/src/main/java`.

```kotlin
plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

// Compila las fuentes puras del DS: no analiza texto Kotlin ni duplica valores.
kotlin.sourceSets.main {
    kotlin.srcDir("../lib/src/main/java")
    kotlin.include("cruxui/android/lib/atoms/**")
    kotlin.include("cruxui/android/lib/tokens/**")
    kotlin.include("cruxui/android/lib/semantics/**")
    listOf("LibraryThemes", "LibraryThemeSnapshot", "ThemeStyle", "FontResources", "FontAssetCatalog").forEach {
        kotlin.include("cruxui/android/lib/theme/$it.kt")
    }
    kotlin.include("cruxui/android/lib/theme/catalog/**")
    kotlin.include("cruxui/android/lib/theme/collections/**")
    kotlin.include("cruxui/android/lib/wrappers/core/tokens/*Role.kt")
    kotlin.include("exporter/**")
}

// Fase independiente: catálogos Library y tokens puros; no depende de temas ni de APIs generadas.
val catalogSchema by sourceSets.creating
kotlin.sourceSets.named("catalogSchema") {
    kotlin.srcDir("../lib/src/main/java")
    kotlin.include("schema/**")
    kotlin.include("cruxui/android/lib/atoms/**")
    kotlin.include("cruxui/android/lib/tokens/base/**")
    kotlin.include("cruxui/android/lib/tokens/colors/**")
    kotlin.include("cruxui/android/lib/tokens/fonts/**")
}
configurations[catalogSchema.implementationConfigurationName].extendsFrom(configurations.implementation.get())

val catalogSources = layout.buildDirectory.dir("generated/catalogApi/kotlin")
val catalogResources = layout.buildDirectory.dir("generated/catalogApi/resources")
val generateCatalogApi = tasks.register<JavaExec>("generateCatalogApi") {
    description = "Genera colecciones, consultas y roles de colores, tipografías e iconos desde Library."
    classpath = catalogSchema.runtimeClasspath
    mainClass.set("schema.GenerateCatalogApiKt")
    outputs.dirs(catalogSources, catalogResources)
    args(catalogSources.get().asFile.absolutePath, catalogResources.get().asFile.absolutePath)
}
kotlin.sourceSets.main { kotlin.srcDir(catalogSources) }
sourceSets.main { resources.srcDir(catalogResources) }
tasks.named("compileKotlin") { dependsOn(generateCatalogApi) }
tasks.named("processResources") { dependsOn(generateCatalogApi) }

application { mainClass.set("exporter.ExportLibraryTokensKt") }
kotlin.compilerOptions.jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_19)
java {
    sourceCompatibility = JavaVersion.VERSION_19
    targetCompatibility = JavaVersion.VERSION_19
}

// Las pruebas de exportación no requieren Android ni un dispositivo.
dependencies { testImplementation(libs.junit) }

// Prueba los escritores de la fase inicial sin incluirlos en el artefacto de ejecución.
kotlin.sourceSets.test { kotlin.srcDir("src/catalogSchema/kotlin") }

```

## Anexo B. build.gradle.kts actual de la librería Android

Referencia completa para integrar plugins, dependencias, opciones y generación. Conserva la publicación, repositorios y configuración específica del trabajo. No pegues un segundo bloque de la misma tarea si ya lo incorporaste.

```kotlin
import javax.inject.Inject
import org.gradle.process.ExecOperations

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "cruxui.android.lib"
    compileSdk = 36


    //Esto garantiza que todos los archivos dentro de src/main/java/... sean visibles.
    sourceSets {
        getByName("main") {
            java.srcDirs("src/main/java")
        }
    }

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // --- 🔹 HABILITAMOS COMPOSE ---
    buildFeatures{
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }

    // El plugin Kotlin Compose configura el compilador con la versión del catálogo.
    kotlinOptions {
        jvmTarget = "19"
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // --- 🔹 Jetpack Compose (usando catálogo y BOM) ---
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime)

    // --- 🔹 Solo en debug (opcional) ---
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // --- 🔹 Tests ---
    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
}

// Proyección de las fuentes del tema durante el build, independiente de la compilación Android.
val tokenExporter by configurations.creating
dependencies { tokenExporter(project(":token-exporter")) }

/**
 * s4121779 - Krause Kelsen
 *
 * Descripción:
 * GenerateLibraryTokens ejecuta el exportador JVM y publica recursos y referencias Kotlin del DS.
 *
 * Justificación:
 * Conecta las salidas del catálogo con cada variante Android sin duplicar valores en archivos manuales.
 * Gradle comprueba el classpath y los assets de entrada para regenerar solo cuando corresponde.
 *
 * Uso:
 * Tarea interna del build. androidComponents registra sus salidas; el host consume los recursos generados.
 *
 * Objetivos:
 * - Mantener Library como fuente única para Compose y XML.
 * - Eliminar salidas obsoletas cuando se retiran tokens.
 *
 * @author Krause Kelsen
 * @since 07-16-2026
 * @version 0.0.13
 * @param exec Servicio Gradle para ejecutar el exportador en una JVM independiente.
 */
abstract class GenerateLibraryTokens @Inject constructor(private val exec: ExecOperations) : DefaultTask() {
    @get:Classpath abstract val exporterClasspath: ConfigurableFileCollection
    @get:InputDirectory @get:PathSensitive(PathSensitivity.RELATIVE) abstract val assets: DirectoryProperty
    @get:OutputDirectory abstract val resources: DirectoryProperty
    @get:OutputDirectory abstract val sources: DirectoryProperty

    /** Regenera únicamente las salidas propias para no conservar recursos de tokens eliminados. */
    @TaskAction fun generate() {
        resources.get().asFile.deleteRecursively()
        sources.get().asFile.deleteRecursively()
        exec.javaexec {
            classpath(exporterClasspath)
            mainClass.set("exporter.ExportLibraryTokensKt")
            args(resources.get().asFile.absolutePath, sources.get().asFile.absolutePath, assets.get().asFile.absolutePath)
        }.assertNormalExitValue()
    }
}

val generateLibraryTokens = tasks.register<GenerateLibraryTokens>("generateLibraryTokens") {
    exporterClasspath.from(tokenExporter)
    assets.set(layout.projectDirectory.dir("src/main/res"))
    resources.set(layout.buildDirectory.dir("generated/libraryTokens/res"))
    sources.set(layout.buildDirectory.dir("generated/libraryTokens/kotlin"))
}

androidComponents.onVariants { variant ->
    variant.sources.res?.addGeneratedSourceDirectory(generateLibraryTokens, GenerateLibraryTokens::resources)
    variant.sources.java?.addGeneratedSourceDirectory(generateLibraryTokens, GenerateLibraryTokens::sources)
}

```

## Anexo C. Catálogo de versiones y dependencias de referencia

Integrar dentro de las secciones existentes de `gradle/libs.versions.toml`. No duplicar `[versions]`, `[libraries]` ni `[plugins]`. Conservar aliases específicos del trabajo.

```toml
[versions]
agp = "8.13.0"
kotlin = "2.0.21"
coreKtx = "1.17.0"
junit = "4.13.2"
robolectric = "4.17"
junitVersion = "1.3.0"
espressoCore = "3.7.0"
lifecycleRuntimeKtx = "2.9.4"
activityCompose = "1.11.0"
composeBom = "2024.09.00"
appcompat = "1.7.1"
material = "1.13.0"
runtime = "1.10.5"
material3 = "1.4.0"
runtimeVersion = "1.10.6"

[libraries]
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "coreKtx" }
junit = { group = "junit", name = "junit", version.ref = "junit" }
robolectric = { group = "org.robolectric", name = "robolectric", version.ref = "robolectric" }
androidx-junit = { group = "androidx.test.ext", name = "junit", version.ref = "junitVersion" }
androidx-espresso-core = { group = "androidx.test.espresso", name = "espresso-core", version.ref = "espressoCore" }
androidx-lifecycle-runtime-ktx = { group = "androidx.lifecycle", name = "lifecycle-runtime-ktx", version.ref = "lifecycleRuntimeKtx" }
androidx-activity-compose = { group = "androidx.activity", name = "activity-compose", version.ref = "activityCompose" }
androidx-compose-bom = { group = "androidx.compose", name = "compose-bom", version.ref = "composeBom" }
androidx-compose-ui = { group = "androidx.compose.ui", name = "ui" }
androidx-compose-ui-graphics = { group = "androidx.compose.ui", name = "ui-graphics" }
androidx-compose-ui-tooling = { group = "androidx.compose.ui", name = "ui-tooling" }
androidx-compose-ui-tooling-preview = { group = "androidx.compose.ui", name = "ui-tooling-preview" }
androidx-compose-ui-test-manifest = { group = "androidx.compose.ui", name = "ui-test-manifest" }
androidx-compose-ui-test-junit4 = { group = "androidx.compose.ui", name = "ui-test-junit4" }
androidx-compose-material3 = { group = "androidx.compose.material3", name = "material3" }
androidx-appcompat = { group = "androidx.appcompat", name = "appcompat", version.ref = "appcompat" }
material = { group = "com.google.android.material", name = "material", version.ref = "material" }
androidx-compose-runtime = { group = "androidx.compose.runtime", name = "runtime", version.ref = "runtime" }
androidx-material3 = { group = "androidx.compose.material3", name = "material3", version.ref = "material3" }
androidx-runtime = { group = "androidx.compose.runtime", name = "runtime", version.ref = "runtimeVersion" }

[plugins]
android-application = { id = "com.android.application", version.ref = "agp" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
kotlin-compose = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
android-library = { id = "com.android.library", version.ref = "agp" }

kotlin-jvm = { id = "org.jetbrains.kotlin.jvm", version.ref = "kotlin" }

```

## Anexo D. Inventario exacto para comprobar cada correo

Rutas de origen de esta computadora. Para las entradas de `maracuya/`, sustituye ese prefijo por la carpeta del módulo del trabajo. Las entradas de `token-exporter/` mantienen su ruta. No se incluyen `build`, `.gradle`, `.idea`, `.DS_Store`, cachés ni archivos de configuración local de la computadora.


### Código de la librería (251 archivos)


**`maracuya/src/main/java/cruxui/android/lib/atoms/`**

- [ ] `LibraryColorTokens.kt`
- [ ] `LibraryIconTokens.kt`
- [ ] `LibraryTypographyTokens.kt`

**`maracuya/src/main/java/cruxui/android/lib/atoms/icons/`**

- [ ] `LibraryAlertsIconsTokens.kt`
- [ ] `LibraryArrowIconsTokens.kt`
- [ ] `LibraryExtensionTokens.kt`
- [ ] `LibraryGeneralIconsTokens.kt`
- [ ] `LibraryMoneyIconsTokens.kt`
- [ ] `LibraryPeopleIconsTokens.kt`

**`maracuya/src/main/java/cruxui/android/lib/compositions/`**

- [ ] `LocalFontFamily.kt`
- [ ] `LocalLibraryColorTokens.kt`
- [ ] `LocalLibraryIcons.kt`
- [ ] `LocalLibraryTypography.kt`

**`maracuya/src/main/java/cruxui/android/lib/core/components/textviews/behavior/`**

- [ ] `TextViewBehavior.kt`

**`maracuya/src/main/java/cruxui/android/lib/core/components/textviews/tokens/`**

- [ ] `TextViewLayoutTokens.kt`
- [ ] `TextViewTokenDefaults.kt`
- [ ] `TextViewTokens.kt`

**`maracuya/src/main/java/cruxui/android/lib/core/components/textviews/tokensrender/`**

- [ ] `TextViewTokensRender.kt`
- [ ] `TextViewTokensRenderResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/core/components/textviews/variant/`**

- [ ] `TextViewVariant.kt`

**`maracuya/src/main/java/cruxui/android/lib/core/text/`**

- [ ] `TextInteraction.kt`

**`maracuya/src/main/java/cruxui/android/lib/theme/`**

- [ ] `FontAssetCatalog.kt`
- [ ] `FontResourceCatalog.kt`
- [ ] `FontResources.kt`
- [ ] `LibraryTheme.kt`
- [ ] `LibraryThemeSnapshot.kt`
- [ ] `LibraryThemes.kt`
- [ ] `MyLibraryTheme.kt`
- [ ] `ThemeExports.kt`
- [ ] `ThemeStyle.kt`

**`maracuya/src/main/java/cruxui/android/lib/tokens/base/`**

- [ ] `ColorToken.kt`
- [ ] `FontFamilyToken.kt`
- [ ] `IconToken.kt`
- [ ] `LibraryIconGroup.kt`
- [ ] `TypographyToken.kt`

**`maracuya/src/main/java/cruxui/android/lib/tokens/colors/`**

- [ ] `DefaultColors.kt`

**`maracuya/src/main/java/cruxui/android/lib/tokens/fonts/`**

- [ ] `FontSizeTokens.kt`
- [ ] `FontStyleTokens.kt`
- [ ] `FontWeightTokens.kt`
- [ ] `LetterSpacingTokens.kt`
- [ ] `LineHeightTokens.kt`
- [ ] `TextAlignTokens.kt`

**`maracuya/src/main/java/cruxui/android/lib/tokens/spacings/`**

- [ ] `ChipChoiceSpacings.kt`
- [ ] `InputCheckBoxSpacings.kt`
- [ ] `InputFieldBasicSpacings.kt`
- [ ] `InteractionSpacings.kt`
- [ ] `LabelSpacings.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/chipchoice/`**

- [ ] `ChipChoiceBaseMrcy.kt`
- [ ] `ChipChoiceBaseTokens.kt`
- [ ] `ChipChoiceBaseTokensResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/chipchoice/carousel/`**

- [ ] `ChipChoiceCarouselMrcy.kt`
- [ ] `ChipChoiceCarouselTokens.kt`
- [ ] `ChipChoiceCarouselTokensResolver.kt`
- [ ] `ChipChoiceModels.kt`
- [ ] `ChipChoiceSelectionState.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/navigation/group/`**

- [ ] `ButtonNavigationGroupArchitecture.md`
- [ ] `ButtonNavigationGroupMrcy.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/navigation/group/behavior/`**

- [ ] `ButtonNavigationGroupBehavior.kt`
- [ ] `ButtonNavigationGroupBehaviorResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/navigation/group/child/`**

- [ ] `ButtonNavigationChildMrcy.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/navigation/group/child/core/`**

- [ ] `ButtonNavigationChild.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/navigation/group/child/tokens/`**

- [ ] `ButtonNavigationChildTokens.kt`
- [ ] `ButtonNavigationChildTokensResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/navigation/group/child/tokensrender/`**

- [ ] `ButtonNavigationChildTokensRender.kt`
- [ ] `ButtonNavigationChildTokensRenderResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/navigation/group/child/variant/`**

- [ ] `ButtonNavigationChildVariant.kt`
- [ ] `ButtonNavigationChildVariantResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/navigation/group/core/`**

- [ ] `ButtonNavigationGroupDefaults.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/navigation/group/tokens/`**

- [ ] `ButtonNavigationGroupTokens.kt`
- [ ] `ButtonNavigationGroupTokensResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/navigation/group/tokensrender/`**

- [ ] `ButtonNavigationGroupTokensRender.kt`
- [ ] `ButtonNavigationGroupTokensRenderResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/navigation/group/variant/`**

- [ ] `ButtonNavigationGroupVariant.kt`
- [ ] `ButtonNavigationGroupVariantResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/navigation/simple/`**

- [ ] `ButtonNavigationArchitecture.md`
- [ ] `ButtonNavigationContent.kt`
- [ ] `ButtonNavigationMrcy.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/navigation/simple/behavior/`**

- [ ] `ButtonNavigationBehavior.kt`
- [ ] `ButtonNavigationBehaviorResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/navigation/simple/core/`**

- [ ] `ButtonNavigationLoadingScope.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/navigation/simple/tokens/`**

- [ ] `ButtonNavigationTokens.kt`
- [ ] `ButtonNavigationTokensResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/navigation/simple/tokensrender/`**

- [ ] `ButtonNavigationTokensRender.kt`
- [ ] `ButtonNavigationTokensRenderResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/navigation/simple/variant/`**

- [ ] `ButtonNavigationVariant.kt`
- [ ] `ButtonNavigationVariantResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/simple/`**

- [ ] `ButtonContent.kt`
- [ ] `ButtonMrcy.kt`
- [ ] `ButtonTokens.kt`
- [ ] `ButtonTokensResolver.kt`
- [ ] `ButtonVariant.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/checkboxes/`**

- [ ] `CheckBoxMrcy.kt`
- [ ] `CheckBoxTokens.kt`
- [ ] `CheckBoxTokensResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/dropdowns/`**

- [ ] `DropDownContent.kt`
- [ ] `DropDownMrcy.kt`
- [ ] `DropDownTokens.kt`
- [ ] `DropDownTokensResolver.kt`
- [ ] `DropDownVariant.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/inputs/basic/`**

- [ ] `InputFieldBasicMrcy.kt`
- [ ] `InputFieldBasicTokens.kt`
- [ ] `InputFieldBasicTypes.kt`
- [ ] `InputFieldBasicWidthType.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/inputs/checkbox/`**

- [ ] `InputCheckBoxMrcy.kt`
- [ ] `InputCheckBoxTokens.kt`
- [ ] `InputCheckBoxTokensGroup.kt`
- [ ] `InputCheckBoxTokensResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/inputs/config/`**

- [ ] `InputDropDownTokenGroup.kt`
- [ ] `InputFieldBasicTokenGroup.kt`
- [ ] `InputFieldBasicTokensResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/inputs/dropdown/`**

- [ ] `InputDropDownMrcy.kt`
- [ ] `InputDropDownTokenGroup.kt`
- [ ] `InputDropDownTokens.kt`
- [ ] `InputDropDownTokensResolver.kt`
- [ ] `InputDropDownVariant.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/labels/`**

- [ ] `LabelMrcy.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/labels/behavior/`**

- [ ] `LabelBehavior.kt`
- [ ] `LabelBehaviorResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/labels/tokens/`**

- [ ] `LabelTokens.kt`
- [ ] `LabelTokensResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/labels/variant/`**

- [ ] `LabelVariant.kt`
- [ ] `LabelVariantResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/layouts/`**

- [ ] `BackgroundLayoutSpec.kt`
- [ ] `BackgroundPilotWellkommen.kt`
- [ ] `BackgroundVectorLayout.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/textfields/`**

- [ ] `BottomTextMrcy.kt`
- [ ] `TextFieldBehavior.kt`
- [ ] `TextFieldBehaviorResolver.kt`
- [ ] `TextFieldMrcy.kt`
- [ ] `TextFieldTokens.kt`
- [ ] `TextFieldTokensResolver.kt`
- [ ] `TextFieldVariant.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/texts/`**

- [ ] `LegalText.kt`
- [ ] `LegalTextTokens.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/textviews/`**

- [ ] `TextView.kt`
- [ ] `TextViewArchitecture.md`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/textviews/behavior/`**

- [ ] `TextViewBehavior.kt`
- [ ] `TextViewBehaviorResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/textviews/content/`**

- [ ] `TextViewContent.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/textviews/tokens/`**

- [ ] `TextViewTokens.kt`
- [ ] `TextViewTokensResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/textviews/tokensrender/`**

- [ ] `TextViewTokensRender.kt`
- [ ] `TextViewTokensRenderResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/textviews/variant/`**

- [ ] `TextViewVariant.kt`
- [ ] `TextViewVariantResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/components/utils/`**

- [ ] `IconPosition.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/previews/`**

- [ ] `ButtonNavigationGroupPreview.kt`
- [ ] `ButtonNavigationPreview.kt`
- [ ] `ButtonPreview.kt`
- [ ] `CheckBoxPreview.kt`
- [ ] `ChipChoiceCarouselPreview.kt`
- [ ] `DropDownPreview.kt`
- [ ] `InputFieldBasicPreview.kt`
- [ ] `LabelPreview.kt`
- [ ] `LayoutPilotWellkommenPreview.kt`
- [ ] `LegalTextPreview.kt`
- [ ] `TextFieldPreview.kt`
- [ ] `TextViewPreview.kt`

**`maracuya/src/main/java/cruxui/android/lib/ui/previews/core/`**

- [ ] `Mode.kt`
- [ ] `PreviewWrapper.kt`

**`maracuya/src/main/java/cruxui/android/lib/utils/`**

- [ ] `InteractionStates.kt`
- [ ] `LibraryThemeManager.kt`

**`maracuya/src/main/java/cruxui/android/lib/utils/composeadapters/`**

- [ ] `ColorComposeAdapter.kt`
- [ ] `FontFamiliesComposeAdapter.kt`
- [ ] `IconComposeAdapter.kt`
- [ ] `TypographyComposeAdapter.kt`

**`maracuya/src/main/java/cruxui/android/lib/utils/materialadapters/`**

- [ ] `ColorMaterialAdapter.kt`
- [ ] `TypographyMaterialAdapter.kt`

**`maracuya/src/main/java/cruxui/android/lib/wrappers/adapters/compose/`**

- [ ] `AndroidLayoutParamsModifierResolver.kt`
- [ ] `ComposeOverrideResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/wrappers/capabilities/compounddrawables/`**

- [ ] `CompoundDrawableBehaviorController.kt`
- [ ] `CompoundDrawableGeometry.kt`
- [ ] `CompoundDrawableRenderer.kt`
- [ ] `IconCallbacks.kt`
- [ ] `InlineCompoundDrawableLayout.kt`
- [ ] `OffsetCompoundDrawable.kt`

**`maracuya/src/main/java/cruxui/android/lib/wrappers/capabilities/text/`**

- [ ] `NativeTextStyle.kt`
- [ ] `NativeTextStyleAttributeReader.kt`
- [ ] `NativeTextStyleOverrides.kt`
- [ ] `NativeTextStyleRenderer.kt`
- [ ] `NativeTextStyleResolver.kt`
- [ ] `NativeTextTouchTarget.kt`
- [ ] `TextAlignmentRenderer.kt`

**`maracuya/src/main/java/cruxui/android/lib/wrappers/components/buttons/navigation/group/`**

- [ ] `ButtonNavigationGroupAttributeParser.kt`
- [ ] `ButtonNavigationGroupAttributes.kt`
- [ ] `ButtonNavigationGroupTokensOverrideRegistry.kt`
- [ ] `ButtonNavigationGroupWrp.kt`

**`maracuya/src/main/java/cruxui/android/lib/wrappers/components/buttons/navigation/simple/`**

- [ ] `ButtonNavigationAttributeParser.kt`
- [ ] `ButtonNavigationAttributes.kt`
- [ ] `ButtonNavigationTokensOverrideRegistry.kt`
- [ ] `ButtonNavigationWrp.kt`

**`maracuya/src/main/java/cruxui/android/lib/wrappers/components/buttons/simple/`**

- [ ] `ButtonAttributeParser.kt`
- [ ] `ButtonAttributes.kt`
- [ ] `ButtonTokensOverrideRegistry.kt`
- [ ] `ButtonWrp.kt`

**`maracuya/src/main/java/cruxui/android/lib/wrappers/components/labels/`**

- [ ] `LabelAttributeParser.kt`
- [ ] `LabelAttributes.kt`
- [ ] `LabelTokensOverrideRegistry.kt`
- [ ] `LabelWrp.kt`

**`maracuya/src/main/java/cruxui/android/lib/wrappers/components/textfields/`**

- [ ] `TextFieldAttributeParser.kt`
- [ ] `TextFieldAttributes.kt`
- [ ] `TextFieldTokensOverrideRegistry.kt`
- [ ] `TextFieldWrp.kt`

**`maracuya/src/main/java/cruxui/android/lib/wrappers/components/textviews/`**

- [ ] `TextView.kt`

**`maracuya/src/main/java/cruxui/android/lib/wrappers/components/textviews/attributes/`**

- [ ] `TextViewAttributeReader.kt`

**`maracuya/src/main/java/cruxui/android/lib/wrappers/components/textviews/behavior/`**

- [ ] `TextViewBehavior.kt`
- [ ] `TextViewBehaviorResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/wrappers/components/textviews/core/`**

- [ ] `TextViewState.kt`

**`maracuya/src/main/java/cruxui/android/lib/wrappers/components/textviews/render/`**

- [ ] `TextViewRenderer.kt`

**`maracuya/src/main/java/cruxui/android/lib/wrappers/components/textviews/tokens/`**

- [ ] `TextViewNativeOverrides.kt`
- [ ] `TextViewOverrideAdapter.kt`
- [ ] `TextViewTokens.kt`
- [ ] `TextViewTokensOverride.kt`
- [ ] `TextViewTokensOverrideRegistry.kt`
- [ ] `TextViewTokensResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/wrappers/components/textviews/tokensrender/`**

- [ ] `TextViewTokensRender.kt`
- [ ] `TextViewTokensRenderResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/wrappers/components/textviews/variant/`**

- [ ] `TextViewVariant.kt`
- [ ] `TextViewVariantResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/wrappers/core/`**

- [ ] `MrcyXmlComposeView.kt`

**`maracuya/src/main/java/cruxui/android/lib/wrappers/core/attributes/`**

- [ ] `NativeAttributeValues.kt`

**`maracuya/src/main/java/cruxui/android/lib/wrappers/core/behavior/`**

- [ ] `CompoundDrawableBehaviorController.kt`
- [ ] `ViewGestureController.kt`

**`maracuya/src/main/java/cruxui/android/lib/wrappers/core/registry/`**

- [ ] `TokensOverrideRegistry.kt`
- [ ] `TokensOverrideSource.kt`

**`maracuya/src/main/java/cruxui/android/lib/wrappers/core/render/`**

- [ ] `SurfaceRenderer.kt`

**`maracuya/src/main/java/cruxui/android/lib/wrappers/core/resources/`**

- [ ] `AndroidFontLoader.kt`
- [ ] `AndroidIconLoader.kt`
- [ ] `AndroidTokenAdapter.kt`
- [ ] `ColorSourceResolver.kt`
- [ ] `DimensionResolver.kt`
- [ ] `FittedIconDrawable.kt`
- [ ] `FontSourceResolver.kt`
- [ ] `IconSourceResolver.kt`
- [ ] `SurfaceResolver.kt`
- [ ] `TypographySourceResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/wrappers/core/theme/`**

- [ ] `AndroidThemeResolver.kt`

**`maracuya/src/main/java/cruxui/android/lib/wrappers/core/tokens/`**

- [ ] `ComponentTokenBinding.kt`
- [ ] `TextColor.kt`
- [ ] `TextDimension.kt`
- [ ] `TextFont.kt`
- [ ] `TextLineSpacing.kt`
- [ ] `TextTypography.kt`
- [ ] `ViewIcon.kt`
- [ ] `ViewSurface.kt`

**`maracuya/src/main/java/cruxui/android/lib/wrappers/previews/`**

- [ ] `TextViewGalleryPreview.kt`

**`maracuya/src/main/java/cruxui/android/lib/wrappers/theme/`**

- [ ] `MyLibraryThemeAttributeParser.kt`
- [ ] `MyLibraryThemeConfig.kt`
- [ ] `MyLibraryThemeMapper.kt`
- [ ] `MyLibraryThemeProvider.kt`
- [ ] `MyLibraryThemeWrp.kt`

### Recursos de producción (430 archivos)


**`maracuya/src/main/res/drawable/`**

- [ ] `bg_pilot_wellkommen_shape.xml`
- [ ] `biometricsdisclamerscreen_imagepage.png`
- [ ] `ic_accessibility.xml`
- [ ] `ic_accessibility_new.xml`
- [ ] `ic_accessible.xml`
- [ ] `ic_accessible_forward.xml`
- [ ] `ic_accessible_menu.xml`
- [ ] `ic_account_balance_wallet.xml`
- [ ] `ic_account_child_invert.xml`
- [ ] `ic_account_circle.xml`
- [ ] `ic_account_circle_off.xml`
- [ ] `ic_acute.xml`
- [ ] `ic_add.xml`
- [ ] `ic_add_2.xml`
- [ ] `ic_add_a_photo.xml`
- [ ] `ic_add_ad.xml`
- [ ] `ic_add_alert.xml`
- [ ] `ic_add_box.xml`
- [ ] `ic_add_card.xml`
- [ ] `ic_add_chat_on.xml`
- [ ] `ic_add_circle.xml`
- [ ] `ic_add_link.xml`
- [ ] `ic_add_notes.xml`
- [ ] `ic_add_shopping_cart.xml`
- [ ] `ic_add_task.xml`
- [ ] `ic_add_to_photos.xml`
- [ ] `ic_admin_moderator.xml`
- [ ] `ic_admin_panel_settings.xml`
- [ ] `ic_ads_click.xml`
- [ ] `ic_air.xml`
- [ ] `ic_alarm.xml`
- [ ] `ic_alarm_add.xml`
- [ ] `ic_alarm_off.xml`
- [ ] `ic_alarm_on.xml`
- [ ] `ic_alarm_smart_wake.xml`
- [ ] `ic_android_wifi_3_bar.xml`
- [ ] `ic_approval_delegation.xml`
- [ ] `ic_approval_delegation_off.xml`
- [ ] `ic_ar_on_you.xml`
- [ ] `ic_ar_stickers.xml`
- [ ] `ic_archive.xml`
- [ ] `ic_arming_countdown.xml`
- [ ] `ic_arrow_circle_down.xml`
- [ ] `ic_arrow_circle_left.xml`
- [ ] `ic_arrow_circle_right.xml`
- [ ] `ic_arrow_circle_up.xml`
- [ ] `ic_arrow_drop_down.xml`
- [ ] `ic_arrow_drop_up.xml`
- [ ] `ic_arrow_left.xml`
- [ ] `ic_arrow_or_edge.xml`
- [ ] `ic_arrow_range_horiz.xml`
- [ ] `ic_arrow_range_vert.xml`
- [ ] `ic_arrow_right.xml`
- [ ] `ic_arrow_selector_tool.xml`
- [ ] `ic_arrow_split.xml`
- [ ] `ic_arrows_input.xml`
- [ ] `ic_arrows_more_down_left.xml`
- [ ] `ic_arrows_more_down_right.xml`
- [ ] `ic_arrows_more_up_left.xml`
- [ ] `ic_arrows_more_up_right.xml`
- [ ] `ic_arrows_output.xml`
- [ ] `ic_arrows_outward_horiz.xml`
- [ ] `ic_arrows_outward_vert.xml`
- [ ] `ic_article.xml`
- [ ] `ic_article_person.xml`
- [ ] `ic_article_shortcut.xml`
- [ ] `ic_artist.xml`
- [ ] `ic_assist_walker.xml`
- [ ] `ic_asterisk.xml`
- [ ] `ic_attach_email.xml`
- [ ] `ic_attach_file.xml`
- [ ] `ic_attach_file_off.xml`
- [ ] `ic_attachment.xml`
- [ ] `ic_auto_delete.xml`
- [ ] `ic_auto_schedule.xml`
- [ ] `ic_award_star.xml`
- [ ] `ic_baby_changing_station.xml`
- [ ] `ic_back_hand.xml`
- [ ] `ic_backlight_high.xml`
- [ ] `ic_ballot.xml`
- [ ] `ic_bar_chart.xml`
- [ ] `ic_bar_chart_4_bars.xml`
- [ ] `ic_bigtop_updates.xml`
- [ ] `ic_bitcoin.xml`
- [ ] `ic_blind.xml`
- [ ] `ic_block.xml`
- [ ] `ic_bluetooth.xml`
- [ ] `ic_body_system.xml`
- [ ] `ic_bolt.xml`
- [ ] `ic_book_3.xml`
- [ ] `ic_book_4_spark.xml`
- [ ] `ic_bookmark.xml`
- [ ] `ic_bookmark_add.xml`
- [ ] `ic_bookmark_added.xml`
- [ ] `ic_bookmark_heart.xml`
- [ ] `ic_bookmark_manager.xml`
- [ ] `ic_bookmark_star.xml`
- [ ] `ic_border_color.xml`
- [ ] `ic_boy.xml`
- [ ] `ic_build.xml`
- [ ] `ic_business_center.xml`
- [ ] `ic_calendar_add_on.xml`
- [ ] `ic_calendar_check.xml`
- [ ] `ic_calendar_clock.xml`
- [ ] `ic_calendar_lock.xml`
- [ ] `ic_calendar_month.xml`
- [ ] `ic_calendar_today.xml`
- [ ] `ic_cancel.xml`
- [ ] `ic_captive_portal.xml`
- [ ] `ic_card_membership.xml`
- [ ] `ic_card_travel.xml`
- [ ] `ic_cardio_load.xml`
- [ ] `ic_celebration.xml`
- [ ] `ic_chat.xml`
- [ ] `ic_chat_info.xml`
- [ ] `ic_check.xml`
- [ ] `ic_check_circle.xml`
- [ ] `ic_check_circle_unread.xml`
- [ ] `ic_checkbook.xml`
- [ ] `ic_checklist.xml`
- [ ] `ic_cheer.xml`
- [ ] `ic_chrome_reader_mode.xml`
- [ ] `ic_chrome_restore.xml`
- [ ] `ic_clarify.xml`
- [ ] `ic_close.xml`
- [ ] `ic_close_fullscreen.xml`
- [ ] `ic_co_present.xml`
- [ ] `ic_code.xml`
- [ ] `ic_colon.xml`
- [ ] `ic_communication.xml`
- [ ] `ic_conditions.xml`
- [ ] `ic_construction.xml`
- [ ] `ic_contact_page.xml`
- [ ] `ic_contact_support.xml`
- [ ] `ic_content_copy.xml`
- [ ] `ic_content_cut.xml`
- [ ] `ic_content_paste.xml`
- [ ] `ic_content_paste_go.xml`
- [ ] `ic_content_paste_off.xml`
- [ ] `ic_content_paste_search.xml`
- [ ] `ic_contract.xml`
- [ ] `ic_contract_delete.xml`
- [ ] `ic_contract_edit.xml`
- [ ] `ic_create_new_folder.xml`
- [ ] `ic_credit_card.xml`
- [ ] `ic_credit_card_clock.xml`
- [ ] `ic_credit_card_gear.xml`
- [ ] `ic_credit_card_heart.xml`
- [ ] `ic_credit_card_off.xml`
- [ ] `ic_credit_score.xml`
- [ ] `ic_crisis_alert.xml`
- [ ] `ic_dehaze.xml`
- [ ] `ic_design_services.xml`
- [ ] `ic_directions_run.xml`
- [ ] `ic_directions_walk.xml`
- [ ] `ic_discover_tune.xml`
- [ ] `ic_distance.xml`
- [ ] `ic_diversity_1.xml`
- [ ] `ic_diversity_2.xml`
- [ ] `ic_diversity_3.xml`
- [ ] `ic_docs.xml`
- [ ] `ic_docs_add_on.xml`
- [ ] `ic_dollar.xml`
- [ ] `ic_dollar_circle.xml`
- [ ] `ic_dollar_exchange.xml`
- [ ] `ic_dollar_off.xml`
- [ ] `ic_done_all.xml`
- [ ] `ic_download.xml`
- [ ] `ic_drag_click.xml`
- [ ] `ic_drag_indicator.xml`
- [ ] `ic_draw.xml`
- [ ] `ic_drive_file_rename_outline.xml`
- [ ] `ic_earthquake.xml`
- [ ] `ic_eda.xml`
- [ ] `ic_edit_calendar.xml`
- [ ] `ic_edit_document.xml`
- [ ] `ic_elderly.xml`
- [ ] `ic_elderly_woman.xml`
- [ ] `ic_emergency_home.xml`
- [ ] `ic_emoji_people.xml`
- [ ] `ic_encrypted.xml`
- [ ] `ic_encrypted_add_circle.xml`
- [ ] `ic_encrypted_minus_circle.xml`
- [ ] `ic_encrypted_off.xml`
- [ ] `ic_energy_program_time_used.xml`
- [ ] `ic_equit_square.xml`
- [ ] `ic_error.xml`
- [ ] `ic_euro.xml`
- [ ] `ic_expand_all.xml`
- [ ] `ic_expand_circle_down.xml`
- [ ] `ic_expand_circle_left.xml`
- [ ] `ic_expand_circle_right.xml`
- [ ] `ic_expand_circle_up.xml`
- [ ] `ic_expand_less.xml`
- [ ] `ic_expand_more.xml`
- [ ] `ic_fact_check.xml`
- [ ] `ic_falling.xml`
- [ ] `ic_family_restroom.xml`
- [ ] `ic_feature_search.xml`
- [ ] `ic_file_copy.xml`
- [ ] `ic_file_copy_off.xml`
- [ ] `ic_file_download_done.xml`
- [ ] `ic_filter_alt.xml`
- [ ] `ic_filter_none.xml`
- [ ] `ic_fingerprint.xml`
- [ ] `ic_flash_on.xml`
- [ ] `ic_folder.xml`
- [ ] `ic_folder_check_2.xml`
- [ ] `ic_folder_match.xml`
- [ ] `ic_folder_off.xml`
- [ ] `ic_folder_shared.xml`
- [ ] `ic_folder_supervised.xml`
- [ ] `ic_follow_the_signs.xml`
- [ ] `ic_footprint.xml`
- [ ] `ic_format_color_reset.xml`
- [ ] `ic_format_ink_highlighter.xml`
- [ ] `ic_forward_media.xml`
- [ ] `ic_franc.xml`
- [ ] `ic_glucose.xml`
- [ ] `ic_gpp_bad.xml`
- [ ] `ic_gpp_maybe.xml`
- [ ] `ic_grade.xml`
- [ ] `ic_group.xml`
- [ ] `ic_group_add.xml`
- [ ] `ic_group_auto.xml`
- [ ] `ic_group_remove.xml`
- [ ] `ic_group_search.xml`
- [ ] `ic_guardian.xml`
- [ ] `ic_hail.xml`
- [ ] `ic_hand_bones.xml`
- [ ] `ic_hand_gesture.xml`
- [ ] `ic_hand_gesture_off.xml`
- [ ] `ic_handshake.xml`
- [ ] `ic_health_and_safety.xml`
- [ ] `ic_help.xml`
- [ ] `ic_highlight_keyboard_focus.xml`
- [ ] `ic_hiking.xml`
- [ ] `ic_how_to_reg.xml`
- [ ] `ic_info.xml`
- [ ] `ic_input.xml`
- [ ] `ic_keyboard_arrow_left.xml`
- [ ] `ic_keyboard_arrow_right.xml`
- [ ] `ic_keyboard_backspace_down.xml`
- [ ] `ic_keyboard_backspace_left.xml`
- [ ] `ic_keyboard_backspace_right.xml`
- [ ] `ic_keyboard_backspace_up.xml`
- [ ] `ic_keyboard_double_arrow_down.xml`
- [ ] `ic_keyboard_double_arrow_up.xml`
- [ ] `ic_keyboard_tab_down.xml`
- [ ] `ic_keyboard_tab_left.xml`
- [ ] `ic_keyboard_tab_right.xml`
- [ ] `ic_keyboard_tab_up.xml`
- [ ] `ic_library_add_check.xml`
- [ ] `ic_lift_to_talk.xml`
- [ ] `ic_link.xml`
- [ ] `ic_link_off.xml`
- [ ] `ic_lira.xml`
- [ ] `ic_lock.xml`
- [ ] `ic_lock_reset.xml`
- [ ] `ic_login.xml`
- [ ] `ic_logout.xml`
- [ ] `ic_look_open.xml`
- [ ] `ic_look_open_right.xml`
- [ ] `ic_magic_button.xml`
- [ ] `ic_man.xml`
- [ ] `ic_man_4.xml`
- [ ] `ic_manage_accounts.xml`
- [ ] `ic_manage_search.xml`
- [ ] `ic_massage.xml`
- [ ] `ic_medical_services.xml`
- [ ] `ic_minus_bar.xml`
- [ ] `ic_mode_heat.xml`
- [ ] `ic_mode_heat_off.xml`
- [ ] `ic_money_bag.xml`
- [ ] `ic_money_range.xml`
- [ ] `ic_more_time.xml`
- [ ] `ic_navigate_before.xml`
- [ ] `ic_navigate_next.xml`
- [ ] `ic_network_check.xml`
- [ ] `ic_new_releases.xml`
- [ ] `ic_new_window.xml`
- [ ] `ic_news.xml`
- [ ] `ic_newsmode.xml`
- [ ] `ic_not_accessible.xml`
- [ ] `ic_notification_add.xml`
- [ ] `ic_notification_important.xml`
- [ ] `ic_notification_settings.xml`
- [ ] `ic_notification_sound.xml`
- [ ] `ic_notifications.xml`
- [ ] `ic_notifications_active.xml`
- [ ] `ic_notifications_unread.xml`
- [ ] `ic_open_in_full.xml`
- [ ] `ic_open_in_new.xml`
- [ ] `ic_open_in_new_down.xml`
- [ ] `ic_open_in_new_off.xml`
- [ ] `ic_open_run.xml`
- [ ] `ic_open_with.xml`
- [ ] `ic_output.xml`
- [ ] `ic_passkey.xml`
- [ ] `ic_payment_arrow_down.xml`
- [ ] `ic_payment_card.xml`
- [ ] `ic_payments.xml`
- [ ] `ic_pending.xml`
- [ ] `ic_pending_actions.xml`
- [ ] `ic_perm_contact_calendar.xml`
- [ ] `ic_person.xml`
- [ ] `ic_person_add.xml`
- [ ] `ic_person_alert.xml`
- [ ] `ic_person_cancel.xml`
- [ ] `ic_person_celebrate.xml`
- [ ] `ic_person_edit.xml`
- [ ] `ic_person_heart.xml`
- [ ] `ic_person_injury.xml`
- [ ] `ic_person_off.xml`
- [ ] `ic_person_remove.xml`
- [ ] `ic_person_search.xml`
- [ ] `ic_person_shield.xml`
- [ ] `ic_photo_camera.xml`
- [ ] `ic_pound.xml`
- [ ] `ic_privacy_tip.xml`
- [ ] `ic_production_quantity_limits.xml`
- [ ] `ic_productivity.xml`
- [ ] `ic_psychiatry.xml`
- [ ] `ic_recent_patient.xml`
- [ ] `ic_redeem.xml`
- [ ] `ic_reduce_capacity.xml`
- [ ] `ic_repeat.xml`
- [ ] `ic_ruble.xml`
- [ ] `ic_rupee.xml`
- [ ] `ic_safety_divider.xml`
- [ ] `ic_savings.xml`
- [ ] `ic_send_money.xml`
- [ ] `ic_settings_alert.xml`
- [ ] `ic_shopping_bag.xml`
- [ ] `ic_shopping_bag_speed.xml`
- [ ] `ic_shopping_cart.xml`
- [ ] `ic_shopping_cart_checkout.xml`
- [ ] `ic_shopping_cart_off.xml`
- [ ] `ic_soap.xml`
- [ ] `ic_sprint.xml`
- [ ] `ic_star_shine.xml`
- [ ] `ic_store.xml`
- [ ] `ic_supervised_user_circle.xml`
- [ ] `ic_supervisor_account.xml`
- [ ] `ic_swap_horiz.xml`
- [ ] `ic_swap_vert.xml`
- [ ] `ic_swipe.xml`
- [ ] `ic_swipe_down.xml`
- [ ] `ic_swipe_left.xml`
- [ ] `ic_swipe_right.xml`
- [ ] `ic_swipe_up.xml`
- [ ] `ic_swipe_vertical.xml`
- [ ] `ic_sync.xml`
- [ ] `ic_sync_disabled.xml`
- [ ] `ic_temp_preferences_custom.xml`
- [ ] `ic_thumb_down_off.xml`
- [ ] `ic_thumb_up.xml`
- [ ] `ic_thumbs_up_double.xml`
- [ ] `ic_thumbs_up_down.xml`
- [ ] `ic_toll.xml`
- [ ] `ic_touch_app.xml`
- [ ] `ic_touch_double.xml`
- [ ] `ic_touch_long.xml`
- [ ] `ic_transfer_within_a_station.xml`
- [ ] `ic_travel_explore.xml`
- [ ] `ic_trending_up.xml`
- [ ] `ic_universal_currency.xml`
- [ ] `ic_universal_currency_alt.xml`
- [ ] `ic_upload.xml`
- [ ] `ic_visibility.xml`
- [ ] `ic_visibility_off.xml`
- [ ] `ic_wallet.xml`
- [ ] `ic_warning.xml`
- [ ] `ic_wash.xml`
- [ ] `ic_water_drop.xml`
- [ ] `ic_wc.xml`
- [ ] `ic_yen.xml`
- [ ] `logo_davibank_house_empty.xml`
- [ ] `onetimedisclamerscreen_imagepage.png`

**`maracuya/src/main/res/font/`**

- [ ] `davivienda_bold.ttf`
- [ ] `davivienda_bold_italic.ttf`
- [ ] `davivienda_condensed_bold.ttf`
- [ ] `davivienda_condensed_bold_italic.ttf`
- [ ] `davivienda_condensed_extra_bold.ttf`
- [ ] `davivienda_condensed_extra_bold_italic.ttf`
- [ ] `davivienda_condensed_italic.ttf`
- [ ] `davivienda_condensed_light.ttf`
- [ ] `davivienda_condensed_light_italic.ttf`
- [ ] `davivienda_condensed_regular.ttf`
- [ ] `davivienda_condensed_semi_bold.ttf`
- [ ] `davivienda_condensed_semi_bold_italic.ttf`
- [ ] `davivienda_extra_bold.ttf`
- [ ] `davivienda_extra_bold_italic.ttf`
- [ ] `davivienda_italic.ttf`
- [ ] `davivienda_light.ttf`
- [ ] `davivienda_light_italic.ttf`
- [ ] `davivienda_regular.ttf`
- [ ] `davivienda_semi_bold.ttf`
- [ ] `davivienda_semi_bold_italic.ttf`
- [ ] `mulish_black.ttf`
- [ ] `mulish_black_italic.ttf`
- [ ] `mulish_bold.ttf`
- [ ] `mulish_bold_italic.ttf`
- [ ] `mulish_extra_bold.ttf`
- [ ] `mulish_extra_bold_italic.ttf`
- [ ] `mulish_extra_light.ttf`
- [ ] `mulish_extra_light_italic.ttf`
- [ ] `mulish_italic.ttf`
- [ ] `mulish_light.ttf`
- [ ] `mulish_light_italic.ttf`
- [ ] `mulish_medium.ttf`
- [ ] `mulish_medium_italic.ttf`
- [ ] `mulish_regular.ttf`
- [ ] `mulish_semi_bold.ttf`
- [ ] `mulish_semi_bold_italic.ttf`

**`maracuya/src/main/res/layout/`**

- [ ] `button_navigation_group_wrp_preview.xml`
- [ ] `button_navigation_wrp_preview.xml`
- [ ] `button_wrp_preview.xml`
- [ ] `catalog_preview.xml`
- [ ] `label_wrp_preview.xml`
- [ ] `text_view_preview.xml`
- [ ] `textfield_wrp_preview.xml`
- [ ] `typography_preview.xml`

**`maracuya/src/main/res/values/`**

- [ ] `attrs.xml`
- [ ] `compound_drawable_actions.xml`
- [ ] `label.xml`
- [ ] `text_view.xml`

**`maracuya/src/main/res/values-en/`**

- [ ] `compound_drawable_actions.xml`
- [ ] `label.xml`
- [ ] `text_view.xml`

### Recursos de pruebas (4 archivos)


**`maracuya/src/debug/res/color/`**

- [ ] `text_view_test_states.xml`

**`maracuya/src/debug/res/layout/`**

- [ ] `text_view_native_style_test.xml`
- [ ] `text_view_registry_test.xml`

**`maracuya/src/debug/res/values/`**

- [ ] `text_view_test.xml`

### Pruebas unitarias (9 archivos)


**`maracuya/src/test/java/cruxui/android/lib/theme/`**

- [ ] `ColorIconCatalogTest.kt`
- [ ] `TokenBridgeTest.kt`
- [ ] `TypographyCatalogTest.kt`

**`maracuya/src/test/java/cruxui/android/lib/ui/components/textviews/`**

- [ ] `TextViewArchitectureTest.kt`

**`maracuya/src/test/java/cruxui/android/lib/wrappers/capabilities/`**

- [ ] `IndependentCapabilitiesTest.kt`

**`maracuya/src/test/java/cruxui/android/lib/wrappers/components/textviews/`**

- [ ] `TextViewTest.kt`

**`maracuya/src/test/java/cruxui/android/lib/wrappers/core/`**

- [ ] `WrapperDependencyRulesTest.kt`

**`maracuya/src/test/java/cruxui/android/lib/wrappers/core/behavior/`**

- [ ] `CompoundDrawableBehaviorControllerTest.kt`

**`maracuya/src/test/java/cruxui/android/lib/wrappers/core/tokens/`**

- [ ] `ComponentTokenBindingTest.kt`

### Pruebas instrumentadas (4 archivos)


**`maracuya/src/androidTest/`**

- [ ] `AndroidManifest.xml`

**`maracuya/src/androidTest/java/cruxui/android/lib/ui/components/textviews/`**

- [ ] `TextViewGalleryParityTest.kt`
- [ ] `TextViewInteractionTest.kt`
- [ ] `TextViewParityTest.kt`

### Fuentes del exportador (31 archivos)


**`token-exporter/src/catalogSchema/kotlin/schema/`**

- [ ] `ColorCatalogSource.kt`
- [ ] `CompatibilitySource.kt`
- [ ] `GenerateCatalogApi.kt`
- [ ] `LibraryCatalogSchema.kt`
- [ ] `LibraryIconsSource.kt`
- [ ] `LibraryTypographySource.kt`
- [ ] `RolesSource.kt`
- [ ] `SchemaDocumentation.kt`
- [ ] `TypographyRolesSource.kt`
- [ ] `TypographySchema.kt`

**`token-exporter/src/main/kotlin/exporter/`**

- [ ] `ExportLibraryTokens.kt`

**`token-exporter/src/main/kotlin/exporter/android/`**

- [ ] `AttributeResourceWriter.kt`
- [ ] `ColorResourceWriter.kt`
- [ ] `FontResourceWriter.kt`
- [ ] `ParagraphAttributes.kt`
- [ ] `ThemeOverlayWriter.kt`
- [ ] `TypographyResourceWriter.kt`

**`token-exporter/src/main/kotlin/exporter/catalog/`**

- [ ] `CatalogWriter.kt`
- [ ] `ExportCatalog.kt`
- [ ] `ThemeCatalogReader.kt`

**`token-exporter/src/main/kotlin/exporter/components/text/`**

- [ ] `TextWidgetStyleWriter.kt`

**`token-exporter/src/main/kotlin/exporter/kotlin/`**

- [ ] `AssetBindingsWriter.kt`
- [ ] `CatalogApiWriter.kt`
- [ ] `XmlReferencesWriter.kt`

**`token-exporter/src/main/kotlin/exporter/output/`**

- [ ] `ExportOutput.kt`
- [ ] `GeneratedDocumentation.kt`
- [ ] `ResourceNames.kt`

**`token-exporter/src/main/kotlin/exporter/validation/`**

- [ ] `CatalogValidator.kt`

**`token-exporter/src/test/kotlin/exporter/`**

- [ ] `CatalogExportTest.kt`

**`token-exporter/src/test/kotlin/schema/`**

- [ ] `LibraryCatalogSchemaTest.kt`
- [ ] `TypographySchemaTest.kt`

### Documentación vigente (7 archivos)


**`arquitectura-xml-compose/`**

- [ ] `01-problema-y-objetivos.md`
- [ ] `02-propuesta-arquitectura.md`
- [ ] `03-textview-implementado.md`
- [ ] `04-escalabilidad-y-responsabilidades.md`
- [ ] `05-tipografia-fuente-unica.md`
- [ ] `06-colores-iconos-fuente-unica.md`
- [ ] `07-paridad-textview-compose-xml.md`

### Referencias de arquitectura (4 archivos)


**`referencias-arquitectura-xml/`**

- [ ] `01-bases-cientificas.md`
- [ ] `02-design-systems-android-xml.md`
- [ ] `03-fundamentos-propuesta.md`
- [ ] `README.md`
