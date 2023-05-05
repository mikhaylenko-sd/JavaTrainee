package ru.digitalchief.javatrainee.service.validation;

import org.springframework.stereotype.Component;
import ru.digitalchief.javatrainee.dto.DriverDto;
import ru.digitalchief.javatrainee.exception.ValidationException;

import java.util.regex.Pattern;

@Component
public class DriverValidationService {
    public void validateDriverCreate(DriverDto driverDto) {
        validateName(driverDto);
        validateSurname(driverDto);
        validateDrivingExperience(driverDto);
        validateCarNumber(driverDto);
    }

    public void validateDriverUpdate(DriverDto driverDto) {
        if (driverDto.getName() != null) {
            validateName(driverDto);
        }
        if (driverDto.getSurname() != null) {
            validateSurname(driverDto);
        }
        if (driverDto.getDrivingExperience() != null) {
            validateDrivingExperience(driverDto);
        }
        if (driverDto.getCarNumber() != null) {
            validateCarNumber(driverDto);
        }
    }

    private void validateName(DriverDto driverDto) {
        String name = driverDto.getName();
        if (name == null || name.isBlank()) {
            throw new ValidationException("Ошибка валидации. Имя водителя не может быть пустым.");
        }
    }

    private void validateSurname(DriverDto driverDto) {
        String surname = driverDto.getSurname();
        if (surname == null || surname.isBlank()) {
            throw new ValidationException("Ошибка валидации. Фамилия водителя не может быть пустой.");
        }
    }

    private void validateDrivingExperience(DriverDto driverDto) {
        Integer drivingExperience = driverDto.getDrivingExperience();
        if (drivingExperience == null || drivingExperience <= 0) {
            throw new ValidationException("Ошибка валидации. Стаж водителя должен быть положительным значением.");
        }
    }

    private void validateCarNumber(DriverDto driverDto) {
        Pattern p = Pattern.compile(
                "([АВЕКМНОРСТУХ]\\d{3}[АВЕКМНОРСТУХ]{1,2})(\\d{2,3})");
        String carNumber = driverDto.getCarNumber();
        if (carNumber == null || !p.matcher(carNumber).matches()) {
            throw new ValidationException("Ошибка валидации. Проверьте формат введенного автомобильного номера.");
        }
    }
}
