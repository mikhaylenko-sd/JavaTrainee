package ru.digitalchief.javatrainee.service;

import ru.digitalchief.javatrainee.dto.DriverDto;

import java.util.List;

public interface DriverService {
    List<DriverDto> getAll();

    DriverDto create(DriverDto driverDto);

    DriverDto update(DriverDto driverDto);

    void deleteDriver(DriverDto driverDto);

    DriverDto getById(int id);
}
