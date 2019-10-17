package com.be.booker.business.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BookingRoom {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private LocalDateTime bookedFrom;
  private LocalDateTime bookedTo;
  private String userName;
  private String roomName;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getBookedFrom() {
    return bookedFrom;
  }

  public void setBookedFrom(LocalDateTime bookedFrom) {
    this.bookedFrom = bookedFrom;
  }

  public LocalDateTime getBookedTo() {
    return bookedTo;
  }

  public void setBookedTo(LocalDateTime bookedTo) {
    this.bookedTo = bookedTo;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getRoomName() {
    return roomName;
  }

  public void setRoomName(String roomName) {
    this.roomName = roomName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BookingRoom)) {
      return false;
    }
    BookingRoom that = (BookingRoom) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(bookedFrom, that.bookedFrom) &&
        Objects.equals(bookedTo, that.bookedTo) &&
        Objects.equals(userName, that.userName) &&
        Objects.equals(roomName, that.roomName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, bookedFrom, bookedTo, userName, roomName);
  }

  @Override
  public String toString() {
    return "BookingRoom{" +
        "id=" + id +
        ", bookedFrom=" + bookedFrom +
        ", bookedTo=" + bookedTo +
        ", userName='" + userName + '\'' +
        ", roomName='" + roomName + '\'' +
        '}';
  }
}
