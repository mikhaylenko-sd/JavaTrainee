package ru.digitalchief.javatrainee.mapper;

import ru.digitalchief.javatrainee.dto.ClientDto;
import ru.digitalchief.javatrainee.entity.Client;

public class ClientMapper {
    public static ClientDto toClientDto(Client client) {
        return ClientDto.builder()
                .id(client.getId())
                .name(client.getName())
                .middleName(client.getMiddleName())
                .surname(client.getSurname())
                .email(client.getEmail())
                .phoneNumber(client.getPhoneNumber())
                .build();
    }

    public static Client toClient(ClientDto clientDto) {
        return Client.builder()
                .id(clientDto.getId())
                .name(clientDto.getName())
                .middleName(clientDto.getMiddleName())
                .surname(clientDto.getSurname())
                .email(clientDto.getEmail())
                .phoneNumber(clientDto.getPhoneNumber())
                .build();
    }
}
