package ru.digitalchief.javatrainee.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class OrderDto {
    Integer id;
    Integer driverId;
    Integer clientId;
    LocalDateTime startDate;
    LocalDateTime endDate;
    Integer price;
    Integer paymentMethod;

}
