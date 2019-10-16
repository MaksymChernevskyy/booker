package com.be.booker.business.entity;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Room {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String roomName;
  private String locationDescription;
  private int numberOfSeats;
  private boolean containProjector;
  private String phoneNumber;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Room)) {
      return false;
    }
    Room room = (Room) o;
    return numberOfSeats == room.numberOfSeats &&
        containProjector == room.containProjector &&
        Objects.equals(id, room.id) &&
        Objects.equals(roomName, room.roomName) &&
        Objects.equals(locationDescription, room.locationDescription) &&
        Objects.equals(phoneNumber, room.phoneNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, roomName, locationDescription, numberOfSeats, containProjector, phoneNumber);
  }

  @Override
  public String toString() {
    return "Room{" +
        "id=" + id +
        ", roomName='" + roomName + '\'' +
        ", locationDescription='" + locationDescription + '\'' +
        ", numberOfSeats=" + numberOfSeats +
        ", containProjector=" + containProjector +
        ", phoneNumber='" + phoneNumber + '\'' +
        '}';
  }
}
