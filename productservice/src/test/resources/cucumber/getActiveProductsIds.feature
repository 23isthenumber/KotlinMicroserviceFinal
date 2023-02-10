Feature: getActiveProductsIds

  Scenario: ActiveProducts
    Given list of product ids both with active and inactive status
    When I make a request
    Then response status is ok and request returns correct list of active products ids