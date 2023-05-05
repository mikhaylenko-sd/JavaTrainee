package ru.digitalchief.javatrainee.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.digitalchief.javatrainee.dto.ClientDto;
import ru.digitalchief.javatrainee.service.ClientService;
import ru.digitalchief.javatrainee.service.validation.ClientValidationService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/clients")
public class ClientController {
    private final ClientService clientService;
    private final ClientValidationService clientValidationService;

    public ClientController(ClientService clientService, ClientValidationService clientValidationService) {
        this.clientService = clientService;
        this.clientValidationService = clientValidationService;
    }

    @GetMapping
    public List<ClientDto> findAllClients() {
        log.info("Получен запрос к эндпоинту: GET /clients");
        return clientService.getAll();
    }

    @PostMapping
    public ClientDto createClient(@RequestBody ClientDto clientDto) {
        log.info("Получен запрос к эндпоинту: POST /clients");
        clientValidationService.validateClientCreate(clientDto);
        return clientService.create(clientDto);
    }

    @PutMapping
    public ClientDto updateClient(@RequestBody ClientDto clientDto) {
        log.info("Получен запрос к эндпоинту: PUT /clients");
        clientValidationService.validateClientUpdate(clientDto);
        return clientService.update(clientDto);
    }

    @DeleteMapping
    public void deleteClient(@RequestBody ClientDto clientDto) {
        log.info("Получен запрос к эндпоинту: DELETE /clients");
        clientService.deleteClient(clientDto);
    }

    @GetMapping("/{id}")
    public ClientDto getClient(@PathVariable int id) {
        log.info("Получен запрос к эндпоинту: GET /clients/" + id);
        return clientService.getById(id);
    }

}
