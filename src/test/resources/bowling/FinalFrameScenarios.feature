Feature: Final frame of the game
    
    Scenario: An open frame in the 10th frame, the game should be over after two throws
        Given a bowling game at the beginning of the final frame with a zero score
        When the player scores 1 pin
        And the player scores 7 pins
        Then the game is over
    
    Scenario: An spare in the 10th frame, the game should not be over
        Given a bowling game at the beginning of the final frame with a zero score
        When the player scores 6 pins
        And the player scores 4 pins
        Then the game is not over
    
    Scenario: An spare in the 10th frame followed by a third throw, the game should be over
        Given a bowling game at the beginning of the final frame with a zero score
        When the player scores 6 pins
        And the player scores 4 pins
        And the player scores 5 pins
        Then the game is over
    
    Scenario: Three strikes in the 10th frame, the game should be over
        Given a bowling game at the beginning of the final frame with a zero score
        When the player throws a strike
        And the player throws a strike
        And the player throws a strike
        Then the game is over