package com.singh.base.dao;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.singh.base.entity.Product;

public interface ProductDao {
	public Boolean addProduct(Product product);
	public Product getProductById(Long productId);
	public List<Product> getAllProducts();
	public Boolean deleteProduct(Long productId);
	public Boolean updateProductByProductId(Long productId, Map<String, Object> productFields);
	public List<Product> sortProducts(String fieldName,String order);
	public List<Product> getMaxPriceProducts();
	public Double countSumOfProductPrice();
	public Long getTotalCountOfProducts();
	public Product getProductByName(String productName);
	
	public Integer uploadFile(List<Product> productsListFromFile);
}
