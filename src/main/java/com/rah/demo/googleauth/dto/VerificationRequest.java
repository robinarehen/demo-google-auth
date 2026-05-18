package com.rah.demo.googleauth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record VerificationRequest(
		@NotBlank @Email String email, 
		@NotBlank @Positive String code) {

}
