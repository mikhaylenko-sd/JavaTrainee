package ru.digitalchief.javatrainee.service;

import ru.digitalchief.javatrainee.dto.ClientDto;

import java.util.List;

public interface ClientService {
    List<ClientDto> getAll();

    ClientDto create(ClientDto clientDto);

    ClientDto update(ClientDto clientDto);

    void deleteClient(ClientDto clientDto);

    ClientDto getById(int id);
}
