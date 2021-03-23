package com.onebox.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CartDTO {

    private Integer idCart;

    private LocalDateTime createDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ProductDTO> products;

}
