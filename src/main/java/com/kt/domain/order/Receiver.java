package com.kt.domain.order;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class Receiver {
	// receiver_name
	@Column(name = "receiver_name")
	private String name;
	// receiver_address
	@Column(name = "receiver_address")
	private String address;
	// receiver_mobile
	@Column(name = "receiver_mobile")
	private String mobile;

	public Receiver(String receiverName, String receiverAddress, String receiverMobile) {
		this.name = receiverName;
		this.address = receiverAddress;
		this.mobile = receiverMobile;
	}
}