# Demo Validador MFA Auth con Códigos de un Solo Uso y Single-Sing-On SSO

Demo de una aplicación Java con la cual podemos validar los códigos de un solo uso, que nos generan las App generadoras de códigos de 6 digitos **TOTP**, como lo es `Google Authenticator`.

## Descripción
Este proyecto implementa lo siguiente.

1. Un flujo de validación de códigos generados por la App Google Authenticator.

2. La autenticación con la cuenta de Gamil de Google o Github, agregando el `ID` y la `SECRET`, los cuales se deben generar en la plataforma correspondiente.

## Características

- ✅ Validación de códigos de Google Authenticator.
- ✅ Genera el QR el cual es leido desde la aplicación de Google para generar los códigos.
- ✅ Recibe el código generado por la App el cual es validado.
- ✅ Se implementa la autenticación con Google o Github.

## Stack Tecnológico

- Java 21
- Spring Boot 3.5.14
- Maven

## Instalación

1. Clona el repositorio:
```bash
git clone https://github.com/robinarehen/demo-google-auth.git
cd demo-google-auth
```

2. Compila el proyecto:
```bash
mvn clean install
```

3. Ejecutar el proyecto:
```bash
mvn spring-boot:run
```

## Uso

### Validar Códigos TOTP

Con el proyecto en ejecución

> No olvide reemplar los valores de ejemplo
```
tu-email = sneyt04@gmail.com
código-App = 638061
```

1. Generar el QR  llamando a la url `localhost:8080/api/mfa/qr?email=tu-email`
2. Desde la App Authenticator leer el QR
3. Validar el código generado por la App llamado a la url `localhost:8080/api/mfa/verify` y pasando el json en el body
```json
{
    "email":"tu-email",
    "code":"código-App"
}
```

### Login con Google

> Pasos previos
1. Crear un proyecto nuevo en la consola de  [Google Cloud Console](https://console.cloud.google.com/welcome)

2. Configurar la url, generar el `ID` y `SECRET` del cliente, consultar el paso a paso con la `IA GEMINIS`

3. Ingresar el `ID` y `SECRET` del cliente en el `yaml` del proyecto
```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: ${TU_CLIENT_ID_DE_GOOGLE}
            client-secret: ${TU_CLIENT_SECRET_DE_GOOGLE}
            scope:
              - email
              - profile
```

> Con el proyecto en Ejecución

1. al llamar a la url `localhost:8080/auth/user` nos redirige al login donde podemos tomar `Google` o `GitHub`

2. Al ingresar tus datos son validados y luego redirigido de nuevo a la aplicación.

## Deuda Técnica


## Licencia

Este proyecto está bajo licencia `MIT`.
