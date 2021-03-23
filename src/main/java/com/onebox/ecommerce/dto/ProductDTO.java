package com.onebox.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {

    private Integer idProduct;

    private String description;

    private double amount;

    @JsonIgnore
    private CartDTO cartDTO;

}
