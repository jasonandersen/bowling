Feature: Bowling Game
 
    Scenario: Throw the first ball of the first frame
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

    Scenario: The first ball after a spare frame is scored twice
        Given a new bowling game
        And these throws were recorded:
            | 5 | 5 |
        When a player knocks down 4 pins
        Then the game score is 18
        
    Scenario: The first two balls thrown after a strike are scored twice
        Given a new bowling game
        And these throws were recorded:
            | 10 |
        When a player knocks down 3 pins
        And a player knocks down 3 pins
        Then the game score is 22
