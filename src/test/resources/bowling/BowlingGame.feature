Feature: Bowling Game
 
    Scenario: Throw the first ball of the first frame
        Given a new bowling game
        And the game score is 0
        When the player scores 4 pins
        Then the game score is 4

    Scenario: Throw the second ball of the first frame
        Given a new bowling game
        And the player scores 5 pins
        And the game score is 5
        When the player scores 4 pins
        Then the game score is 9

    Scenario: Throw a gutter ball
        Given a new bowling game
        When the player scores 0 pins
        Then the game score is 0
  
    Scenario: The first ball after a spare frame is scored twice
        Given a new bowling game
        And these throws were recorded:
            | 5 | 5 |
        And the game score is 10
        When the player scores 4 pins
        Then the game score is 18
        
    Scenario: The first two balls thrown after a strike are scored twice
        Given a new bowling game
        And the player throws a strike
        And the game score is 10
        When the player scores 3 pins
        And the player scores 3 pins
        Then the game score is 22
    
    Scenario: Two scores in one frame adding up to greater than 10 should throw an error
        Given a new bowling game
        And the player scores 5 pins
        When the player scores 6 pins
        Then an error occurs 
    
    Scenario: A strike in the first throw will advance to the next frame
        Given a new bowling game
        And the player throws a strike
        And the current frame number is 1
        When the player scores 2 pins
        Then the current frame number is 2
    
    Scenario: Perfect game!
        Given a new bowling game
        And these throws were recorded:
            | 10 | 10 | 10 | 10 | 10 | 10 | 10 | 10 | 10 | 10 | 10 |
        When the player throws a strike
        Then the game score is 300
    
    Scenario Outline: Negative tests - invalid number of pins
        Given a new bowling game
        When the player scores <numPins> pins
        Then an error occurs
        Examples:
            |numPins|
            | -1    |
            | 11    |
            | 10000 |  