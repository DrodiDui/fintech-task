package by.kapitonov.fintech.task.util;


import by.kapitonov.fintech.task.dto.UserAccountDTO;
import by.kapitonov.fintech.task.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

public class UserAccountValidator implements Validator {

    private static final Pattern usernamePattern = Pattern.compile("^[A-Za-z]{3,16}$");
    private static final Pattern passwordPattern = Pattern.compile("^[A-Za-z0-9]{3,16}$");
    private static final Pattern firstNamePattern = Pattern.compile("^[A-Za-z]{1,16}$");
    private static final Pattern lastNamePattern = Pattern.compile("^[A-Za-z]{1,16}$");

    @Autowired
    private UserAccountRepository userAccountRepository;

    public static boolean isValidUsername(String username) {
        return usernamePattern.matcher(username).matches();
    }

    public static boolean isValidPassword(String password) {
        return passwordPattern.matcher(password).matches();
    }

    public static boolean isValidFirstName(String firstName) {
        return firstNamePattern.matcher(firstName).matches();
    }

    public static boolean isValidLastName(String lastName) {
        return lastNamePattern.matcher(lastName).matches();
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserAccountDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserAccountDTO userAccountDTO = (UserAccountDTO) target;

        if (userAccountRepository.findByUsername(userAccountDTO.getUsername()).isPresent()) {
            errors.rejectValue("createUserAccountDTO.exist", "UserAccount already exists");
        }

        if (!isValidUsername(userAccountDTO.getUsername())) {
            errors.rejectValue("username", "Invalid username");
        }

        if (!isValidPassword(userAccountDTO.getPassword())) {
            errors.rejectValue("password", "Invalid password");
        }

        if (!isValidFirstName(userAccountDTO.getFirstName())) {
            errors.rejectValue("firstName", "Invalid first name");
        }

        if (!isValidLastName(userAccountDTO.getLastName())) {
            errors.rejectValue("lastName", "Invalid last name");
        }

    }
}
