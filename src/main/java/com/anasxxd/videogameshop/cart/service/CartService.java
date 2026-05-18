package com.anasxxd.videogameshop.cart.service;

import com.anasxxd.videogameshop.cart.CartItem;
import com.anasxxd.videogameshop.cart.dto.AddCartItemRequest;
import com.anasxxd.videogameshop.cart.dto.CartItemResponse;
import com.anasxxd.videogameshop.cart.dto.CartResponse;
import com.anasxxd.videogameshop.cart.dto.UpdateCartItemRequest;
import com.anasxxd.videogameshop.cart.repo.CartItemRepository;
import com.anasxxd.videogameshop.cart.repo.CartRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public CartItemResponse upsertItem(Long userId, AddCartItemRequest request){
        Long productId = request.getProductId();
        int quantity = request.getQuantity();

        CartItem cartItem = new CartItem(productId, quantity);
        validateCartItem(cartItem);

        Long cartId = cartRepository.findOrCreateCartId(userId);
        cartItemRepository.upsertItem(cartId, cartItem);

        return getCartItemResponse(cartItem);
    }

    @Transactional
    public CartResponse getCart(Long userId){
        Long cartId = cartRepository.findOrCreateCartId(userId);
        List<CartItem> cartItems = cartItemRepository.getCart(cartId);

        return getCartResponse(userId, cartItems);
    }

    @Transactional
    public CartItemResponse getCartItemById(Long userId, Long productId){
        return getCartItemResponse(findCartItemOrThrow(userId, productId));
    }

    @Transactional
    public void updateCartItem(Long userId, Long productId, UpdateCartItemRequest request){
        Long cartId = cartRepository.findOrCreateCartId(userId);
        CartItem cartItem = findCartItemOrThrow(userId, productId);

        if (request.getQuantity() != null){
            cartItem.setQuantity(request.getQuantity());
        }

        cartItemRepository.updateCartItem(cartId, cartItem);
    }

    @Transactional
    public void deleteCartItem(Long userId, Long productId){
        Long cartId = cartRepository.findOrCreateCartId(userId);
        findCartItemOrThrow(userId, productId);
        cartItemRepository.deleteCartItem(cartId, productId);
    }

    @Transactional
    public void deleteCart(Long userId){
        Long cartId = cartRepository.findOrCreateCartId(userId);
        cartItemRepository.deleteCart(cartId);
    }

    private static @NotNull CartItemResponse getCartItemResponse(CartItem cartItem){
        CartItemResponse response = new CartItemResponse();

        response.setProductId(cartItem.getProductId());
        response.setQuantity(cartItem.getQuantity());

        return response;
    }

    private static @NotNull CartResponse getCartResponse(Long userId, List<CartItem> cartItems){
       CartResponse response = new CartResponse();
       response.setUserId(userId);
       int numberOfItems = 0;

       for (CartItem cartItem : cartItems){
           numberOfItems++;
       }
       response.setNumberOfItems(numberOfItems);
       return response;
    }

    private CartItem findCartItemOrThrow(Long userId, Long productId){
        Long cartId = cartRepository.findOrCreateCartId(userId);
        Optional<CartItem> optionalCartItem = cartItemRepository.getCartItemById(cartId, productId);

        if(optionalCartItem.isPresent()){
            return optionalCartItem.get();
        }

        throw new IllegalArgumentException("This item does not exist in the cart with the Id: " + cartId);
    }


    private void validateCartItem(CartItem cartItem){
        if (cartItem.getQuantity() <= 0){
            throw new IllegalArgumentException("Quantity can not be less than 1");
        }
    }
}