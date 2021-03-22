package com.onebox.ecommerce.service;

import com.onebox.ecommerce.dto.ResultActionCRUD;
import com.onebox.ecommerce.model.Cart;
import com.onebox.ecommerce.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSSS");

    public CartServiceImpl(CartRepository cartRepository) {this.cartRepository = cartRepository;}

    @Override
    public ResultActionCRUD createCart() {

        try {

            Cart cart = new Cart();
            cart.setCreateDate(LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter));
            cartRepository.saveAndFlush(cart);

            return ResultActionCRUD.builder()
                    .isOk(true)
                    .missatge("The car has been created!")
                    .build();

        } catch (Exception e) {

            return ResultActionCRUD
                    .builder()
                    .isOk(false)
                    .missatge(e.getMessage())
                    .build();

        }

    }

}
