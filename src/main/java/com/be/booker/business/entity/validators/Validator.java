package com.be.booker.business.entity.validators;

import java.util.List;

class Validator {
  static void addResultOfValidation(List<String> resultList, List<String> resultsOfValidation) {
    resultList.addAll(resultsOfValidation);
  }

  static void addResultOfValidation(List<String> resultList, String resultOfValidation) {
    if (resultOfValidation != null) {
      resultList.add(resultOfValidation);
    }
  }
}
