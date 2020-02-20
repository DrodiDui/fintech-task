package by.kapitonov.fintech.task.util;


import by.kapitonov.fintech.task.dto.UserAccountDTO;
import by.kapitonov.fintech.task.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class UserAccountValidator implements Validator {

    private static final Pattern usernamePattern = Pattern.compile("^[A-Za-z]*$");
    private static final Pattern passwordPattern = Pattern.compile("^[A-Za-z0-9]*$");
    private static final Pattern firstNamePattern = Pattern.compile("^[A-Za-z]*$");
    private static final Pattern lastNamePattern = Pattern.compile("^[A-Za-z]*$");

    private final UserAccountRepository userAccountRepository;

    public UserAccountValidator(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

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


        //username validation
        if (userAccountRepository.findByUsername(userAccountDTO.getUsername()).isPresent()) {
            errors.rejectValue("username", "", "UserAccount already exists");
        }

        if (!isValidUsername(userAccountDTO.getUsername())) {
            errors.rejectValue("username", "", "Username can only contain letters");
        }

        if (userAccountDTO.getUsername() == null || userAccountDTO.getUsername().equals("")) {
            errors.rejectValue("username", "", "Username cannot be empty");
        }

        if (userAccountDTO.getUsername().length() < 3 || userAccountDTO.getUsername().length() > 16) {
            errors.rejectValue("username", "", "Username cannot be less than 3 and more than 16");
        }


        //password validation
        if (!isValidPassword(userAccountDTO.getPassword())) {
            errors.rejectValue("password", "", "Password can only contain letters and numbers");
        }

        if (userAccountDTO.getPassword() == null || userAccountDTO.getPassword().equals("")) {
            errors.rejectValue("password", "", "Password cannot be empty");
        }

        if (userAccountDTO.getPassword().length() < 3 || userAccountDTO.getPassword().length() > 16) {
            errors.rejectValue("password", "", "Password cannot be less than 3 and more than 16");
        }

        //firstName validation
        if (!isValidFirstName(userAccountDTO.getFirstName())) {
            errors.rejectValue("firstName", "", "First name can only contain letters and numbers");
        }

        if (userAccountDTO.getFirstName() == null || userAccountDTO.getFirstName().equals("")) {
            errors.rejectValue("firstName", "", "First name cannot be empty");
        }

        if (userAccountDTO.getFirstName().length() < 1 || userAccountDTO.getFirstName().length() > 16) {
            errors.rejectValue("firstName", "", "First name cannot be less than 1 and more than 16");
        }

        //lastName validation
        if (!isValidLastName(userAccountDTO.getLastName())) {
            errors.rejectValue("lastName", "", "Last name can only contain letters and numbers");
        }

        if (userAccountDTO.getLastName() == null || userAccountDTO.getLastName().equals("")) {
            errors.rejectValue("lastName", "", "Last name cannot be empty");
        }

        if (userAccountDTO.getLastName().length() < 1 || userAccountDTO.getLastName().length() > 16) {
            errors.rejectValue("lastName", "", "Last name cannot be less than 1 and more than 16");
        }
    }
}
