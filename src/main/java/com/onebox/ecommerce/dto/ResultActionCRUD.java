package com.onebox.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultActionCRUD {

    private String missatge;

    private boolean isOk;

}
