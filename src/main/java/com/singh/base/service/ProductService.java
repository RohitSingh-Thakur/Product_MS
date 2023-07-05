package com.singh.base.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.singh.base.entity.Product;
import com.singh.base.model.ProductModel;

public interface ProductService {	
	public Boolean addProduct(Product product);
	public ProductModel getProductById(Long productId);
	
	
	public ProductModel getProductByName(String productName);
	public List<ProductModel> getAllProducts();
	public Boolean deleteProduct(Long id);
	
	public Boolean updateProductByProductId(Long productId, Map<String, Object> productFields);
	
	public List<ProductModel> sortProducts(String fieldName, String order);
	public List<ProductModel> getMaxPriceProducts();
	public Double countSumOfProductPrice();
	public Long getTotalCountOfProducts();
	
	public Map<String, Object> uploadFile(MultipartFile file);
	

}
