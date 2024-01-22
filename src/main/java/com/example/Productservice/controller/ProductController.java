package com.example.productservice.controller;

import com.example.productservice.entity.Product;
import com.example.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {

        if (product.getQty() >= 500 || !isValidCategory(product.getCategory())
                || product.getExpiryDate().before(new Date()) || !isValidProductName(product.getName())
                || product.getPrice() <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Product savedProduct = productRepository.save(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        Product updatedProduct = productRepository.save(product);
        if (productRepository.existsById(id) && (product.getId() != null || product.getExpiryDate() != null)) {
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        }

        if (!isValidCategory(product.getCategory()) || !isValidProductName(product.getName())
                || product.getPrice() <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    private boolean isValidCategory(String category) {
        return category.equals("Dairy") || category.equals("Pharma") || category.equals("Product");
    }

    private boolean isValidProductName(String name) {
        return name.matches("^[A-Za-z].*");
    }
}
