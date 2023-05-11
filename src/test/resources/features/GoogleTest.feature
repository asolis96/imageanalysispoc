Feature: Google simple test

  Scenario: do a google search
    Given I'm on the google home page
    When I search for wwe
    Then I see results relating to wwe