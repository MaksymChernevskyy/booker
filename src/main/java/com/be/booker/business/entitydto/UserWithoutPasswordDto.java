package com.be.booker.business.entitydto;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserWithoutPasswordDto {
    private String login;

    private String name;

    private String surname;

}
