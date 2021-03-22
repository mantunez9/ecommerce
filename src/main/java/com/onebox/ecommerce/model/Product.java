package com.onebox.ecommerce.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product")
@Data
public class Product implements Serializable {

    private static final long serialVersionUID = 1;

    @Id
    @Column(name = "id_product")
    @SequenceGenerator(name = "productIdGenerator", sequenceName = "product_id_product_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productIdGenerator")
    private Integer idProduct;

    private String description;

    private double amount;

    @ManyToOne
    @JoinColumn(name = "id_cart", insertable = false, updatable = false)
    private Cart cart;

}
