
Feature: Dvla Test
  This Feature is to test Dvla Application

 
Scenario: Default API Listing
    Given a request to the API to list files
    And get a csv file from the list
    Then confirm all vehicles license