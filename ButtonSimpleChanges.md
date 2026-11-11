# Inventario de la migración Button simple

**s4121779 — Krause Kelsen · 11-07-2026 · 0.0.14**

Rama: `Button_Simple_Refactor`. El árbol estaba limpio al comenzar. Se incluyen todos los archivos de código, recursos, pruebas y documentación agregados, modificados o retirados. Las salidas de Gradle y herramientas temporales no son fuentes de esta migración.

## Carpetas nuevas

- `maracuya/src/androidTest/java/cruxui/android/lib/ui/components/buttons/`
- `maracuya/src/main/java/cruxui/android/lib/core/components/buttons/`
- `maracuya/src/main/java/cruxui/android/lib/core/components/buttons/behavior/`
- `maracuya/src/main/java/cruxui/android/lib/core/components/buttons/tokens/`
- `maracuya/src/main/java/cruxui/android/lib/core/components/buttons/tokensrender/`
- `maracuya/src/main/java/cruxui/android/lib/core/components/buttons/variant/`
- `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/simple/behavior/`
- `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/simple/render/`
- `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/simple/tokens/`
- `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/simple/tokensrender/`
- `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/simple/variant/`
- `maracuya/src/main/java/cruxui/android/lib/wrappers/components/buttons/simple/attributes/`
- `maracuya/src/main/java/cruxui/android/lib/wrappers/components/buttons/simple/behavior/`
- `maracuya/src/main/java/cruxui/android/lib/wrappers/components/buttons/simple/render/`
- `maracuya/src/main/java/cruxui/android/lib/wrappers/components/buttons/simple/tokens/`
- `maracuya/src/main/java/cruxui/android/lib/wrappers/components/buttons/simple/tokensrender/`
- `maracuya/src/main/java/cruxui/android/lib/wrappers/components/buttons/simple/variant/`
- `maracuya/src/test/java/cruxui/android/lib/core/`
- `maracuya/src/test/java/cruxui/android/lib/core/components/`
- `maracuya/src/test/java/cruxui/android/lib/core/components/buttons/`
- `maracuya/src/test/java/cruxui/android/lib/wrappers/components/buttons/`

## Carpetas existentes con cambios directos

- `raíz del repositorio`
- `app/src/main/java/com/example/myfirstapplication/`
- `app/src/main/java/com/example/myfirstapplication/lookandfeel/`
- `maracuya/src/main/java/cruxui/android/lib/atoms/icons/`
- `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/navigation/simple/`
- `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/navigation/simple/tokens/`
- `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/navigation/simple/variant/`
- `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/simple/`
- `maracuya/src/main/java/cruxui/android/lib/ui/previews/`
- `maracuya/src/main/java/cruxui/android/lib/wrappers/components/buttons/simple/`
- `maracuya/src/main/java/cruxui/android/lib/wrappers/previews/`
- `maracuya/src/main/res/drawable/`
- `maracuya/src/main/res/layout/`
- `maracuya/src/main/res/values/`
- `maracuya/src/test/java/cruxui/android/lib/wrappers/core/tokens/`

## Archivos

| Estado | Ruta |
|---|---|
| Agregado | `ButtonSimpleChanges.md` |
| Modificado | `app/src/main/java/com/example/myfirstapplication/MainActivity.kt` |
| Modificado | `app/src/main/java/com/example/myfirstapplication/lookandfeel/BiometricsDisclaimerScreen.kt` |
| Modificado | `app/src/main/java/com/example/myfirstapplication/lookandfeel/LoginMainScreen.kt` |
| Modificado | `app/src/main/java/com/example/myfirstapplication/lookandfeel/LoginScreen.kt` |
| Modificado | `app/src/main/java/com/example/myfirstapplication/lookandfeel/OneTimeDisclamerScreen.kt` |
| Agregado | `maracuya/src/androidTest/java/cruxui/android/lib/ui/components/buttons/ButtonInteractionTest.kt` |
| Modificado | `maracuya/src/main/java/cruxui/android/lib/atoms/icons/LibraryGeneralIconsTokens.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/core/components/buttons/behavior/ButtonBehavior.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/core/components/buttons/behavior/ButtonBehaviorResolver.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/core/components/buttons/tokens/ButtonLayoutTokens.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/core/components/buttons/tokens/ButtonLoadingTokens.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/core/components/buttons/tokens/ButtonTokenDefaults.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/core/components/buttons/tokens/ButtonTokens.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/core/components/buttons/tokensrender/ButtonTokensRender.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/core/components/buttons/tokensrender/ButtonTokensRenderResolver.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/core/components/buttons/variant/ButtonVariant.kt` |
| Modificado | `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/navigation/simple/ButtonNavigationArchitecture.md` |
| Modificado | `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/navigation/simple/tokens/ButtonNavigationTokensResolver.kt` |
| Modificado | `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/navigation/simple/variant/ButtonNavigationVariantResolver.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/simple/Button.kt` |
| Eliminado | `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/simple/ButtonContent.kt` |
| Eliminado | `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/simple/ButtonMrcy.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/simple/ButtonSimpleArchitecture.md` |
| Eliminado | `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/simple/ButtonTokens.kt` |
| Eliminado | `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/simple/ButtonTokensResolver.kt` |
| Eliminado | `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/simple/ButtonVariant.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/simple/behavior/ButtonBehavior.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/simple/behavior/ButtonBehaviorResolver.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/simple/render/ButtonRenderer.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/simple/tokens/ButtonTokens.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/simple/tokens/ButtonTokensResolver.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/simple/tokensrender/ButtonTokensRender.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/simple/tokensrender/ButtonTokensRenderResolver.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/simple/variant/ButtonVariant.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/simple/variant/ButtonVariantResolver.kt` |
| Modificado | `maracuya/src/main/java/cruxui/android/lib/ui/previews/ButtonPreview.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/wrappers/components/buttons/simple/Button.kt` |
| Eliminado | `maracuya/src/main/java/cruxui/android/lib/wrappers/components/buttons/simple/ButtonAttributeParser.kt` |
| Eliminado | `maracuya/src/main/java/cruxui/android/lib/wrappers/components/buttons/simple/ButtonAttributes.kt` |
| Eliminado | `maracuya/src/main/java/cruxui/android/lib/wrappers/components/buttons/simple/ButtonTokensOverrideRegistry.kt` |
| Modificado | `maracuya/src/main/java/cruxui/android/lib/wrappers/components/buttons/simple/ButtonWrp.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/wrappers/components/buttons/simple/attributes/ButtonAttributeReader.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/wrappers/components/buttons/simple/behavior/ButtonBehavior.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/wrappers/components/buttons/simple/behavior/ButtonBehaviorResolver.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/wrappers/components/buttons/simple/render/ButtonRenderer.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/wrappers/components/buttons/simple/tokens/ButtonTokens.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/wrappers/components/buttons/simple/tokens/ButtonTokensOverrideRegistry.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/wrappers/components/buttons/simple/tokens/ButtonTokensResolver.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/wrappers/components/buttons/simple/tokensrender/ButtonTokensRender.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/wrappers/components/buttons/simple/tokensrender/ButtonTokensRenderResolver.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/wrappers/components/buttons/simple/variant/ButtonVariant.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/wrappers/components/buttons/simple/variant/ButtonVariantResolver.kt` |
| Agregado | `maracuya/src/main/java/cruxui/android/lib/wrappers/previews/ButtonGalleryPreview.kt` |
| Agregado | `maracuya/src/main/res/drawable/ic_loading.xml` |
| Agregado | `maracuya/src/main/res/layout/button_primary_preview.xml` |
| Agregado | `maracuya/src/main/res/layout/button_secondary_preview.xml` |
| Agregado | `maracuya/src/main/res/layout/button_terciary_preview.xml` |
| Modificado | `maracuya/src/main/res/layout/button_wrp_preview.xml` |
| Modificado | `maracuya/src/main/res/values/attrs.xml` |
| Agregado | `maracuya/src/main/res/values/button_strings.xml` |
| Agregado | `maracuya/src/test/java/cruxui/android/lib/core/components/buttons/ButtonArchitectureTest.kt` |
| Agregado | `maracuya/src/test/java/cruxui/android/lib/wrappers/components/buttons/ButtonTest.kt` |
| Modificado | `maracuya/src/test/java/cruxui/android/lib/wrappers/core/tokens/ComponentTokenBindingTest.kt` |

## Alcance

- Solo existen PRIMARY (rojo), SECONDARY (borde neutro) y TERCIARY (borde rojo). Button no utiliza HIPERTEXT.
- Se retiran ButtonMrcy y los contratos antiguos del paquete raíz; ButtonWrp queda como alias de clase deprecado hacia el control nativo.
- Core comparte estados, tokens y resolución; los behavior de Compose y Views controlan sus motores de animación y esperan el estado real del host.
- Se retiran de Button XML los atributos y setters de visibilidad de iconos; su presencia depende exclusivamente de la fuente configurada o null. El lector compartido de TextView no propaga sus controles de visibilidad al botón.
- iconStart/iconEnd y sus descripciones se entregan al TextView; tokensrender expone ambos iconos y su tinte compartido con el texto. No hay acciones independientes de iconos.
- Los previews incluyen todas las variantes y estados en claro/oscuro, las cuatro disposiciones de iconos y un ejemplo de inicio/fin de carga.
- Se formatearon Kotlin y XML del componente; los layouts usan un atributo por línea.
- Se migran consumidores de la app, accesos de ButtonNavigation y ComponentTokenBindingTest. El fallback Material de navegación queda desacoplado del nuevo resolver de Button; las recetas DS siguen viniendo de Library.
- No se cambian dependencias Gradle ni la implementación de TextView. El exportador descubre el icono loading; no se editaron archivos de build/generated.
- `loading` se declara como campo obligatorio de General Icons y se inicializa en su catálogo `companion object.general`, siguiendo el mismo patrón que los demás iconos. Library lo incorpora a su colección, rol y referencias XML generadas.
- Guía con contratos, parámetros, precedencias, ejemplos copiables y migración: [ButtonSimpleArchitecture.md](maracuya/src/main/java/cruxui/android/lib/ui/components/buttons/simple/ButtonSimpleArchitecture.md).
- Se actualiza también ButtonNavigationArchitecture.md para explicar el mapeo de variantes y su compatibilidad Material.

## Verificación

- Compilación de librería/AAR, APK de prueba y app: correcta.
- Pruebas unitarias del módulo: 74 aprobadas, sin fallos ni omisiones; incluye la regresión que impide heredar controles de visibilidad de TextView en Button XML.
- Pruebas instrumentadas: 5 aprobadas, sin fallos ni omisiones. Cubren capturas XML/Compose, iconos y acción única, loading, ciclo de vida del animator y fallback de navegación.
- Las pruebas de dispositivo utilizan únicamente el AVD temporal Codex_Button_Validation; no modifican datos de los emuladores del usuario.
- El AAR regenerado contiene button_loading, ic_loading y los atributos DesignButton de iconos.
- Tras integrar loading con el patrón de General Icons: 14 pruebas del exportador aprobadas, AAR ensamblado y consumidores de la app compilados; verificados GeneralLoading y libraryIconGeneralLoading generados.
- La guía distingue la validación de render en dispositivo del recurso R obsoleto observado en Android Studio y documenta cómo recargar el preview.

## Capturas generadas para revisión

Las ocho imágenes están en `captures/button-validation/`, carpeta de salidas ignorada por Git. Son capturas reales del emulador; no son recursos del componente ni sustituyen a los previews del editor.

- `button-primary-compose.png`
- `button-primary-xml.png`
- `button-secondary-compose.png`
- `button-secondary-xml.png`
- `button-terciary-compose.png`
- `button-terciary-xml.png`
- `button-icons-compose.png`
- `button-icons-xml.png`
