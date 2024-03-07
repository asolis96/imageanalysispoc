@Test
Feature: Google simple test

  @WWE
  Scenario: do a google search
    Given I'm on the google home page
    When I search for wwe
    Then I see results relating to wwe

  @F1
  Scenario: do a google search
    Given I'm on the google home page
    When I search for "F1"
    Then I see results relating to wwe