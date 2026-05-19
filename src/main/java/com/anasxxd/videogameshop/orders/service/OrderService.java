package com.anasxxd.videogameshop.orders.service;

import com.anasxxd.videogameshop.cart.service.CartService;
import com.anasxxd.videogameshop.orders.dto.OrderResponse;
import com.anasxxd.videogameshop.orders.repo.OrderItemRepository;
import com.anasxxd.videogameshop.orders.repo.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartService cartService;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartService = cartService;
    }

    @Transactional
    public OrderResponse checkOut(Long userId){
        cartService.
    }

}
