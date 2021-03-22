package com.onebox.ecommerce.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NamedEntityGraph(name = "productJoin", attributeNodes = {
        @NamedAttributeNode("products")
})
public class Cart implements Serializable {

    private static final long serialVersionUID = 1;

    @Id
    @SequenceGenerator(name = "cartIdGenerator", sequenceName = "cart_id_cart_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cartIdGenerator")
    @Column(name = "id_cart")
    private Integer idCart;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "cart")
    private List<Product> products;

}
