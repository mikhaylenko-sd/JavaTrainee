package ru.digitalchief.javatrainee.service.impl;

import org.springframework.stereotype.Service;
import ru.digitalchief.javatrainee.dto.OrderDto;
import ru.digitalchief.javatrainee.entity.Order;
import ru.digitalchief.javatrainee.exception.NotFoundException;
import ru.digitalchief.javatrainee.mapper.OrderMapper;
import ru.digitalchief.javatrainee.repository.OrderRepository;
import ru.digitalchief.javatrainee.service.ClientService;
import ru.digitalchief.javatrainee.service.DriverService;
import ru.digitalchief.javatrainee.service.OrderService;
import ru.digitalchief.javatrainee.service.validation.OrderValidationService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ClientService clientService;
    private final DriverService driverService;
    private final OrderValidationService orderValidationService;

    public OrderServiceImpl(OrderRepository orderRepository, ClientService clientService, DriverService driverService, OrderValidationService orderValidationService) {
        this.orderRepository = orderRepository;
        this.clientService = clientService;
        this.driverService = driverService;
        this.orderValidationService = orderValidationService;
    }

    @Override
    public List<OrderDto> getAll() {
        return orderRepository.findAll().stream()
                .map(OrderMapper::toOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto create(OrderDto orderDto) {
        Order order = OrderMapper.toOrder(orderDto);
        clientService.getById(orderDto.getClientId());
        driverService.getById(orderDto.getDriverId());
        order.setStartDate(LocalDateTime.now());
        return OrderMapper.toOrderDto(orderRepository.save(order));
    }

    @Override
    public OrderDto completeOrder(int orderId) {
        Order order = findById(orderId);
        order.setEndDate(LocalDateTime.now());
        orderValidationService.validateTime(OrderMapper.toOrderDto(order));
        return OrderMapper.toOrderDto(orderRepository.save(order));
    }

    @Override
    public OrderDto update(OrderDto orderDto) {
        Order result = merge(findById(orderDto.getId()), OrderMapper.toOrder(orderDto));
        clientService.getById(result.getClientId());
        driverService.getById(result.getDriverId());
        return OrderMapper.toOrderDto(orderRepository.save(result));
    }

    @Override
    public void deleteOrder(OrderDto orderDto) {
        Order order = findById(orderDto.getId());
        orderRepository.delete(order);
    }

    @Override
    public OrderDto getById(int orderId) {
        return OrderMapper.toOrderDto(findById(orderId));
    }

    private Order findById(int orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Заказа с id = " + orderId + " не существует."));
    }

    private Order merge(Order oldOrder, Order newOrder) {
        Order result = new Order();
        result.setId(oldOrder.getId());
        result.setStartDate(oldOrder.getStartDate());
        result.setEndDate(oldOrder.getEndDate());

        result.setDriverId(newOrder.getDriverId() != null ? newOrder.getDriverId() : oldOrder.getDriverId());
        result.setClientId(newOrder.getClientId() != null ? newOrder.getClientId() : oldOrder.getClientId());
        result.setPaymentMethod(newOrder.getPaymentMethod() != null ? newOrder.getPaymentMethod() : oldOrder.getPaymentMethod());
        result.setPrice(newOrder.getPrice() != null ? newOrder.getPrice() : oldOrder.getPrice());

        return result;
    }
}
