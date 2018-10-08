#Author: abhilash04sharma@gmail.com
Feature: Demo Mobile App Login
  This feature deals with the basic element checks

  @sanity
  Scenario:
    Given Clean install and launch demo app
    Then Verify all main page elements are present

  @regression
  Scenario Outline:
    Given Clean install and launch demo app
    Then Verify Super Tackle "<MovePoint>" and Reset Functionality

  Examples:
    | MovePoint |
    | 2 |