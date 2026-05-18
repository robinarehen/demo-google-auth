package com.rah.demo.googleauth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TotpServiceTest {

	private TotpService totpService;

	@BeforeEach
	void setUp() {
		this.totpService = new TotpService();
	}

	@Test
	void test_generateSecretKey() {
		String secretKey = this.totpService.generateSecretKey();
		assertNotNull(secretKey);
		assertEquals(secretKey, this.totpService.generateSecretKey());
		System.out.println("secretKey: ".concat(secretKey));
		System.out.println("generateSecretKey: ".concat(this.totpService.generateSecretKey()));
	}
}
