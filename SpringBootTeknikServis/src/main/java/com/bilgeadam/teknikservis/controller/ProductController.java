package com.bilgeadam.teknikservis.controller;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bilgeadam.teknikservis.model.Product;
import com.bilgeadam.teknikservis.model.Sale;
import com.bilgeadam.teknikservis.repository.ProductRepository;
import com.bilgeadam.teknikservis.service.ProductService;

@RestController
@RequestMapping(path = "/product")
@io.swagger.v3.oas.annotations.tags.Tag(description = "Product Endpointleri", name = "Product")
public class ProductController {
	 private final ProductRepository productRepository;
	    private final ProductService productService;
	    
	public ProductController(ProductRepository productRepository, ProductService productService) {
			super();
			this.productRepository = productRepository;
			this.productService = productService;
		}
	@GetMapping("/getAllProduct")
    @Operation(description = "Başarılı olursa 200", summary ="Ürünleri gösterir")
    @ApiResponses(value =
            { @ApiResponse(responseCode = "200", description = "Başarılı olursa"),@ApiResponse(responseCode = "500", description = "Başarılı olmaz ise")})

    public ResponseEntity<List<Product>> getAllProduct(){
        try {
            List<Product> temp = productService.GetAllProduct();
            return ResponseEntity.ok(temp);
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

}
