package com.janith.Service;

import com.janith.model.Order;
import com.janith.model.User;
import com.janith.request.OrderRequest;

import java.util.List;

public interface OrderService {

    public Order createOrder(OrderRequest order, User user) throws Exception;

    public Order updateOrder(Long orderId, String orderStatus) throws Exception;

    public void cancelOrder(Long orderId) throws Exception;

    public List<Order> getUsersOrder(Long userId) throws Exception;

    public List<Order> getShopsOrder(Long shopId, String orderStatus) throws Exception;

    public Order findOrderById(Long orderId) throws Exception;

}
