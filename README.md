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

## Deuda Técnica
Al ser un demo, todo esta fijo en el código, para un uso en producción se debe implementar la logica que guarde en base de datos, el `secret key` asociado al `email` del usuario, para poder conservar la `secret key` la cual tiene asociada a la App cada usuario. 
Asegurar que el código se valida una sola vez y no se pueda usar varias veces, ya que el algoritmo te permite usarlo en una ventana de 30 segudos.

## Licencia

Este proyecto está bajo licencia `MIT`.
