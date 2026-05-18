package com.rah.demo.googleauth.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rah.demo.googleauth.dto.SetupResponse;
import com.rah.demo.googleauth.dto.VerificationRequest;
import com.rah.demo.googleauth.dto.VerificationResponse;
import com.rah.demo.googleauth.service.TotpService;

@RestController
@RequestMapping("/api/mfa")
public class MfaController {

	private final TotpService totpService;

	public MfaController(TotpService totpService) {
		super();
		this.totpService = totpService;
	}

	/**
	 * Retornar los siguientes valores
	 * 
	 * La URL: con la que se puede generar el QR con alguna pagina online
	 * 
	 * El SecretKey: el cual se puede pasar a la aplicación que genera el código
	 * 
	 * @param email
	 * @return
	 */
	@GetMapping("/setup")
	public ResponseEntity<SetupResponse> setupMfa(@RequestParam String email) {
		return ResponseEntity.ok(this.totpService.getQrUrl(email));
	}

	@PostMapping("/verify")
	public ResponseEntity<VerificationResponse> verifyMfa(@RequestBody VerificationRequest request) {
		// Simulación: Aquí debes buscar en BD la 'secretKey' que guardaste para este
		// request.email()
		// Ejemplo estático, reemplázalo por tu valor en la BD.
		// String userSecretKeyInDb = "L3HK6OCON63PUDIT";
		// String userSecretKeyInDb = this.totpService.generateSecretKey();
		// var response = this.totpService.verifyCode(userSecretKeyInDb,
		// request.code());
		VerificationResponse response = this.totpService.verifyCode(request);
		return ResponseEntity.status(response.status()).body(response);
	}

	@GetMapping(value = "/qr", produces = MediaType.IMAGE_PNG_VALUE)
	public ResponseEntity<byte[]> getMfaQrCode(@RequestParam String email) {
		// 1. Simulación: En un entorno real, buscarías si el usuario ya tiene un
		// secretKey en BD.
		// Si no existe, lo generas y lo guardas.
		// Para esta prueba generamos uno estático o dinámico:
		// String secretKey = this.totpService.generateSecretKey();
		// 2. Generar la estructura de la URL requerida por Google
		// String qrUrl = this.totpService.getQrUrl(secretKey, email, "Rah-developers");
		// 3. Convertir esa URL en una imagen PNG de 250x250 píxeles
		// byte[] qrImage = this.totpService.generateQrCodeImage(qrUrl, 250, 250);

		byte[] qrImage = this.totpService.generateQrCodeImage(email, 250, 250);

		// 4. Retornar los bytes con el tipo de contenido adecuado
		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrImage);
	}
}
