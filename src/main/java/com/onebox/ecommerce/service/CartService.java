package com.onebox.ecommerce.service;

import com.onebox.ecommerce.dto.CartDTO;
import com.onebox.ecommerce.dto.ProductDTO;
import com.onebox.ecommerce.dto.ResultActionCRUD;

import java.util.List;
import java.util.Optional;

public interface CartService {

    Optional<CartDTO> createCart();

    Optional<CartDTO> findCart(Integer idCart);

    ResultActionCRUD deleteCart(Integer idCart);

    Optional<CartDTO> addProductToCart(List<ProductDTO> productDTOList, Integer idCart);

}
