Freecell
========

A homework assignment for CSCI 200<br>
The assignment was to create a card game.
The requirements were:

You are to implement a simplified version of one of the card games below.

    Go Fish for 2 (Rules to follow)
    Poker for 2(lots of variations) (Rules to follow)
    Egyptian Rat Slap/Screw (Rules to follow)
    War for 2 players (Rules to follow)
    Blackjack for 2 (Rules to follow)
    Solitaire (Rules to follow)
    Bullshit for 2 (Rules to follow)
    Speed for 2 (Rules to follow)

You must have at least five classes in your implementation: application, Card, Deck, Player, and Game. The application class will extend JFrame. Nothing related to the card game itself is to be in the application class. You can prompt for a player name, etc., in the application class.

The Game class is to have all the rules of the game.

The Deck, Card, and Player, classes are pretty much self-explanatory

For input from the user, you can use the Scanner class - which means the player will have to click on the shell window to type input. On the other hand, for single key input, you can use a KeyListener, which allows the user to stay in the JFrame for entering keyboard characters. You can also use a MouseListener to click on "buttons" in the JFrame for input.

Assignment 2 - Card Game Version 2
You are to make your game a GUI-based game. This means you are to eliminate all command line input.You are to have the following additional capabilities:

    Menus for starting a game, stopping a game, pausing a game (if appropriate), exiting the application, creating a player, and listing all the players.
    Ability to save player data (use of Serializable interface) upon exiting the application and reading it at application startup
    Panel for creating new players
    Panel for displaying all existing players and their stats (like money, if a gambling game, or games won/lost for other games)
    Panel for selecting an existing player when wanting to play the game
    Panel for playing the game. This can be graphics-based, or Swing component-based

Other requirements were: No using arrays of any kind, for whatever reason.
