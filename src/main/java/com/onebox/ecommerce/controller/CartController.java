package com.onebox.ecommerce.controller;

import com.onebox.ecommerce.dto.CartDTO;
import com.onebox.ecommerce.dto.ProductDTO;
import com.onebox.ecommerce.dto.ResultActionCRUD;
import com.onebox.ecommerce.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<CartDTO> createCart() {
        Optional<CartDTO> optionalCartDTO = cartService.createCart();
        return optionalCartDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{idCart}")
    public ResponseEntity<Object> addProducts(@RequestBody List<ProductDTO> productDTOList, @PathVariable Integer idCart) {
        Optional<CartDTO> optionalCartDTO = cartService.addProductToCart(productDTOList, idCart);
        return optionalCartDTO.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error adding a product to the cart!"));
    }

    @GetMapping("/{idCart}")
    public ResponseEntity<Object> getCart(@PathVariable Integer idCart) {
        Optional<CartDTO> optionalCartDTO = cartService.findCart(idCart);
        return optionalCartDTO.<ResponseEntity<Object>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("The car does not exist!"));
    }

    @DeleteMapping("/{idCart}")
    public ResponseEntity<String> deleteCart(@PathVariable Integer idCart) {

        ResultActionCRUD result = cartService.deleteCart(idCart);

        if (result.isOk()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result.getMissatge());

    }

}
