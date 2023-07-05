package com.singh.base.serviceImpl;

import java.io.File; 
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.singh.base.dao.ProductDao;
import com.singh.base.entity.Product;
import com.singh.base.model.ProductModel;
import com.singh.base.service.ProductService;
import com.singh.base.utility.ValidateFileProducts;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDao dao;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private ValidateFileProducts validateFileProducts;
	
	private static final Logger log = Logger.getLogger(ProductServiceImpl.class);
	
	private String completeFileName;
	
	private int alreadyExist = 0;
	
	private List<Integer> alreadyExistProductsRowNumbers = new ArrayList<Integer>();
	
	private Map<Integer, Map<String, String>> notValidProduct = new LinkedHashMap<>();	
	private Map<String, Object> responseMap = new LinkedHashMap<>();

	@Override
	public Boolean addProduct(Product product) {
		return dao.addProduct(product);
	}

	@Override
	public ProductModel getProductById(Long productId) { // Model Mapper Checked
		 Product product = dao.getProductById(productId);
		 if(product != null) {
			 ProductModel model = this.mapper.map(product, ProductModel.class);
			 System.out.println(model.getClass().getName());
			 return this.mapper.map(product, ProductModel.class);
			 
		 }else {
			 return null;
		 }
	}
	

	@Override
	public List<ProductModel> getAllProducts() {
		List<Product> products = dao.getAllProducts();
		if(!products.isEmpty()) {
			 return products.stream().map(e -> this.mapper.map(e, ProductModel.class)).toList();
		}else {
			return null;
		}
	}
	
	@Override
	public Boolean deleteProduct(Long productId) {
		Boolean status = dao.deleteProduct(productId);
		if (status) {
			return status;
		}else {
			return status;
		}
	}
	
	
	@Override
	public Boolean updateProductByProductId(Long  productId, Map<String, Object> productFields) {
		
		Boolean status = dao.updateProductByProductId(productId, productFields);
		return status;
	}
	
	
	@Override
	public List<ProductModel> sortProducts(String fieldName, String order) {
		List<Product> products = dao.sortProducts(fieldName, order);
		if(!products.isEmpty()) {
			return products.stream().map(e -> this.mapper.map(e, ProductModel.class)).toList();
		}else {
			return null;
		}
	}
	
	
	@Override
	public List<ProductModel> getMaxPriceProducts() {
			List<Product> maxPriceProducts = dao.getMaxPriceProducts();
			if(!maxPriceProducts.isEmpty()) {
				return maxPriceProducts.stream().map(e -> this.mapper.map(e, ProductModel.class)).toList();
			}else {
				return null;
			}
	}
	
	@Override
	public Double countSumOfProductPrice() {
		double sumOfProductPrice = dao.countSumOfProductPrice();
		String formattedNumber = String.format("%.3f", sumOfProductPrice);
		return Double.parseDouble(formattedNumber);
	}
	
	@Override
	public Long getTotalCountOfProducts() {
		return dao.getTotalCountOfProducts();
	}
	

	@Override
	public ProductModel getProductByName(String productName) {
		Product product = dao.getProductByName(productName);
		if(product != null) {
			return this.mapper.map(product, ProductModel.class);
		}else {
			return null;
		}
	}

	@Override
	public Map<String, Object> uploadFile(MultipartFile file) {
		
		String path = "src/main/resources";
		String filename = file.getOriginalFilename();
		completeFileName = path+File.separator+filename;
		
		Integer addedCount = null;
		
		Map<String, String> errorMap = new HashedMap<String, String>();
		List<Product> list = new ArrayList<Product>();
		Integer totalRowsInExcelFile = 0;		
		
		try(FileOutputStream fos = new FileOutputStream(completeFileName)) {
			byte[] data = file.getBytes();
			fos.write(data);
			
			try(Workbook workbook = new XSSFWorkbook(completeFileName)) {
				
				Sheet sheet = workbook.getSheet("products");
				Iterator<Row> rowIterator = sheet.rowIterator();
				
				while (rowIterator.hasNext()) {
					Row row = (Row) rowIterator.next();
					
					if(row.getRowNum() == 0) {
						continue; // if condition matched then continue will stop the condition and pointer will go to while loop again
					}
					
					Product product = new Product(); // creating project below if because if condition is excluding header and if object created above if then for 0th index which is header row one student object will be created.
					
					Iterator<Cell> cellIterator = row.cellIterator();
					
					while (cellIterator.hasNext()) {
						Cell cell = (Cell) cellIterator.next();
						int columnIndex = cell.getColumnIndex();
						
						switch (columnIndex) {
						case 0:{
							product.setProductName(cell.getStringCellValue());
							break;
						}case 1:{
							product.setSupplierId((long)cell.getNumericCellValue());	
							break;
						}case 2:{
							product.setCategoryId((long)cell.getNumericCellValue());
							break;
						}case 3:{
							product.setProductQuantity((long)cell.getNumericCellValue());
							break;
						}case 4:{
							product.setProductPrice(cell.getNumericCellValue());
							break;
						}
					}//end switch
				}//end while
					
					errorMap = validateFileProducts.validateFileProducts(product);
					
					if(errorMap.isEmpty()) {
						Product dBProduct = dao.getProductByName(product.getProductName());
						if(dBProduct == null) {
							list.add(product);
						}else {
							alreadyExist += 1;
							alreadyExistProductsRowNumbers.add(row.getRowNum()+1);
						}
					}else {
						notValidProduct.put(row.getRowNum()+1,errorMap );
					}
					
					totalRowsInExcelFile += 1;
					
				}
				
				//////////////////////////////////////////////// Changes Done With File Upload
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			addedCount = dao.uploadFile(list);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		responseMap.put("Total Record In Sheet : ", totalRowsInExcelFile);
		responseMap.put("Uploaded Record In Db", addedCount);
		responseMap.put("Total Exists Records In DB", alreadyExist);
		responseMap.put("Row Num, Exists Record In DB", alreadyExistProductsRowNumbers);
		responseMap.put("Excluded Record Count", notValidProduct.size());
		responseMap.put("Bad Records Row Num", notValidProduct);
		
		return responseMap;
	}

	
}
