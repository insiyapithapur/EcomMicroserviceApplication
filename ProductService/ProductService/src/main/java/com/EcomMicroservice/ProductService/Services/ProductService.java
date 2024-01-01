package com.EcomMicroservice.ProductService.Services;
import com.EcomMicroservice.ProductService.Collections.Product;
import com.EcomMicroservice.ProductService.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired private MongoTemplate MT;
    private ProductRepository PR;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.PR = productRepository;
    }
    public String save(Product product) {
        return PR.save(product).getId();
    }

    public List<Product> getProducts() {
        return PR.findAll();
    }

    public List<Product> getProductsByCategory(String Category) {
        Query query = new Query();
        query.addCriteria(Criteria.where("Category").is(Category));
        return MT.find(query,Product.class);
    }

    public List<Product> getProductsByTag(String Tag) {
        Query query = new Query();
        query.addCriteria(Criteria.where("Tags").in(Tag));
        return MT.find(query , Product.class);
    }

    public Optional<Product> getProductByProduct_ID(String Product_ID) {
        Optional<Product> product = PR.findById(Product_ID);
        return  product;
    }

    public void DeleteTheProduct(String Product_ID) {
        PR.deleteById(Product_ID);
    }
}
