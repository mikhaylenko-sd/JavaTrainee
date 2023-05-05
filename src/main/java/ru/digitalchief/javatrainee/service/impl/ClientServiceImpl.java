package ru.digitalchief.javatrainee.service.impl;

import org.springframework.stereotype.Service;
import ru.digitalchief.javatrainee.dto.ClientDto;
import ru.digitalchief.javatrainee.entity.Client;
import ru.digitalchief.javatrainee.exception.NotFoundException;
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
        return ClientMapper.toClientDto(clientRepository.save(client));
    }

    @Override
    public ClientDto update(ClientDto clientDto) {
        merge(findById(clientDto.getId()), ClientMapper.toClient(clientDto));
        return ClientMapper.toClientDto(clientRepository.save(ClientMapper.toClient(clientDto)));
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

    private void merge(Client oldClient, Client newClient) {
        String name = newClient.getName();
        String middleName = newClient.getMiddleName();
        String surname = newClient.getSurname();
        String email = newClient.getEmail();
        String phoneNumber = newClient.getPhoneNumber();

        if (name != null) {
            oldClient.setName(name);
        }
        if (middleName != null) {
            oldClient.setMiddleName(middleName);
        }
        if (surname != null) {
            oldClient.setSurname(surname);
        }
        if (email != null && !oldClient.getEmail().equals(email)) {
            oldClient.setEmail(email);
        }
        if (phoneNumber != null && !oldClient.getPhoneNumber().equals(phoneNumber)) {
            oldClient.setPhoneNumber(phoneNumber);
        }
    }
}
