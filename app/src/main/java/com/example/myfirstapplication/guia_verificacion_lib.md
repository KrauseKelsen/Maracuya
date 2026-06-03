# Checklist para verificar y refrescar el consumo de `cruxui:maracuya-release:0.0.3`

Este documento contiene comandos para ejecutar desde la **Terminal de Android Studio** dentro del proyecto consumidor `ScotiaToken`.

La dependencia esperada es:

```gradle
implementation 'cruxui:maracuya-release:0.0.3'
```

> Nota: si tu módulo principal no se llama `app`, cambia `:app` por el nombre real del módulo.

---

## 1. Verificar que Gradle realmente resolvió/descargó el paquete

Ejecuta:

```bash
./gradlew :app:dependencies --configuration debugRuntimeClasspath | grep -i "maracuya"
```

Si tu módulo no se llama `app`, cambia `:app` por el nombre real, por ejemplo:

```bash
./gradlew :ScotiaToken:dependencies --configuration debugRuntimeClasspath | grep -i "maracuya"
```

También puedes pedirle a Gradle el detalle exacto de esa dependencia:

```bash
./gradlew :app:dependencyInsight \
  --dependency maracuya-release \
  --configuration debugRuntimeClasspath
```

Gradle está resolviendo si aparece algo como:

```text
cruxui:maracuya-release:0.0.3
```

---

## 2. Ver dónde está descargado el paquete en el caché de Gradle

Gradle normalmente descarga dependencias en:

```bash
~/.gradle/caches/modules-2/files-2.1/
```

Busca el artifact así:

```bash
find ~/.gradle/caches/modules-2/files-2.1/cruxui/maracuya-release/0.0.3 -type f
```

Debes recibir algo como:

```text
maracuya-release-0.0.3.aar
maracuya-release-0.0.3.pom
```

Si no sabes si está en esa ruta exacta, usa:

```bash
find ~/.gradle/caches/modules-2/files-2.1 -type f -name "*maracuya*0.0.3*"
```

---

## 3. Guardar la ruta del `.aar` en una variable

Ejecuta:

```bash
AAR_PATH=$(find ~/.gradle/caches/modules-2/files-2.1/cruxui/maracuya-release/0.0.3 -type f -name "*.aar" | head -n 1)

echo "$AAR_PATH"
```

Si imprime una ruta, perfecto.

Ejemplo esperado:

```text
/Users/tu_usuario/.gradle/caches/modules-2/files-2.1/cruxui/maracuya-release/0.0.3/xxxxx/maracuya-release-0.0.3.aar
```

---

## 4. Ver qué archivos trae el paquete `.aar`

Un `.aar` es básicamente un `.zip`. Puedes listar su contenido así:

```bash
unzip -l "$AAR_PATH" | less
```

Para ver rápidamente carpetas importantes:

```bash
unzip -l "$AAR_PATH" | grep -E "classes.jar|res/|AndroidManifest.xml|R.txt|public.txt"
```

Deberías revisar si existen cosas como:

```text
classes.jar
res/layout/...
res/values/...
AndroidManifest.xml
R.txt
```

---

## 5. Extraer el `.aar` para inspeccionarlo mejor

Crea una carpeta temporal:

```bash
rm -rf /tmp/maracuya-aar
mkdir -p /tmp/maracuya-aar
unzip "$AAR_PATH" -d /tmp/maracuya-aar
```

Ahora lista su contenido:

```bash
find /tmp/maracuya-aar -maxdepth 3 -type f | sort
```

---

## 6. Ver si hay layouts XML dentro del paquete

Ejecuta:

```bash
find /tmp/maracuya-aar/res -type f -name "*.xml" | sort
```

Si esperas invocar componentes desde XML, revisa especialmente:

```bash
find /tmp/maracuya-aar/res/layout -type f -name "*.xml" 2>/dev/null | sort
```

Puedes abrir uno:

```bash
cat /tmp/maracuya-aar/res/layout/NOMBRE_DEL_LAYOUT.xml
```

---

## 7. Ver si las clases de `components` existen dentro del `.aar`

El código compilado normalmente está dentro de:

```text
classes.jar
```

Lista las clases así:

```bash
jar tf /tmp/maracuya-aar/classes.jar | grep -i "components"
```

Para buscar específicamente por tu nuevo paquete:

```bash
jar tf /tmp/maracuya-aar/classes.jar | grep "cruxui/android/maracuya"
```

Para buscar wrappers XML, que son los que normalmente podrían usarse desde layouts:

```bash
jar tf /tmp/maracuya-aar/classes.jar | grep -i "wrappers"
```

O más específico:

```bash
jar tf /tmp/maracuya-aar/classes.jar | grep "cruxui/android/maracuya/wrappers"
```

---

## 8. Convertir rutas de clases a nombres de package para XML

Este comando te muestra los nombres en formato más parecido al que usarías en XML:

```bash
jar tf /tmp/maracuya-aar/classes.jar \
  | grep "cruxui/android/maracuya" \
  | grep "\.class$" \
  | sed 's#/#.#g' \
  | sed 's#\.class$##' \
  | sort
```

Ejemplo de salida esperada:

```text
cruxui.android.maracuya.wrappers.theme.MyLibraryThemeWrp
cruxui.android.maracuya.wrappers.components.buttons.primary.PrimaryButtonWrp
```

Si quieres usar un componente en XML, debería existir una clase pública como esa.

---

## 9. Ver solo clases relacionadas con buttons/components

Ejecuta:

```bash
jar tf /tmp/maracuya-aar/classes.jar \
  | grep "cruxui/android/maracuya" \
  | grep -E "components|wrappers|buttons" \
  | sed 's#/#.#g' \
  | sed 's#\.class$##' \
  | sort
```

Esto ayuda a confirmar si dentro del `.aar` están las clases de `components`, `wrappers` y `buttons` que esperas consumir.

---

## 10. Ver si una clase específica existe

Por ejemplo, si intentas usar esto en XML:

```xml
<cruxui.android.maracuya.wrappers.components.buttons.primary.PrimaryButtonWrp />
```

verifica si existe:

```bash
jar tf /tmp/maracuya-aar/classes.jar \
  | grep "cruxui/android/maracuya/wrappers/components/buttons/primary/PrimaryButtonWrp.class"
```

Si no imprime nada, esa clase no está dentro del `.aar` publicado.

---

## 11. Ver si una clase es pública y extiende una View

Esto es importante para XML.

Supongamos que tienes esta clase:

```text
cruxui.android.maracuya.wrappers.components.buttons.primary.PrimaryButtonWrp
```

Puedes inspeccionarla así:

```bash
javap -classpath /tmp/maracuya-aar/classes.jar \
  cruxui.android.maracuya.wrappers.components.buttons.primary.PrimaryButtonWrp
```

Para poder usarse en XML como View tradicional, normalmente debería ser una clase pública y heredar de algo compatible con Android View, por ejemplo:

```text
android.view.View
android.widget.FrameLayout
androidx.constraintlayout.widget.ConstraintLayout
```

Si es solo un `@Composable`, no va a aparecer como componente invocable directamente desde XML. Para XML necesitas un wrapper tipo `ComposeView`, `FrameLayout`, `LinearLayout`, etc.

---

## 12. Ver el `AndroidManifest.xml` del `.aar`

Ejecuta:

```bash
cat /tmp/maracuya-aar/AndroidManifest.xml
```

También puedes buscar el namespace/package:

```bash
cat /tmp/maracuya-aar/AndroidManifest.xml | grep -i "package\|manifest"
```

---

## 13. Ver `R.txt` para confirmar recursos publicados

Ejecuta:

```bash
cat /tmp/maracuya-aar/R.txt | less
```

Buscar recursos específicos:

```bash
grep -i "primary" /tmp/maracuya-aar/R.txt
```

```bash
grep -i "button" /tmp/maracuya-aar/R.txt
```

---

## 14. Ver el `.pom` publicado

El `.pom` te dice qué dependencias trae declaradas el paquete.

```bash
POM_PATH=$(find ~/.gradle/caches/modules-2/files-2.1/cruxui/maracuya-release/0.0.3 -type f -name "*.pom" | head -n 1)

cat "$POM_PATH"
```

Buscar dependencias:

```bash
cat "$POM_PATH" | grep -E "groupId|artifactId|version"
```

Esto ayuda a detectar si el paquete se publicó sin declarar dependencias necesarias como Compose, AppCompat, Material, etc.

---

## 15. Forzar a Gradle a refrescar la dependencia

Si crees que Android Studio está usando una versión cacheada o corrupta:

```bash
./gradlew --stop
```

Luego:

```bash
./gradlew clean --refresh-dependencies
```

Después:

```bash
./gradlew :app:assembleDebug --refresh-dependencies
```

O todo junto:

```bash
./gradlew --stop
./gradlew clean
./gradlew :app:assembleDebug --refresh-dependencies
```

---

## 16. Limpiar cachés locales del proyecto consumidor

Desde la raíz de `ScotiaToken`:

```bash
rm -rf .gradle
rm -rf build
rm -rf app/build
./gradlew clean
./gradlew :app:assembleDebug --refresh-dependencies
```

Si tienes más módulos, puedes limpiar todos los `build` así:

```bash
find . -maxdepth 3 -type d -name build -exec rm -rf {} +
```

Luego:

```bash
./gradlew :app:assembleDebug --refresh-dependencies
```

---

## 17. Borrar solo el paquete `maracuya-release` del caché de Gradle

Si quieres obligar a Gradle a descargar otra vez específicamente ese paquete:

```bash
rm -rf ~/.gradle/caches/modules-2/files-2.1/cruxui/maracuya-release/0.0.3
```

Luego:

```bash
./gradlew :app:assembleDebug --refresh-dependencies
```

---

## 18. Comando completo recomendado para refrescar todo

Yo usaría este bloque:

```bash
./gradlew --stop

rm -rf .gradle
rm -rf build
rm -rf app/build
rm -rf ~/.gradle/caches/modules-2/files-2.1/cruxui/maracuya-release/0.0.3

./gradlew clean
./gradlew :app:assembleDebug --refresh-dependencies
```

Después abre Android Studio y haz:

```text
File > Sync Project with Gradle Files
```

Si sigue raro:

```text
File > Invalidate Caches...
```

y reinicia Android Studio.

---

## 19. Verificar después del build que el paquete sigue en el classpath

Después de compilar:

```bash
./gradlew :app:dependencies --configuration debugRuntimeClasspath | grep -i "maracuya"
```

Y otra vez:

```bash
./gradlew :app:dependencyInsight \
  --dependency maracuya-release \
  --configuration debugRuntimeClasspath
```

---

## 20. Punto importante sobre componentes XML

Que el paquete compile **no garantiza** que puedas invocar componentes desde XML.

Para que un componente sea usable así:

```xml
<cruxui.android.maracuya.wrappers.components.buttons.primary.PrimaryButtonWrp
    android:layout_width="wrap_content"
    android:layout_height="wrap_content" />
```

la clase debe cumplir cosas como:

1. Estar dentro del `.aar`.
2. Ser `public`.
3. Ser una clase Android View o ViewGroup.
4. Tener constructor compatible con XML, por ejemplo en Kotlin:

```kotlin
constructor(context: Context) : super(context)

constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
```

Si tus componentes son solo `@Composable`, no se pueden usar directamente como tags XML. En ese caso necesitas wrappers tipo `ComposeView`, `FrameLayout`, etc.

---

## Checklist rápido

Ejecuta en este orden:

```bash
./gradlew :app:dependencyInsight --dependency maracuya-release --configuration debugRuntimeClasspath
```

```bash
AAR_PATH=$(find ~/.gradle/caches/modules-2/files-2.1/cruxui/maracuya-release/0.0.3 -type f -name "*.aar" | head -n 1)
echo "$AAR_PATH"
```

```bash
rm -rf /tmp/maracuya-aar
mkdir -p /tmp/maracuya-aar
unzip "$AAR_PATH" -d /tmp/maracuya-aar
```

```bash
jar tf /tmp/maracuya-aar/classes.jar | grep "cruxui/android/maracuya" | sort
```

```bash
jar tf /tmp/maracuya-aar/classes.jar | grep -i "wrappers"
```

```bash
cat /tmp/maracuya-aar/R.txt | less
```

```bash
./gradlew --stop
rm -rf .gradle build app/build
rm -rf ~/.gradle/caches/modules-2/files-2.1/cruxui/maracuya-release/0.0.3
./gradlew :app:assembleDebug --refresh-dependencies
```
