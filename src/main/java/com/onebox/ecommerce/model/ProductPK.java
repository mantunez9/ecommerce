package com.onebox.ecommerce.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class ProductPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "id_cart", insertable = false, updatable = false)
    private Integer idCart;

    @Column(name = "id_product")
    private Integer idProduct;

}
