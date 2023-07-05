package com.singh.base.utility;

import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.singh.base.constants.GlobalConstants_ValidateProduct;
import com.singh.base.entity.Product;

@Component
public class ValidateFileProducts {
	
	@Autowired
	private ModelMapper mapper;
	
	public Map<String, String> validateFileProducts(Product product){
		
		Map<String, String> validProduct = new HashedMap<String, String>();
		
		if(product.getProductName() == null) {
			validProduct.put("Product Name : ", GlobalConstants_ValidateProduct.productNameIsEmpty+GlobalConstants_ValidateProduct.productNameLenght);
		}
		
//		SupplierModel supplierModel = supplierService.getSupplierById(product.getSupplierId().getSupplierId());
//		supplier = this.mapper.map(supplierModel, Supplier.class);
//		if(supplier == null) {
//			validProduct.put("Supplier ID : ", "No Supplier ID Found");
//		}
		
		
//		CategoryModel categoryModel = categoryService.getCategoryById(product.getCategoryId().getCategoryId());
//		category = this.mapper.map(categoryModel, Category.class);
//		if(category == null) {
//			validProduct.put("Category ID : ", "No Category ID Found");
//		}
		
		if(product.getProductQuantity()<=0) {
			validProduct.put("Product Quantity : ", "Invalid Product Quantity, Shoud Be Greater Than 0");
		}
		
		if(product.getProductPrice()<=0) {
			validProduct.put("Product Price : ", "Invalid Product Price, Shoud Be Greater Than 0");
		}
		
		return validProduct;
	}

}
