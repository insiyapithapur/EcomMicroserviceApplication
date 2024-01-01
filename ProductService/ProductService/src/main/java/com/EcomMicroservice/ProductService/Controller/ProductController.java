package com.EcomMicroservice.ProductService.Controller;
import com.EcomMicroservice.ProductService.Collections.Product;
import com.EcomMicroservice.ProductService.Repository.ProductRepository;
import com.EcomMicroservice.ProductService.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("PS")
public class ProductController {
    @Autowired private MongoTemplate MT;
    private ProductService PS;

    @Autowired
    public ProductController(ProductService productService) {
        this.PS = productService;
    }

    @PostMapping("/CreateProduct")
    public ResponseEntity<String> CreateProduct(@RequestBody Product product){
        String Name = product.getName();
        String Category = product.getCategory();
        Query query = new Query();
        query.addCriteria(Criteria.where("Name").is(Name).and("Category").is(Category));
        boolean exists  = MT.exists(query, Product.class);
        if(!exists) {
            PS.save(product);
            return ResponseEntity.status(HttpStatus.CREATED).body("Product created successfully.");
        }
        else{
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Product with the same name and category already exists.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Product>> ListOfProducts()
    {
        List<Product> products = PS.getProducts();
        if(products.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        else {
            return ResponseEntity.ok().body(products);
        }
    }

    @GetMapping("/Category/{Category}")
    public ResponseEntity<List<Product>> ListOfProductsByCategory(@PathVariable String Category)
    {
        List<Product> products = PS.getProductsByCategory(Category);
        if(products.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        else {
            return ResponseEntity.ok().body(products);
        }
    }

    @GetMapping("/Tag/{Tag}")
    public ResponseEntity<List<Product>> ListOfProductsByTag(@PathVariable String Tag)
    {
        List<Product> products = PS.getProductsByTag(Tag);
        if(products.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        else {
            return ResponseEntity.ok().body(products);
        }
    }

    @GetMapping("/productId/{Product_ID}")
    public ResponseEntity<List<Object>> ProductByProduct_ID(@PathVariable String Product_ID)
    {
        Optional<Product> product = PS.getProductByProduct_ID(Product_ID);
        if(product.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        else {
            return ResponseEntity.ok().body(Collections.singletonList(product));
        }
    }

    @DeleteMapping("/{Product_ID}")
    public ResponseEntity<String> deleteProduct(@PathVariable String Product_ID){
        PS.DeleteTheProduct(Product_ID);
        return ResponseEntity.status(HttpStatus.OK).body("Done");
    }
}
