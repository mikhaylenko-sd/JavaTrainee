package ru.digitalchief.javatrainee.service.validation;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import ru.digitalchief.javatrainee.dto.ClientDto;
import ru.digitalchief.javatrainee.exception.ValidationException;

import java.util.regex.Pattern;

@Component
public class ClientValidationService {
    public void validateClientCreate(ClientDto clientDto) {
        validateName(clientDto);
        validateSurname(clientDto);
        validateEmail(clientDto);
        validatePhoneNumber(clientDto);
    }

    public void validateClientUpdate(ClientDto clientDto) {
        if (clientDto.getName() != null) {
            validateName(clientDto);
        }
        if (clientDto.getSurname() != null) {
            validateSurname(clientDto);
        }
        if (clientDto.getEmail() != null) {
            validateEmail(clientDto);
        }
        if (clientDto.getPhoneNumber() != null) {
            validatePhoneNumber(clientDto);
        }
    }

    private void validateEmail(ClientDto clientDto) {
        EmailValidator validator = EmailValidator.getInstance();
        if (!validator.isValid(clientDto.getEmail())) {
            throw new ValidationException("Ошибка валидации. Проверьте корректность адреса электронной почты.");
        }
    }

    private void validateName(ClientDto clientDto) {
        String name = clientDto.getName();
        if (name == null || name.isBlank()) {
            throw new ValidationException("Ошибка валидации. Имя клиента не может быть пустым.");
        }
    }

    private void validateSurname(ClientDto clientDto) {
        String surname = clientDto.getSurname();
        if (surname == null || surname.isBlank()) {
            throw new ValidationException("Ошибка валидации. Фамилия клиента не может быть пустой.");
        }
    }

    private void validatePhoneNumber(ClientDto clientDto) {
        Pattern p = Pattern.compile(
                "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$");
        String phoneNumber = clientDto.getPhoneNumber();
        if (phoneNumber == null || !p.matcher(phoneNumber).matches()) {
            throw new ValidationException("Ошибка валидации. Проверьте формат введенного номера.");
        }
    }
}
