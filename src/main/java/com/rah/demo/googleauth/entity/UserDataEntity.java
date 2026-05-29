package com.rah.demo.googleauth.entity;

import java.time.LocalDateTime;

import com.rah.demo.googleauth.dto.MfaStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER_DATA")
@Data
@NoArgsConstructor
public class UserDataEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private String userSecretKey;
	@Column(unique = true, nullable = false)
	private String userEmail;
	@Column(nullable = false)
	private String qrCodeUrl;
	private String lastCode;
	private LocalDateTime createDate;
	private String mfaStatus;

	public UserDataEntity(String userSecretKey, String userEmail, String qrCodeUrl) {
		super();
		this.userSecretKey = userSecretKey;
		this.userEmail = userEmail;
		this.qrCodeUrl = qrCodeUrl;
	}

	@PrePersist
	void prePersist() {
		this.createDate = LocalDateTime.now();
		this.mfaStatus = MfaStatus.PENDING.name();
	}

}
