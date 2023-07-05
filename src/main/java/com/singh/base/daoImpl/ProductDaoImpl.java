package com.singh.base.daoImpl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RestController;

import com.singh.base.dao.ProductDao;
import com.singh.base.entity.Product;

@RestController
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private SessionFactory factory;

	@Override
	public Boolean addProduct(Product product) {
		Boolean b = true;
		try (Session session = factory.openSession()) {
			session.save(product);
			session.beginTransaction().commit();
		} catch (Exception e) { 
			e.printStackTrace();
			//javax.persistence.RollbackException: Error while committing the transaction
			//Caused by: javax.persistence.PersistenceException: org.hibernate.exception.ConstraintViolationException:
			//Caused by: java.sql.SQLIntegrityConstraintViolationException: Duplicate entry
			b = false;
		}
		return b;
	}

	@Override
	public Product getProductById(Long productId) {
		Product product = null;
		try (Session session = factory.openSession()) {
			product = session.get(Product.class, productId);
		} catch (Exception e) {
			e.printStackTrace();
			product = null;
		}
		return product;
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<Product> getAllProducts() {
		List<Product> productList = null;
		try (Session session = factory.openSession()) {
			productList = session.createCriteria(Product.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			productList = null;
		}
		return productList;
	}

	@Override
	public Boolean deleteProduct(Long productId) {
		boolean status = true;
		try (Session session = factory.openSession()) {
			// Product product = this.getProductById(productId); // Not Working
			Product product = session.get(Product.class, productId);
			session.delete(product);
			session.beginTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		return status;
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public Boolean updateProductByProductId(Long productId, Map<String, Object> productFields) { //productFields value is in Integer
			
		Boolean status = true;
		try (Session session = factory.openSession()) {
			Product product = session.get(Product.class, productId);
			if (product != null) 
			{
				productFields.forEach((Key, Value) ->
				{
					Field findField = ReflectionUtils.findField(Product.class, Key);
					findField.setAccessible(true);

					// findField --> (Variable Name / Column Name)
					
					if(findField.getName().equalsIgnoreCase("productQuantity")) {
						
						Integer intValueOfProductQuantity = (Integer) productFields.get(findField.getName());
						@SuppressWarnings("removal")
						Long longValueOfProductQuantity = new Long(intValueOfProductQuantity);
						
						if(longValueOfProductQuantity instanceof Long) {
							ReflectionUtils.setField(findField, product, longValueOfProductQuantity);
						}
						
					}else {
						ReflectionUtils.setField(findField, product, Value);
					}
				});
				session.save(product);
				session.beginTransaction().commit();
				status = true;
			} else {
				status = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		return status;
	}

	@Override
	public List<Product> sortProducts(String fieldName, String order) {
		List<Product> list = null;
		try (Session session = factory.openSession()) {
			Criteria criteria = session.createCriteria(Product.class);
			if (order.equalsIgnoreCase("asc")) {
				criteria.addOrder(Order.asc(fieldName));
			} else {
				criteria.addOrder(Order.desc(fieldName));
			}
			list = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			list = null;
		}
		return list;
	}
	
	public Double getMaxPrice() {
		Double maxPrice = 0d;
		try(Session session = factory.openSession()) {
			@SuppressWarnings("deprecation")
			Criteria criteria = session.createCriteria(Product.class);
			criteria.setProjection(Projections.max("productPrice"));
			List list = criteria.list();
			if (!list.isEmpty()) {
				maxPrice = (Double) list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
			return maxPrice;
	}

	@Override
	public List<Product> getMaxPriceProducts() {
		List<Product> products = null;
		try(Session session = factory.openSession()) {
			Double maxPrice = getMaxPrice();
			if (maxPrice > 0) {
				Criteria criteria = session.createCriteria(Product.class);
				criteria.add(Restrictions.eq("productPrice", maxPrice));
				products = criteria.list();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return products;
	}

	@Override
	public Double countSumOfProductPrice() {
		Double sumOfProductPrice = 0d;
		try(Session session = factory.openSession()) {
			Criteria criteria = session.createCriteria(Product.class);
			criteria.setProjection(Projections.sum("productPrice"));
			List list = criteria.list();
			if (!list.isEmpty()) {
				sumOfProductPrice = (Double) list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sumOfProductPrice;
	}

	@Override
	public Long getTotalCountOfProducts() {
		long productCount = 0;
		try(Session session = factory.openSession()) {
			@SuppressWarnings("deprecation")
			Criteria criteria = session.createCriteria(Product.class);
			criteria.setProjection(Projections.rowCount());
			List list = criteria.list();
			if (!list.isEmpty()) {
				productCount = (Long) list.get(0);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return productCount;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Product getProductByName(String productName) {
		Product product = null;
		try(Session session = factory.openSession()) {
			Criteria criteria = session.createCriteria(Product.class);
			List list = criteria.add(Restrictions.ilike("productName", productName, MatchMode.ANYWHERE)).list();
			if(!list.isEmpty()) {
				product = (Product) list.get(0);
			}else {
				return product;
			}
		}catch (Exception e) {
			e.printStackTrace();
			product = null;
		}
		return product;
	}

	@Override
	public Integer uploadFile(List<Product> list){

		int productSaved = 0;

		for (Product product : list) {
			Boolean isAdded = addProduct(product);
			if(isAdded) {
				productSaved += 1;
			}
		}
		return productSaved;

	}
}
