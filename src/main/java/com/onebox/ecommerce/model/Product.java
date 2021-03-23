package com.onebox.ecommerce.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product")
@Data
public class Product implements Serializable {

    private static final long serialVersionUID = 1;

    @EmbeddedId
    private ProductPK id;

    private String description;

    private double amount;

    @ManyToOne
    @JoinColumn(name = "id_cart", insertable = false, updatable = false)
    private Cart cart;

}
