package com.product.api.repository;

import javax.transaction.Transactional;

import com.product.api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.product.api.entity.Product;

@Repository
public interface RepoProduct extends JpaRepository<Product, Integer>{
	
	// 3. Implementar la firma de un método que permita consultar un producto por su código GTIN y con estatus 1
    @Query(value = "SELECT * FROM Product WHERE gtin = :gtin AND status = 1", nativeQuery = true)
    Product getProductByGtin(@Param("gtin") String gtin);

    @Query(value = "SELECT * FROM product WHERE product_id = :product_id AND status = 1", nativeQuery = true)
    Category findByProductId(@Param("product_id") Integer product_id);

    @Query(value = "SELECT * FROM product WHERE product = :product", nativeQuery = true)
    Product findByProduct(@Param("product") String product);

    @Modifying
    @org.springframework.transaction.annotation.Transactional
    @Query(value = "INSERT INTO product (gtin, product, description, price, stock, category_id, status) " +
            "VALUES (:gtin, :product, :description, :price, :stock, :category_id, 1)p", nativeQuery = true)
    void createProduct (
            @Param("gtin") String gtin,
            @Param("product") String product,
            @Param("description") String description,
            @Param("price") Double price,
            @Param("stock") Integer stock,
            @Param("category_id") Integer category_id
    );
	
	@Modifying
	@Transactional
	@Query(value ="UPDATE product "
					+ "SET gtin = :gtin, "
						+ "product = :product, "
						+ "description = :description, "
						+ "price = :price, "
						+ "stock = :stock, "
						+ "status = 1, "
						+ "category_id = :category_id "
					+ "WHERE product_id = :product_id", nativeQuery = true)
	Integer updateProduct(
			@Param("product_id") Integer product_id,
			@Param("gtin") String gtin, 
			@Param("product") String product, 
			@Param("description") String description, 
			@Param("price") Double price, 
			@Param("stock") Integer stock,
			@Param("category_id") Integer category_id
		);

    @Modifying
    @org.springframework.transaction.annotation.Transactional
    @Query(value = "UPDATE product SET status = 1 WHERE product_id = :product_id", nativeQuery = true)
    Integer activateProduct (@Param("product_id") Integer product_id);
	
	@Modifying
	@Transactional
	@Query(value ="UPDATE product SET status = 0 WHERE product_id = :product_id AND status = 1", nativeQuery = true)
	Integer deleteProduct(@Param("product_id") Integer product_id);
	
	@Modifying
	@Transactional
	@Query(value ="UPDATE product SET stock = :stock WHERE gtin = :gtin AND status = 1", nativeQuery = true)
	Integer updateProductStock(@Param("gtin") String gtin, @Param("stock") Integer stock);

}
