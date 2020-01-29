package com.be.booker.business.entity.entitydto;

import lombok.*;

import javax.validation.constraints.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RoomDto {

    @NotNull(message = "Room name cannot be null")
    @NotEmpty(message = "Room name cannot be empty")
    @NotBlank(message = "Room name cannot be blank")
    @Size(max = 50)
    private String name;

    @Size(max = 256)
    private String locationDescription;

    @NotNull(message = "Number of seats cannot be null")
    @Min(1)
    @Max(100)
    private Integer numberOfSeats;

    private Boolean containProjector;

    @Size(max = 100)
    private String phoneNumber;

}
