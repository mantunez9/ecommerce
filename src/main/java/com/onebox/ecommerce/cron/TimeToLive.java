package com.onebox.ecommerce.cron;

import com.onebox.ecommerce.model.Cart;
import com.onebox.ecommerce.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.TimerTask;

@Slf4j
public class TimeToLive extends TimerTask {

    private final CartRepository cartRepository;
    private final Cart cart;
    private final Long ttl;

    public TimeToLive(CartRepository cartRepository, Cart cart, Long ttl) {
        this.cartRepository = cartRepository;
        this.cart = cart;
        this.ttl = ttl;
    }

    // Clear cache.
    public void run() {
        cartRepository.delete(cart);
        log.info("The TTL is over, cached cleared!");
    }

    public Long getTTL() {
        return ttl - (LocalDateTime.now().toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli()
                - cart.getCreateDate().toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli());
    }

}
