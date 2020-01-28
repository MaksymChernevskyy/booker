package com.be.booker.business.usecases.booking;

import com.be.booker.business.exceptions.BadRequestException;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class DateChecker {

  void dateCheckerForSave(LocalDateTime bookedFrom, LocalDateTime bookedTo) {
    if (bookedFrom == null || bookedTo == null) {
      return;
    }
    if (bookedTo.isBefore(LocalDateTime.now()) || bookedFrom.isBefore(LocalDateTime.now())) {
      throw new BadRequestException("You cant book room in the past");
    }
    if (bookedTo.equals(bookedFrom)) {
      throw new BadRequestException("End date is the same like start date.");
    }
    if (bookedTo.isBefore(bookedFrom)) {
      throw new BadRequestException("End date can't be before start date.");
    }
  }

  void dateCheckerForSearching(LocalDateTime bookedFrom, LocalDateTime bookedTo) {
    if (bookedTo.equals(bookedFrom)) {
      throw new BadRequestException("End date is the same like start date.");
    }
    if (bookedTo.isBefore(bookedFrom)) {
      throw new BadRequestException("End date can't be before start date.");
    }
  }
}
