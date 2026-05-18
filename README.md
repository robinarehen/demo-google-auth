# Demo Google Auth

Una aplicación Java que demuestra la integración y autenticación con Google.

## Descripción

Este proyecto implementa un flujo de autenticación con Google, permitiendo a los usuarios iniciar sesión de forma segura mediante sus credenciales de Google. Es un ejemplo completo que muestra las mejores prácticas para integrar OAuth 2.0 con Google en aplicaciones Java.

## Características

- ✅ Autenticación con Google OAuth 2.0
- ✅ Gestión segura de tokens
- ✅ Información del perfil de usuario
- ✅ Implementación de ejemplo en Java

## Requisitos

- Java 8 o superior
- Maven o Gradle
- Credenciales de Google Cloud (Client ID y Secret)

## Instalación

1. Clona el repositorio:
```bash
git clone https://github.com/robinarehen/demo-google-auth.git
cd demo-google-auth
```

2. Configura tus credenciales de Google en el archivo de propiedades:
```properties
google.client.id=tu-client-id
google.client.secret=tu-client-secret
google.redirect.uri=tu-redirect-uri
```

3. Compila el proyecto:
```bash
mvn clean install
```

## Uso

Ejecuta la aplicación y sigue el flujo de autenticación con Google para iniciar sesión.

## Licencia

Este proyecto está bajo licencia MIT.
