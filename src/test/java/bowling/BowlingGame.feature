Feature: Bowling Game

    Scenario: Throw the first ball of the frame
        Given a new bowling game
        When a player knocks down 4 pins
        Then the game score is 4
        
    Scenario: Throw the second ball of the first frame
        Given a new bowling game
        And a player knocks down 5 pins
        When a player knocks down 4 pins
        Then the game score is 9
        
    Scenario: Throw a gutter ball
        Given a new bowling game
        When a player knocks down 0 pins
        Then the game score is 0
        
    Scenario Outline: Negative tests - invalid number of pins
        Given a new bowling game
        When a player knocks down <numPins> pins
        Then an error occurs
        Examples:
            |numPins|
            | -1    |
            | 11    |
            | 10000 |
        