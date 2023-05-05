package ru.digitalchief.javatrainee.service.impl;

import org.springframework.stereotype.Service;
import ru.digitalchief.javatrainee.dto.ClientDto;
import ru.digitalchief.javatrainee.entity.Client;
import ru.digitalchief.javatrainee.exception.NotFoundException;
import ru.digitalchief.javatrainee.exception.ValidationException;
import ru.digitalchief.javatrainee.mapper.ClientMapper;
import ru.digitalchief.javatrainee.repository.ClientRepository;
import ru.digitalchief.javatrainee.service.ClientService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<ClientDto> getAll() {
        return clientRepository.findAll().stream()
                .map(ClientMapper::toClientDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClientDto create(ClientDto clientDto) {
        Client client = ClientMapper.toClient(clientDto);
        validateEmailUnique(client.getEmail(), null);
        validatePhoneNumberUnique(client.getPhoneNumber(), client.getId());
        return ClientMapper.toClientDto(clientRepository.save(client));
    }

    @Override
    public ClientDto update(ClientDto clientDto) {
        Client result = merge(findById(clientDto.getId()), ClientMapper.toClient(clientDto));
        validateEmailUnique(result.getEmail(), result.getId());
        validatePhoneNumberUnique(result.getPhoneNumber(), result.getId());
        return ClientMapper.toClientDto(clientRepository.save(result));
    }

    @Override
    public void deleteClient(ClientDto clientDto) {
        Client client = findById(clientDto.getId());
        clientRepository.delete(client);
    }

    @Override
    public ClientDto getById(int id) {
        return ClientMapper.toClientDto(findById(id));
    }

    private Client findById(int clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Клиента с id = " + clientId + " не существует."));
    }

    private Client merge(Client oldClient, Client newClient) {
        Client result = new Client();
        result.setId(oldClient.getId());

        result.setName(newClient.getName() != null ? newClient.getName() : oldClient.getName());
        result.setSurname(newClient.getSurname() != null ? newClient.getSurname() : oldClient.getSurname());
        result.setMiddleName(newClient.getMiddleName() != null ? newClient.getMiddleName() : oldClient.getMiddleName());
        result.setPhoneNumber(newClient.getPhoneNumber() != null ? newClient.getPhoneNumber() : oldClient.getPhoneNumber());
        result.setEmail(newClient.getEmail() != null ? newClient.getEmail() : oldClient.getEmail());

        return result;
    }

    private void validateEmailUnique(String email, Integer id) {
        Client client = clientRepository.findClientByEmail(email);
        if (client != null && !client.getId().equals(id)) {
            throw new ValidationException("Пользователь с таким email уже существует.");
        }
    }

    private void validatePhoneNumberUnique(String phoneNumber, Integer id) {
        Client client = clientRepository.findClientByPhoneNumber(phoneNumber);
        if (client != null && !client.getId().equals(id)) {
            throw new ValidationException("Пользователь с таким номером телефона уже существует.");
        }
    }
}
