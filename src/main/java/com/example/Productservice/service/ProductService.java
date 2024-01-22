package com.example.productservice.service;


import com.example.productservice.entity.Product;
import com.example.productservice.exception.ProductNotFoundException;
import com.example.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public Product createProduct(Product product) {
        // Perform any additional validation or business logic before saving
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    public Product updateProduct(Long id, Product updatedProduct) {
        // Perform any additional validation or business logic before updating
        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isPresent()) {
            // Update fields of existing product with the data from updatedProduct
            Product productToUpdate = existingProduct.get();
            productToUpdate.setName(updatedProduct.getName());
            productToUpdate.setQty(updatedProduct.getQty());
            productToUpdate.setCategory(updatedProduct.getCategory());
            productToUpdate.setExpiryDate(updatedProduct.getExpiryDate());
            productToUpdate.setPrice(updatedProduct.getPrice());

            return productRepository.save(productToUpdate);
        } else {
            // Handle product not found
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
    }

    @Transactional
    public void deleteProduct(Long id) {
        // Perform any additional validation or business logic before deleting
        productRepository.deleteById(id);
    }
}