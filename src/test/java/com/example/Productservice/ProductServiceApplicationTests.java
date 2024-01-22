package com.example.Productservice;


import com.example.productservice.entity.Product;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Optional;


@SpringBootTest
class ProductServiceApplicationTests {

	@InjectMocks
	private ProductService productService;

	@Mock
	private ProductRepository productRepository;

	@Test
	public void testGetAllProducts() {

		List<Product> mockProducts = Arrays.asList(
				new Product(1L, "Milk", 5, "Dairy", null, 100),
				new Product(2L, "Bread", 10, "Product", null, 400)
		);
		when(productRepository.findAll()).thenReturn(mockProducts);

		List<Product> result = productService.getAllProducts();


		assertEquals(2, result.size());
	}



	@Test
	public void testUpdateProduct() {

		Product mockProduct = new Product(1L, "Milk", 5, "Dairy", null, 100);
		when(productRepository.findById(any(Long.class))).thenReturn(Optional.of(mockProduct));
		when(productRepository.save(any(Product.class))).thenReturn(mockProduct);


		Product result = productService.updateProduct(1L, new Product());


		assertEquals(mockProduct, result);
	}


}

