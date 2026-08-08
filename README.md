# Tic Tac Toe (Java Swing)

A desktop Tic Tac Toe game built with Java Swing/AWT, contained in a single runnable class file.

## Features

- 3×3 grid of clickable buttons for placing X/O marks
- Status label showing whose turn it is, or the game result
- Automatic turn alternation between Player X and Player O
- Win detection across all 8 possible lines (rows, columns, diagonals)
- Winning line highlighted in green
- Draw detection when the board fills with no winner
- Result announced via a popup dialog and the status label
- "Reset / Play Again" button to start a new round

## Requirements

- Java Development Kit (JDK) 8 or later
- No external dependencies — uses only the standard `javax.swing` and `java.awt` libraries

## How to Run

1. Save the source file as `TicTacToe.java`.
2. Open a terminal in the folder containing the file.
3. Compile:

   ```bash
   javac TicTacToe.java
   ```

4. Run:

   ```bash
   java TicTacToe
   ```

A window will open with the game board. Click any empty cell to place your mark; turns alternate automatically starting with X.

## How to Play

1. Player X goes first — click any empty cell to place an X.
2. Player O takes the next turn, and turns continue to alternate.
3. The game ends when:
   - **A player wins** — three of their marks line up in a row, column, or diagonal. The winning cells turn green and a dialog announces the winner.
   - **The board fills up** with no winner — the game announces a draw.
4. Once the game ends, the board locks (no further moves) until you reset.
5. Click **Reset / Play Again** at any time to clear the board and start over, with X always going first.

## Project Structure

The entire game lives in one file, `TicTacToe.java`, organized into clearly separated methods:

| Method | Responsibility |
|---|---|
| `buildStatusPanel()` | Builds the top label showing turn/result |
| `buildBoardPanel()` | Builds the 3×3 grid of buttons |
| `buildControlPanel()` | Builds the bottom panel with the Reset button |
| `handleCellClick(row, col)` | Places a mark, disables the cell, checks win/draw |
| `switchPlayer()` | Alternates the current player and updates the label |
| `checkWinner()` | Scans all 8 win lines for a match |
| `highlightWinner(line)` | Colors the winning cells green |
| `isBoardFull()` | Checks for a draw condition |
| `endGame(message)` | Locks the board and shows the result dialog |
| `resetGame()` | Clears the board and resets all game state |

## Possible Extensions

- Scoreboard tracking wins/losses/draws across rounds
- Single-player mode with a simple AI opponent
- "Choose who goes first" option before each round
- Sound effects or animations on win

