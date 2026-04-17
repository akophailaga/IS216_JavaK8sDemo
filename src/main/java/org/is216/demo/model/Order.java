package org.is216.demo.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    private String id;
    private String customer;
    private String item;
    private String status;

    public Order() {
    }

    public Order(String customer, String item, String status) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.customer = customer;
        this.item = item;
        this.status = status;
    }

    public Order(String id, String customer, String item, String status) {
        this.id = id;
        this.customer = customer;
        this.item = item;
        this.status = status;
    }

    public String getId() { return id; }
    public String getCustomer() { return customer; }
    public String getItem() { return item; }
    public String getStatus() { return status; }
}