package com.onebox.ecommerce.service;

import com.onebox.ecommerce.cron.TimeToLive;
import com.onebox.ecommerce.dto.CartDTO;
import com.onebox.ecommerce.dto.ProductDTO;
import com.onebox.ecommerce.dto.ResultActionCRUD;
import com.onebox.ecommerce.model.Cart;
import com.onebox.ecommerce.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;
    Long ttl = TimeUnit.MINUTES.toMillis(10);

    public CartServiceImpl(CartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    @Override
    public Optional<CartDTO> createCart() {

        try {

            Cart cart = new Cart();
            cart.setCreateDate(LocalDateTime.now());

            Timer t = new Timer();
            TimeToLive timeToLive = new TimeToLive(cartRepository, cart, ttl);
            t.schedule(timeToLive, ttl);

            cart = cartRepository.saveAndFlush(cart);

            return Optional.ofNullable(
                    CartDTO.builder()
                            .idCart(cart.getIdCart())
                            .createDate(cart.getCreateDate())
                            .ttl(timeToLive.getTTL())
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

        if (optionalCart.isPresent()) {

            TimeToLive timeToLive = new TimeToLive(cartRepository, optionalCart.get(), ttl);

            return optionalCart.map(cart -> CartDTO
                    .builder()
                    .idCart(cart.getIdCart())
                    .createDate(cart.getCreateDate())
                    .products(productService.findAllProductByCart(cart))
                    .ttl(timeToLive.getTTL())
                    .build()
            );

        }

        return Optional.empty();

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
