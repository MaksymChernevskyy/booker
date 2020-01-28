package com.be.booker.business.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "rooms")
@Data
@Setter
@Getter
@EqualsAndHashCode
public class Room {
    @Id
    @Column(name = "name")
    private String roomName;

    @Column(name = "locationDescription", columnDefinition = "varchar (255) default 'na'")
    private String locationDescription;

    @Column(name = "numberOfSeats")
    private int numberOfSeats;

    @Column(name = "isContainsProjecctor", columnDefinition = "boolean default false")
    private boolean containProjector;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public boolean isContainProjector() {
        return containProjector;
    }

    public void setContainProjector(boolean containProjector) {
        this.containProjector = containProjector;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
