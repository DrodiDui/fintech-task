package by.kapitonov.fintech.task.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserAccountDTO {

    @NotBlank
    @Size(min = 3, max = 16)
    @Pattern(regexp = "^[A-Za-z]{3,16}$")
    private String username;

    @NotBlank
    @Size(min = 3, max = 16)
    @Pattern(regexp = "^[A-Za-z0-9]{3,16}$")
    private String password;

    @NotBlank
    @Size(min = 1, max = 16)
    @Pattern(regexp = "^[A-Za-z]{1,16}$")
    private String firstName;

    @NotBlank
    @Size(min = 1, max = 16)
    @Pattern(regexp = "^[A-Za-z]{1,16}$")
    private String lastName;

   /* private Role role;

    private Status status;*/
}
