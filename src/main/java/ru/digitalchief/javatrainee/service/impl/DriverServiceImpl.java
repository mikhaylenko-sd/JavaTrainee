package ru.digitalchief.javatrainee.service.impl;

import org.springframework.stereotype.Service;
import ru.digitalchief.javatrainee.dto.DriverDto;
import ru.digitalchief.javatrainee.entity.Driver;
import ru.digitalchief.javatrainee.exception.NotFoundException;
import ru.digitalchief.javatrainee.exception.ValidationException;
import ru.digitalchief.javatrainee.mapper.DriverMapper;
import ru.digitalchief.javatrainee.repository.DriverRepository;
import ru.digitalchief.javatrainee.service.DriverService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;

    public DriverServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public List<DriverDto> getAll() {
        return driverRepository.findAll().stream()
                .map(DriverMapper::toDriverDto)
                .collect(Collectors.toList());
    }

    @Override
    public DriverDto create(DriverDto driverDto) {
        Driver driver = DriverMapper.toDriver(driverDto);
        validateCarNumberUnique(driver.getCarNumber(), null);
        return DriverMapper.toDriverDto(driverRepository.save(driver));
    }

    @Override
    public DriverDto update(DriverDto driverDto) {
        Driver result = merge(findById(driverDto.getId()), DriverMapper.toDriver(driverDto));
        validateCarNumberUnique(result.getCarNumber(), result.getId());
        return DriverMapper.toDriverDto(driverRepository.save(result));
    }

    @Override
    public void deleteDriver(DriverDto driverDto) {
        Driver driver = findById(driverDto.getId());
        driverRepository.delete(driver);
    }

    @Override
    public DriverDto getById(int id) {
        return DriverMapper.toDriverDto(findById(id));
    }

    private Driver findById(int driverId) {
        return driverRepository.findById(driverId)
                .orElseThrow(() -> new NotFoundException("Водителя с id = " + driverId + " не существует."));
    }

    private Driver merge(Driver oldDriver, Driver newDriver) {
        Driver result = new Driver();
        result.setId(oldDriver.getId());

        result.setName(newDriver.getName() != null ? newDriver.getName() : oldDriver.getName());
        result.setSurname(newDriver.getSurname() != null ? newDriver.getSurname() : oldDriver.getSurname());
        result.setDrivingExperience(newDriver.getDrivingExperience() != null
                ? newDriver.getDrivingExperience() : oldDriver.getDrivingExperience());
        result.setMiddleName(newDriver.getMiddleName() != null ? newDriver.getMiddleName() : oldDriver.getMiddleName());
        result.setCarNumber(newDriver.getCarNumber() != null ? newDriver.getCarNumber() : oldDriver.getCarNumber());

        return result;
    }

    private void validateCarNumberUnique(String carNumber, Integer id) {
        Driver driver = driverRepository.findDriverByCarNumber(carNumber);
        if (driver != null && !driver.getId().equals(id)) {
            throw new ValidationException("Водитель с таким номером автомобиля уже существует.");
        }
    }
}
