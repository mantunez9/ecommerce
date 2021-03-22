package com.onebox.ecommerce.controller;

import com.onebox.ecommerce.dto.ResultActionCRUD;
import com.onebox.ecommerce.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {this.cartService = cartService;}

    @PostMapping
    public ResponseEntity<String> createCart() {

        ResultActionCRUD result = cartService.createCart();

        if (result.isOk()) {
            return ResponseEntity.ok(result.getMissatge());
        }

        return ResponseEntity.badRequest().body(result.getMissatge());

    }

}
