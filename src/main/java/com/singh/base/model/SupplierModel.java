package com.singh.base.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplierModel {
	private Long supplierId;
	private String supplierName;
	private String city;
	private String postalCode;
	private String country;
	private String mobileNumber;
}
