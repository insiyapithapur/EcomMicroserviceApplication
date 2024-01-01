package com.EcomMicroservice.ProductService.Collections;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Builder
@Document(collection = "product")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product {
    @Id
    private String id;
    private String Name;
    private String Description;
    private int  Price;
    private List<String> size;
    private String Category;
    private List<String> Images;
    private List<String> Tags;
    private Integer quantity;
// tax
}
