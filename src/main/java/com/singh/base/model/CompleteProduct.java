package com.singh.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompleteProduct {
	private ProductModel productModel;
	private SupplierModel supplierModel;
	private CategoryModel categoryModel;
}
