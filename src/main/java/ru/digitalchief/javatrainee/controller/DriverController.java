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
import ru.digitalchief.javatrainee.dto.DriverDto;
import ru.digitalchief.javatrainee.service.DriverService;
import ru.digitalchief.javatrainee.service.validation.DriverValidationService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/drivers")
public class DriverController {
    private final DriverService driverService;

    private final DriverValidationService driverValidationService;

    public DriverController(DriverService driverService, DriverValidationService driverValidationService) {
        this.driverService = driverService;
        this.driverValidationService = driverValidationService;
    }

    @GetMapping
    public List<DriverDto> findAllDrivers() {
        log.info("Получен запрос к эндпоинту: GET /drivers");
        return driverService.getAll();
    }

    @PostMapping
    public DriverDto createDriver(@RequestBody DriverDto driverDto) {
        log.info("Получен запрос к эндпоинту: POST /drivers");
        driverValidationService.validateDriverCreate(driverDto);
        return driverService.create(driverDto);
    }

    @PutMapping
    public DriverDto updateDriver(@RequestBody DriverDto driverDto) {
        log.info("Получен запрос к эндпоинту: PUT /drivers");
        driverValidationService.validateDriverUpdate(driverDto);
        return driverService.update(driverDto);
    }

    @DeleteMapping
    public void deleteDriver(@RequestBody DriverDto driverDto) {
        log.info("Получен запрос к эндпоинту: DELETE /drivers");
        driverService.deleteDriver(driverDto);
    }

    @GetMapping("/{id}")
    public DriverDto getDriver(@PathVariable int id) {
        log.info("Получен запрос к эндпоинту: GET /drivers/" + id);
        return driverService.getById(id);
    }
}
