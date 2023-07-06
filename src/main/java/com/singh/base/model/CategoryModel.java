package com.singh.base.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryModel {
	private Long categoryId;
	private String categoryName;
	private String discription;
	private Integer discount;
	private Integer gst;
	private Integer deliveryCharge;
}
