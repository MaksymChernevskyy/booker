package com.be.booker.business.entitydto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserDto {

    @NotNull(message = "Login cannot be null")
    @NotEmpty(message = "Login cannot be empty")
    @NotBlank(message = "Login cannot be blank")
    @Size(max = 100)
    private String login;

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 50)
    private String name;

    @NotNull(message = "Surname cannot be null")
    @NotEmpty(message = "Surname cannot be empty")
    @NotBlank(message = "Surname cannot be blank")
    @Size(max = 100)
    private String surname;

    @NotNull(message = "Password cannot be null")
    @NotEmpty(message = "Password cannot be empty")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, max = 100)
    private String password;
}