package com.be.booker.business.entitydto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RoomBookingDto {


    @NotNull(message = "Room name cannot be null")
    @NotEmpty(message = "Room name cannot be empty")
    @NotBlank(message = "Room name cannot be blank")
    private String roomName;

    @NotNull(message = "User login cannot be null")
    @NotEmpty(message = "User login cannot be empty")
    @NotBlank(message = "User login cannot be blank")
    private String userLogin;

    @NotNull(message = "Booked from date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime bookedFrom;

    @NotNull(message = "Booked to date cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime bookedTo;

}
