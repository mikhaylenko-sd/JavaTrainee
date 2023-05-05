package ru.digitalchief.javatrainee.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.digitalchief.javatrainee.dto.OrderDto;
import ru.digitalchief.javatrainee.service.OrderService;
import ru.digitalchief.javatrainee.service.validation.OrderValidationService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    private final OrderValidationService orderValidationService;

    public OrderController(OrderService orderService, OrderValidationService orderValidationService) {
        this.orderService = orderService;
        this.orderValidationService = orderValidationService;
    }

    @GetMapping
    public List<OrderDto> findAllOrders() {
        log.info("Получен запрос к эндпоинту: GET /orders");
        return orderService.getAll();
    }

    @PostMapping
    public OrderDto createOrder(@RequestBody OrderDto orderDto) {
        log.info("Получен запрос к эндпоинту: POST /orders");
        orderValidationService.validateOrderCreate(orderDto);
        return orderService.create(orderDto);
    }

    @PutMapping
    public OrderDto updateOrder(@RequestBody OrderDto orderDto) {
        log.info("Получен запрос к эндпоинту: PUT /orders");
        orderValidationService.validateOrderUpdate(orderDto);
        return orderService.update(orderDto);
    }

    @PatchMapping(value = "/{orderId}")
    public OrderDto completeOrder(@PathVariable int orderId) {
        log.info("Получен запрос к эндпоинту: PATCH /orders/{}", orderId);
        return orderService.completeOrder(orderId);
    }

    @DeleteMapping
    public void deleteOrder(@RequestBody OrderDto orderDto) {
        log.info("Получен запрос к эндпоинту: DELETE /orders");
        orderService.deleteOrder(orderDto);
    }

    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable int id) {
        log.info("Получен запрос к эндпоинту: GET /orders/" + id);
        return orderService.getById(id);
    }
}
