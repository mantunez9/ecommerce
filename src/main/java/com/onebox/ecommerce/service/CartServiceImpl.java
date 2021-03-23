package com.onebox.ecommerce.service;

import com.onebox.ecommerce.dto.CartDTO;
import com.onebox.ecommerce.dto.ProductDTO;
import com.onebox.ecommerce.dto.ResultActionCRUD;
import com.onebox.ecommerce.model.Cart;
import com.onebox.ecommerce.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSSS");

    public CartServiceImpl(CartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    @Override
    public Optional<CartDTO> createCart() {

        try {

            Cart cart = new Cart();
            cart.setCreateDate(LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter));
            cart = cartRepository.saveAndFlush(cart);

            return Optional.ofNullable(
                    CartDTO.builder()
                            .idCart(cart.getIdCart())
                            .createDate(cart.getCreateDate())
                            .build()
            );

        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.empty();
        }

    }

    @Override
    public Optional<CartDTO> findCart(Integer idCart) {

        Optional<Cart> optionalCart = cartRepository.findByIdCart(idCart);

        return optionalCart.map(cart -> CartDTO
                .builder()
                .idCart(cart.getIdCart())
                .createDate(cart.getCreateDate())
                .products(productService.findAllProductByCart(cart))
                .build()
        );

    }

    @Override
    public ResultActionCRUD deleteCart(Integer idCart) {

        try {

            Optional<Cart> optionalCart = cartRepository.findByIdCart(idCart);

            if (optionalCart.isPresent()) {

                cartRepository.delete(optionalCart.get());

                return ResultActionCRUD
                        .builder()
                        .isOk(true)
                        .build();

            }

            return ResultActionCRUD
                    .builder()
                    .isOk(false)
                    .missatge("The car does not exist!")
                    .build();

        } catch (Exception e) {

            log.error(e.getMessage());
            return ResultActionCRUD
                    .builder()
                    .isOk(false)
                    .missatge("Error deleting the cart!")
                    .build();

        }

    }

    @Override
    public Optional<CartDTO> addProductToCart(List<ProductDTO> productDTOList, Integer idCart) {

        Optional<Cart> optionalCartDTO = cartRepository.findByIdCart(idCart);

        if (optionalCartDTO.isPresent()) {

            ResultActionCRUD resultActionCRUD = productService.addProduct(optionalCartDTO.get(), productDTOList);

            if (resultActionCRUD.isOk()) {
                return findCart(idCart);
            }

        }

        return Optional.empty();

    }

}
