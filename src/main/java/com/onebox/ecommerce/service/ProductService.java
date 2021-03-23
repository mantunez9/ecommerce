package com.onebox.ecommerce.service;

import com.onebox.ecommerce.dto.ProductDTO;
import com.onebox.ecommerce.dto.ResultActionCRUD;
import com.onebox.ecommerce.model.Cart;

import java.util.List;

public interface ProductService {

    ResultActionCRUD addProduct(Cart cart, List<ProductDTO> productDTOList);

    List<ProductDTO> findAllProductByCart(Cart cart);

}
