# Demo Validador MFA Auth con Códigos de un Solo Uso

Demo de una aplicación Java con la cual podemos validar los códigos de un solo uso, que nos generan las App generadoras de códigos de 6 digitos **TOTP**, como lo es Google Authenticator.

## Descripción

Este proyecto implementa un flujo de validación de códigos generados por la App Google Authenticator.

## Características

- ✅ Validación de códigos de Google Authenticator.
- ✅ Genera el QR el cual es leido desde la aplicación de Google para generar los códigos.
- ✅ Recibe el código generado por la App el cual es validado.

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

1. Generar el QR  llamando a la url `localhost:8080/api/mfa/qr?email=tu-email`
2. Desde la App Authenticator leer el QR
3. Validar el código generado por la App llamado a la url `localhost:8080/api/mfa/verify` y pasando el json en el body
```json
{
    "email":"sneyt04@gmail.com",
    "code":"684174"
}
```

## Licencia

Este proyecto está bajo licencia `MIT`.
