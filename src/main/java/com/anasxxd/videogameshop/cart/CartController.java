package com.anasxxd.videogameshop.cart;

import com.anasxxd.videogameshop.cart.dto.AddCartItemRequest;
import com.anasxxd.videogameshop.cart.dto.CartItemResponse;
import com.anasxxd.videogameshop.cart.dto.CartResponse;
import com.anasxxd.videogameshop.cart.dto.UpdateCartItemRequest;
import com.anasxxd.videogameshop.cart.service.CartService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart/{userId}")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/items")
    public CartItemResponse upsertCartItem(
            @PathVariable Long userId,
            @Valid @RequestBody AddCartItemRequest request
    ){
        return cartService.upsertItem(userId, request);
    }

    @GetMapping()
    public CartResponse getCart(@PathVariable Long userId){
        return cartService.getCart(userId);
    }

    @GetMapping("/items/{productId}")
    public CartItemResponse getCartItemById(
            @PathVariable Long userId,
            @PathVariable Long productId
    ){
        return cartService.getCartItemById(userId, productId);
    }

    @PatchMapping("/items/{productId}")
    public void updateCartItem(
            @PathVariable Long userId,
            @PathVariable Long productId,
            @Valid @RequestBody UpdateCartItemRequest request
            ){
        cartService.updateCartItem(userId, productId, request);
    }

    @DeleteMapping("/items/{productId}")
    public void deleteCartItem(
            @PathVariable Long userId,
            @PathVariable Long productId){
        cartService.deleteCartItem(userId, productId);
    }

    @DeleteMapping()
    public void deleteCart(@PathVariable Long userId){
        cartService.deleteCart(userId);
    }
}