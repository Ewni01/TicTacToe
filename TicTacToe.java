import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * TicTacToe - A single-file Java Swing desktop Tic Tac Toe game.
 *
 * Structure:
 *  - Fields: game state (board buttons, current player, move count, game-over flag)
 *  - Constructor: builds the UI (top label, 3x3 grid, reset button)
 *  - Core methods:
 *      handleCellClick(...)  -> places a mark and checks game state
 *      checkWinner()         -> scans all win lines, returns winning line or null
 *      highlightWinner(...)  -> colors the winning cells green
 *      isBoardFull()         -> draw detection
 *      endGame(...)          -> disables board, shows dialog, updates label
 *      resetGame()           -> clears board, re-enables buttons, resets state
 */
public class TicTacToe extends JFrame {

    private static final int SIZE = 3;
    private static final Color DEFAULT_BG = UIManager.getColor("Button.background");
    private static final Color WIN_BG = new Color(46, 204, 113); // green

    private final JButton[][] cells = new JButton[SIZE][SIZE];
    private final JLabel statusLabel = new JLabel("Player X's Turn", SwingConstants.CENTER);
    private final JButton resetButton = new JButton("Reset / Play Again");

    private char currentPlayer = 'X';
    private int movesMade = 0;
    private boolean gameOver = false;

    // All 8 possible winning lines, expressed as {row, col} coordinate triples.
    private static final int[][][] WIN_LINES = {
            {{0,0},{0,1},{0,2}}, // rows
            {{1,0},{1,1},{1,2}},
            {{2,0},{2,1},{2,2}},
            {{0,0},{1,0},{2,0}}, // columns
            {{0,1},{1,1},{2,1}},
            {{0,2},{1,2},{2,2}},
            {{0,0},{1,1},{2,2}}, // diagonals
            {{0,2},{1,1},{2,0}}
    };

    public TicTacToe() {
        super("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(buildStatusPanel(), BorderLayout.NORTH);
        add(buildBoardPanel(), BorderLayout.CENTER);
        add(buildControlPanel(), BorderLayout.SOUTH);

        pack();
        setMinimumSize(new Dimension(400, 480));
        setLocationRelativeTo(null); // center on screen
    }

    // ---------- UI BUILDING METHODS ----------

    private JPanel buildStatusPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        panel.add(statusLabel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildBoardPanel() {
        JPanel boardPanel = new JPanel(new GridLayout(SIZE, SIZE, 5, 5));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JButton cell = new JButton("");
                cell.setFont(new Font("SansSerif", Font.BOLD, 48));
                cell.setFocusPainted(false);
                cell.setPreferredSize(new Dimension(100, 100));

                final int r = row;
                final int c = col;
                cell.addActionListener(e -> handleCellClick(r, c));

                cells[row][col] = cell;
                boardPanel.add(cell);
            }
        }
        return boardPanel;
    }

    private JPanel buildControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        resetButton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        resetButton.addActionListener(e -> resetGame());
        panel.add(resetButton);
        return panel;
    }

    // ---------- CORE GAME LOGIC ----------

    /**
     * Handles a click on a board cell: places the current player's mark,
     * checks for a win/draw, and either ends the game or switches turns.
     */
    private void handleCellClick(int row, int col) {
        if (gameOver) return;

        JButton clicked = cells[row][col];
        if (!clicked.getText().isEmpty() || !clicked.isEnabled()) {
            return; // already occupied
        }

        clicked.setText(String.valueOf(currentPlayer));
        // Set the color before disabling so it stays visible once disabled.
        clicked.setForeground(currentPlayer == 'X' ? Color.BLUE : Color.RED);
        clicked.setEnabled(false);
        movesMade++;

        int[][] winningLine = checkWinner();
        if (winningLine != null) {
            highlightWinner(winningLine);
            endGame("Player " + currentPlayer + " Wins!");
        } else if (isBoardFull()) {
            endGame("It's a Draw!");
        } else {
            switchPlayer();
        }
    }

    /** Switches the active player and updates the status label. */
    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        statusLabel.setText("Player " + currentPlayer + "'s Turn");
    }

    /**
     * Scans all 8 win lines. Returns the coordinate triple of the winning
     * line if found, otherwise null.
     */
    private int[][] checkWinner() {
        for (int[][] line : WIN_LINES) {
            String a = cells[line[0][0]][line[0][1]].getText();
            String b = cells[line[1][0]][line[1][1]].getText();
            String c = cells[line[2][0]][line[2][1]].getText();

            if (!a.isEmpty() && a.equals(b) && b.equals(c)) {
                return line;
            }
        }
        return null;
    }

    /** Colors the three winning cells green. */
    private void highlightWinner(int[][] winningLine) {
        for (int[] coord : winningLine) {
            cells[coord[0]][coord[1]].setBackground(WIN_BG);
            cells[coord[0]][coord[1]].setOpaque(true);
        }
    }

    /** Returns true if every cell has been filled (used for draw detection). */
    private boolean isBoardFull() {
        return movesMade == SIZE * SIZE;
    }

    /**
     * Finalizes the game: disables remaining buttons, updates the status
     * label, and shows a result dialog.
     */
    private void endGame(String message) {
        gameOver = true;
        statusLabel.setText(message);

        for (JButton[] row : cells) {
            for (JButton cell : row) {
                cell.setEnabled(false);
            }
        }

        JOptionPane.showMessageDialog(
                this,
                message,
                "Game Over",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /** Clears the board, re-enables all buttons, and resets turn state to 'X'. */
    private void resetGame() {
        for (JButton[] row : cells) {
            for (JButton cell : row) {
                cell.setText("");
                cell.setEnabled(true);
                cell.setBackground(DEFAULT_BG);
                cell.setOpaque(false);
            }
        }
        currentPlayer = 'X';
        movesMade = 0;
        gameOver = false;
        statusLabel.setText("Player X's Turn");
    }

    // ---------- ENTRY POINT ----------

    public static void main(String[] args) {
        // Ensure UI is built on the Event Dispatch Thread.
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Fall back to default look and feel if system L&F is unavailable.
            }
            TicTacToe game = new TicTacToe();
            game.setVisible(true);
        });
    }
}
