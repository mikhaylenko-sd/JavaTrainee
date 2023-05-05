package ru.digitalchief.javatrainee.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class ClientDto {
    private Integer id;
    private String name;
    private String middleName;
    private String surname;
    private String email;
    private String phoneNumber;
}
