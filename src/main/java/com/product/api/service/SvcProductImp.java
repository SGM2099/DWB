package com.product.api.service;

import com.product.api.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.product.api.dto.ApiResponse;
import com.product.api.entity.Product;
import com.product.api.repository.RepoCategory;
import com.product.api.repository.RepoProduct;
import com.product.exception.ApiException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public class SvcProductImp implements SvcProduct {

    @Autowired
    RepoProduct repo;

    @Autowired
    RepoCategory repoCategory;

    @Override
    public List<Product> getProducts() {

        List<Product> products = repo.findByStatus(1);

        for (Product product : products) {
            product.setCategory(repoCategory.findByCategoryId(product.getCategory_id()));
        }

        return products;
    }

    @Override
    public Product getProduct(String gtin) {
        Product product = repo.getProductByGtin(gtin); // sustituir null por la llamada al método implementado en el repositorio
        if (product != null) {
            product.setCategory(repoCategory.findByCategoryId(product.getCategory_id()));
            return product;
        }else
            throw new ApiException(HttpStatus.NOT_FOUND, "product does not exist");
    }

    /*
	 * 4. Implementar el método createProduct considerando las siguientes validaciones:
  		1. validar que la categoría del nuevo producto exista
  		2. el código GTIN y el nombre del producto son únicos
  		3. si al intentar realizar un nuevo registro ya existe un producto con el mismo GTIN pero tiene estatus 0, 
  		   entonces se debe cambiar el estatus del producto existente a 1 y actualizar sus datos con los del nuevo registro
	 */
    @Override
    public ApiResponse createProduct(Product in) {

        Product productSaved = repo.findByProduct(in.getProduct());

        if (productSaved != null) {
            if (productSaved.getStatus() == 0){

                repo.activateProduct(productSaved.getProduct_id());
                return new ApiResponse("product has been activated");

            } else
                throw new ApiException(HttpStatus.BAD_REQUEST, "product name already exists");
        }

        Category category = repoCategory.findByCategoryId(in.getCategory_id());

        if (category == null){
            throw new ApiException(HttpStatus.NOT_FOUND, "category not found");
        }

        Product gtinCheck = repo.getProductByGtin(in.getGtin());

        if (gtinCheck != null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "product gtin already exist");
        }

        repo.createProduct(
                in.getGtin(),
                in.getProduct(),
                in.getDescription(),
                in.getPrice(),
                in.getStock(),
                in.getCategory_id()
        );

        return new ApiResponse("product created");

    }

    @Override
    public ApiResponse updateProduct(Product in, Integer id) {
        Integer updated = 0;
        try {
            updated = repo.updateProduct(id, in.getGtin(), in.getProduct(), in.getDescription(), in.getPrice(), in.getStock(), in.getCategory_id());
        }catch (DataIntegrityViolationException e) {
            if (e.getLocalizedMessage().contains("gtin"))
                throw new ApiException(HttpStatus.BAD_REQUEST, "product gtin already exist");
            if (e.getLocalizedMessage().contains("product"))
                throw new ApiException(HttpStatus.BAD_REQUEST, "product name already exist");
            if (e.contains(SQLIntegrityConstraintViolationException.class))
                throw new ApiException(HttpStatus.BAD_REQUEST, "category not found");
        }
        if(updated == 0)
            throw new ApiException(HttpStatus.BAD_REQUEST, "product cannot be updated");
        else
            return new ApiResponse("product updated");
    }

    @Override
    public ApiResponse deleteProduct(Integer id) {
        if (repo.deleteProduct(id) > 0)
            return new ApiResponse("product removed");
        else
            throw new ApiException(HttpStatus.BAD_REQUEST, "product cannot be deleted");
    }

    @Override
    public ApiResponse updateProductStock(String gtin, Integer stock) {
        Product product = getProduct(gtin);

        if (product == null)
            throw new ApiException(HttpStatus.NOT_FOUND, "product does not exist");
        if(stock > product.getStock())
            throw new ApiException(HttpStatus.BAD_REQUEST, "stock to update is invalid");

        repo.updateProductStock(gtin, product.getStock() - stock);
        return new ApiResponse("product stock updated");
    }

    @Override
    public ApiResponse updateProductCategory(String gtin, Integer category) {
        Product product = getProduct(gtin);
        Category categoryFound = repoCategory.findByCategoryId(category);

        if (product == null)
            throw new ApiException(HttpStatus.NOT_FOUND, "product does not exist");
        if (categoryFound == null)
            throw new ApiException(HttpStatus.BAD_REQUEST, "category not found");

        repo.updateProductCategory(gtin, category);
        return new ApiResponse("product category updated");
    }

}
