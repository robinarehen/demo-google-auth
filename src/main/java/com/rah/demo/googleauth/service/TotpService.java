package com.rah.demo.googleauth.service;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.jboss.aerogear.security.otp.Totp;
import org.jboss.aerogear.security.otp.api.Base32;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.rah.demo.googleauth.dto.SetupResponse;
import com.rah.demo.googleauth.dto.VerificationRequest;
import com.rah.demo.googleauth.dto.VerificationResponse;
import com.rah.demo.googleauth.entity.UserDataEntity;
import com.rah.demo.googleauth.repository.UserDataRepository;

@Service
public class TotpService {

	private static final String SECRET_KEY = Base32.random();

	private UserDataRepository userDataRepository;

	public TotpService(UserDataRepository userDataRepository) {
		super();
		this.userDataRepository = userDataRepository;
	}

	public String generateSecretKey() {
		return Base32.random();
	}

	public SetupResponse getQrUrl(String email) {

		Optional<UserDataEntity> userDataDb = this.userDataRepository.findByUserEmail(email);

		return userDataDb.map(data -> new SetupResponse(data.getUserSecretKey(), data.getQrCodeUrl())).orElseGet(() -> {

			String secretKey = this.generateSecretKey();
			String qrUrl = this.generateQrUrl(secretKey, email);

			var userData = new UserDataEntity(secretKey, email, qrUrl);

			this.userDataRepository.save(userData);

			return new SetupResponse(secretKey, qrUrl);
		});

	}

	private String generateQrUrl(String secretKey, String email) {
		try {
			String issuer = "Rah-developers";
			String urlIssuerEmail = URLEncoder.encode(issuer + ":" + email, StandardCharsets.UTF_8.name());
			String urlIssuer = URLEncoder.encode(issuer, StandardCharsets.UTF_8.name());
			return String.format("otpauth://totp/%s?secret=%s&issuer=%s", urlIssuerEmail, secretKey, urlIssuer);
		} catch (UnsupportedEncodingException exception) {
			throw new RuntimeException("Error codificando la URL del código QR", exception);
		}
	}

	public VerificationResponse verifyCode(VerificationRequest request) {
		try {
			// Simulación: Aquí debes buscar en BD la 'secretKey' que guardaste para este
			// request.email()
			Totp totp = new Totp(SECRET_KEY);

			if (totp.verify(request.code())) {
				// Aquí puedes marcar al usuario como "MFA verificado" en tu sesión o generar un
				// JWT
				return new VerificationResponse(true, 200, "Código verificado correctamente");
			} else {
				return new VerificationResponse(false, 401, "Código inválido o expirado");
			}
		} catch (Exception exception) {
			return new VerificationResponse(false, 401, "Código inválido o expirado");
		}
	}

	public byte[] generateQrCodeImage(String email, int width, int height) {
		try {

			SetupResponse setupResponse = this.getQrUrl(email);

			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			// Transforma el texto otpauth:// en una matriz de bits
			BitMatrix bitMatrix = qrCodeWriter.encode(setupResponse.qrCodeUrl(), BarcodeFormat.QR_CODE, width, height);

			// Escribe los bits en un flujo de memoria como formato PNG
			ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();

			MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);

			return pngOutputStream.toByteArray();

		} catch (Exception exception) {
			throw new RuntimeException("Error interno al generar la imagen del código QR", exception);
		}
	}
}
