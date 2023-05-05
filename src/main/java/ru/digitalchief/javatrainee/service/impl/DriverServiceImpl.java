package ru.digitalchief.javatrainee.service.impl;

import org.springframework.stereotype.Service;
import ru.digitalchief.javatrainee.dto.DriverDto;
import ru.digitalchief.javatrainee.entity.Driver;
import ru.digitalchief.javatrainee.exception.NotFoundException;
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
        return DriverMapper.toDriverDto(driverRepository.save(driver));
    }

    @Override
    public DriverDto update(DriverDto driverDto) {
        merge(findById(driverDto.getId()), DriverMapper.toDriver(driverDto));
        return DriverMapper.toDriverDto(driverRepository.save(DriverMapper.toDriver(driverDto)));
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

    private void merge(Driver oldDriver, Driver newDriver) {
        String name = newDriver.getName();
        String middleName = newDriver.getMiddleName();
        String surname = newDriver.getSurname();
        Integer drivingExperience = newDriver.getDrivingExperience();
        String carNumber = newDriver.getCarNumber();

        if (name != null) {
            oldDriver.setName(name);
        }
        if (middleName != null) {
            oldDriver.setMiddleName(middleName);
        }
        if (surname != null) {
            oldDriver.setSurname(surname);
        }
        if (drivingExperience != null) {
            oldDriver.setDrivingExperience(drivingExperience);
        }
        if (carNumber != null && !oldDriver.getCarNumber().equals(carNumber)) {
            oldDriver.setCarNumber(carNumber);
        }
    }
}
