package by.kapitonov.fintech.task.dto;

import by.kapitonov.fintech.task.model.Role;
import by.kapitonov.fintech.task.model.Status;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserAccountDTO {

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private Role role;

    private Status status;
}
