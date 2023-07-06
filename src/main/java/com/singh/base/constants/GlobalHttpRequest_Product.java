package com.singh.base.constants;

public class GlobalHttpRequest_Product {
	public static final String ADD_PRODUCT = "/addProduct";
	public static final String GET_PRODUCT_BY_ID = "/getProductById/{productId}";
	public static final String GET_COMPLETEPRODUCT_BY_ID = "/getCompleteProductById/{productId}";
	public static final String GET_ALL_PRODUCTS = "/get-all-products";
	public static final String DELETE_PRODUCT = "/delete-product/{productId}";
	public static final String UPDATE_PRODUCT_BY_PRODUCT_ID = "/update-product-by-productId/{productId}";
	public static final String SORT_PRODUCT = "/sort-products";
	public static final String GET_MAX_PRICE_PRODUCT = "/get-maxprice-products";
	public static final String GET_COUNT_SUMOF_PRODUCT_PRICE = "/count-sumof-product-price";
	public static final String GET_TOTAL_PRODUCTS_COUNT = "/get-total-products-count";
	public static final String GET_PRODUCT_BY_NAME = "/get-product-by-name";
	public static final String UPLOAD_EXCELFILE = "/uploadExcellFile";

	// Supplier
	public static final String GET_SUPPLIER_BY_ID = "http://localhost:8002/get-supplier-by-id/";

	// Category 
	public static final String GET_CATEGORY_BY_ID = "http://localhost:8003/get-category-by-id/";
}
