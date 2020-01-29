package com.be.booker.business.entity.entitydto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RoomBookingNameAndSurnameDto {

    @NotNull(message = "Room name cannot be null")
    @NotEmpty(message = "Room name cannot be empty")
    @NotBlank(message = "Room name cannot be blank")
    private String roomName;

    @NotNull(message = "User name cannot be null")
    @NotEmpty(message = "User name cannot be empty")
    @NotBlank(message = "User name cannot be blank")
    private String userName;

    @NotNull(message = "Surname cannot be null")
    @NotEmpty(message = "Surname cannot be empty")
    @NotBlank(message = "Surname cannot be blank")
    private String surname;

    @NotNull(message = "Booked from date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime bookedFrom;

    @NotNull(message = "Booked to date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime bookedTo;

}
