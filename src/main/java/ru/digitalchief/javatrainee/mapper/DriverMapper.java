package ru.digitalchief.javatrainee.mapper;

import ru.digitalchief.javatrainee.dto.DriverDto;
import ru.digitalchief.javatrainee.entity.Driver;

public class DriverMapper {
    public static DriverDto toDriverDto(Driver driver) {
        return DriverDto.builder()
                .id(driver.getId())
                .name(driver.getName())
                .middleName(driver.getMiddleName())
                .surname(driver.getSurname())
                .carNumber(driver.getCarNumber())
                .drivingExperience(driver.getDrivingExperience())
                .build();
    }

    public static Driver toDriver(DriverDto driverDto) {
        return Driver.builder()
                .id(driverDto.getId())
                .name(driverDto.getName())
                .middleName(driverDto.getMiddleName())
                .surname(driverDto.getSurname())
                .carNumber(driverDto.getCarNumber())
                .drivingExperience(driverDto.getDrivingExperience())
                .build();
    }
}
