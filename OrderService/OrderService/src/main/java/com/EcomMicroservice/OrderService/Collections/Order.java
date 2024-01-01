package com.EcomMicroservice.OrderService.Collections;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Builder
@Data
@Document(collection = "Order")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Order {
    @Id
    private String id;
    private String Product_id;
    private String Name;
    private String Description;
    private int Price;
    private List<String> size;
    private String Category;
    private List<String> Images;
    private List<String> Tags;
    private Integer quantity;
}
//    @Id
//    private String Order_ID;
////    User_ID
////    Quantity;
//    private float Total_Price;
//    private Date creationDate;
//    private String Status;
//    private String id;
//    private String Name;
//    private String Description;
//    private int  Price;
//    private List<String> size;
//    private String Category;
//    private List<String> Images;
//    private List<String> Tags;
//    private Integer quantity;
//    public void setCreationDate() {
//        this.creationDate = new Date();
//    }
