#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template

Feature: Book an Appointment
  Scenario: Successful booking of an appointment
    Given the user is on the CURA Healthcare login page
    When the user logs in with valid credentials
    And the user books an appointment with the following details:
      | Facility                         | Healthcare Program | Visit Date | Comment |
      | Hongkong CURA Healthcare Center  | Medicaid           | 31/08/2024 | today   |
    Then the appointment should be confirmed
    And the submitted data should be verified
    And the user logs out