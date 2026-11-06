# Maracuya
Maracuyá es un Design System modular y escalable, creado para construir interfaces claras, consistentes y mantenibles en aplicaciones modernas.  Está diseñado bajo principios de separación de responsabilidades, composición y tokenización, permitiendo que la UI sea predecible, extensible y fácil de evolucionar sin fricción.

El tema y los tokens de TextView se comparten entre Jetpack Compose y XML. La [guía de TokenBridgeXML](arquitectura-xml-compose/03-textview-implementado.md) explica la arquitectura, el registro de overrides, los recursos generados y la migración del wrapper anterior.


Documentación vigente:

- [Arquitectura y distribución de responsabilidades](arquitectura-xml-compose/02-propuesta-arquitectura.md).
- [Extensión a nuevos componentes](arquitectura-xml-compose/04-escalabilidad-y-responsabilidades.md).
- [Tipografías](arquitectura-xml-compose/05-tipografia-fuente-unica.md) y [colores e iconos](arquitectura-xml-compose/06-colores-iconos-fuente-unica.md) desde una sola fuente.
- [Paridad visual, comportamiento y previews de TextView](arquitectura-xml-compose/07-paridad-textview-compose-xml.md).
- [Generación automática de recursos](token-exporter/README.md).


## Paquete para integración

El namespace y paquete base de la librería es `cruxui.android.lib`.
Las fuentes están en `maracuya/src/main/java/cruxui/android/lib/` y las pruebas usan el mismo paquete base.
El módulo Gradle local sigue llamándose `:maracuya`; el nombre del módulo y el paquete Kotlin son independientes.

Al trasladar el código a otro proyecto:

- Configurar `namespace = "cruxui.android.lib"` en el módulo Android de destino.
- Copiar también los recursos de `src/main/res`, no solo los archivos Kotlin.
- Integrar `token-exporter`, su inclusión en `settings.gradle.kts` y la tarea `generateLibraryTokens` del módulo Android. Las colecciones y referencias generadas se crean durante el build; no se mantienen a mano.
- Si el módulo de destino se llama `lib`, adaptar las rutas `../maracuya/src/main/java` de `token-exporter/build.gradle.kts` al directorio real y las dependencias `project(":maracuya")` al nombre real del módulo.
- Mantener alineadas las versiones y dependencias de Gradle del proyecto. Copiar solo `atoms` no constituye una librería compilable: necesita sus tipos base y el resto de dependencias.

Verificación local del paquete:

```sh
./gradlew clean :token-exporter:test :maracuya:testDebugUnitTest :maracuya:lintDebug :maracuya:assembleRelease :app:assembleDebug :maracuya:assembleDebugAndroidTest
```
