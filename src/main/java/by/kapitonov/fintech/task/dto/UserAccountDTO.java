package by.kapitonov.fintech.task.dto;

import by.kapitonov.fintech.task.model.Role;
import by.kapitonov.fintech.task.model.Status;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserAccountDTO {

    @NotBlank
    @Size(min = 3, max = 16)
    private String username;

    @NotBlank
    @Size(min = 3, max = 16)
    private String password;

    @NotBlank
    @Size(min = 1, max = 16)
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 16)
    private String lastName;

    private Role role;

    private Status status;
}
