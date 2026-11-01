# RIDE CUENCA — proyecto Android

Prototipo Android nativo (Kotlin + WebView) con interfaz local HTML.

## Incluye
- Inicio y navegación por secciones.
- Catálogo de bicicletas Specialized, Giant y Trek con fotografías enlazadas a fabricantes/distribuidores.
- Enlaces externos para buscar tiendas, talleres y rutas de Cuenca (sin mapa incrustado ni API key).
- GPS nativo: velocidad, distancia aproximada, tiempo y altitud. El permiso de ubicación se solicita únicamente al pulsar Iniciar.
- Anuncios de prueba guardados localmente en el dispositivo.

## Limitaciones importantes
- Los anuncios no son públicos ni se sincronizan entre usuarios. Para un marketplace real se necesita backend, cuentas y moderación.
- Los precios mostrados son referencias y pueden cambiar; no garantizan stock en Cuenca.
- Las fotografías se cargan desde URLs externas y requieren internet.
- El GPS requiere permiso de ubicación y señal adecuada. No se incluye mapa integrado.

## Crear APK
1. Instala Android Studio en un computador.
2. Abre la carpeta `RIDE_CUENCA` como proyecto.
3. Acepta la instalación de Android SDK 35 y la sincronización de Gradle.
4. Ve a **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
5. Android Studio generará el APK en `app/build/outputs/apk/debug/app-debug.apk`.

Este entorno de creación no incluye Android SDK ni Gradle, por lo que se entrega el proyecto fuente y no se afirma que el APK ya esté compilado.

## Compilar el APK desde una tablet (GitHub Actions)
1. Crea un repositorio público en GitHub llamado `ride-cuenca`.
2. Extrae el ZIP y sube **el contenido de la carpeta `RIDE_CUENCA`** al nivel principal del repositorio (deben verse `app`, `build.gradle.kts`, `settings.gradle.kts` y `.github`).
3. Abre la pestaña **Actions** y selecciona **Build RIDE CUENCA APK**. Si aparece un aviso para habilitar Actions, acéptalo.
4. Pulsa **Run workflow**. Espera a que termine con marca verde.
5. Abre la ejecución completada, baja a **Artifacts** y descarga `RIDE-CUENCA-APK`. Dentro está `app-debug.apk`.
6. Para compartirlo, crea un **Release** en GitHub y adjunta `app-debug.apk`. Comparte el enlace de esa Release con la comunidad.

Nota: el APK de prueba/debug sirve para instalar y probar. Antes de distribuir ampliamente, conviene generar una versión firmada de lanzamiento y revisar privacidad, permisos, contenido y funcionamiento en varios teléfonos.
