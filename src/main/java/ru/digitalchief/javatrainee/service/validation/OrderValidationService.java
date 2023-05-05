package ru.digitalchief.javatrainee.service.validation;

import org.springframework.stereotype.Component;
import ru.digitalchief.javatrainee.dto.OrderDto;
import ru.digitalchief.javatrainee.exception.ValidationException;

import java.time.LocalDateTime;

@Component
public class OrderValidationService {
    public void validateOrderCreate(OrderDto orderDto) {
        validateId(orderDto.getClientId());
        validateId(orderDto.getDriverId());
        validatePrice(orderDto);
        validatePaymentMethod(orderDto);

        if (orderDto.getEndDate() != null) {
            throw new ValidationException("Ошибка валидации. Заказ не может завершиться, не начавшись.");
        }
    }

    public void validateOrderUpdate(OrderDto orderDto) {
        if (orderDto.getClientId() != null) {
            validateId(orderDto.getClientId());
        }
        if (orderDto.getDriverId() != null) {
            validateId(orderDto.getDriverId());
        }
        if (orderDto.getPrice() != null) {
            validatePrice(orderDto);
        }
        if (orderDto.getPaymentMethod() != null) {
            validatePaymentMethod(orderDto);
        }
    }

    private void validateId(Integer id) {
        if (id == null || id < 0) {
            throw new ValidationException("Ошибка валидации. Идентификаторы клиента и водителя должны быть неотрицательными значениями.");
        }
    }

    private void validatePrice(OrderDto orderDto) {
        Integer price = orderDto.getPrice();
        if (price == null || price <= 0) {
            throw new ValidationException("Ошибка валидации. Стоимость заказа должна быть положительным значением.");
        }
    }

    private void validatePaymentMethod(OrderDto orderDto) {
        Integer paymentMethod = orderDto.getPaymentMethod();
        if (paymentMethod == null || (paymentMethod != 1 && paymentMethod != 2)) {
            throw new ValidationException("Ошибка валидации. Проверьте правильность метода оплаты: 1 - наличные, 2 - безналичные.");
        }
    }

    public void validateTime(OrderDto orderDto) {
        LocalDateTime start = orderDto.getStartDate();
        LocalDateTime end = orderDto.getEndDate();
        if (start == null || end == null) {
            throw new ValidationException("Дата и время не могут быть пустыми.");
        }
        if (end.isBefore(start)) {
            throw new ValidationException("Дата и время оканчания заказа не могут быть раньше начала.");
        }
        if (start.equals(end)) {
            throw new ValidationException("Дата и время начала и конца заказа не могут совпадать.");
        }
    }
}
