Feature: Bowling Game
    Scenario: Throw the first ball of the first frame
        Given a new bowling game
        When a player knocks down 4 pins
        Then the game score is 4
        