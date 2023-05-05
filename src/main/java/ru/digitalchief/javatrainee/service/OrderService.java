package ru.digitalchief.javatrainee.service;

import ru.digitalchief.javatrainee.dto.OrderDto;

import java.util.List;

public interface OrderService {
    List<OrderDto> getAll();

    OrderDto create(OrderDto orderDto);

    OrderDto update(OrderDto orderDto);

    void deleteOrder(OrderDto orderDto);

    OrderDto completeOrder(int orderId);

    OrderDto getById(int orderId);
}
