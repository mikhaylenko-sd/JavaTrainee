package ru.digitalchief.javatrainee.mapper;

import ru.digitalchief.javatrainee.dto.OrderDto;
import ru.digitalchief.javatrainee.entity.Order;

public class OrderMapper {
    public static OrderDto toOrderDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .driverId(order.getDriverId())
                .clientId(order.getClientId())
                .startDate(order.getStartDate())
                .endDate(order.getEndDate())
                .price(order.getPrice())
                .paymentMethod(order.getPaymentMethod())
                .build();
    }

    public static Order toOrder(OrderDto orderDto) {
        return Order.builder()
                .id(orderDto.getId())
                .driverId(orderDto.getDriverId())
                .clientId(orderDto.getClientId())
                .startDate(orderDto.getStartDate())
                .endDate(orderDto.getEndDate())
                .price(orderDto.getPrice())
                .paymentMethod(orderDto.getPaymentMethod())
                .build();
    }
}
