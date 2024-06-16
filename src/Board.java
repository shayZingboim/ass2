/**
 * Represents the dimensions of a game board.
 */
public class Board {
    private static int height = 600; // Default height
    private static int width = 800; // Default width

    /**
     * Returns the height of the board.
     *
     * @return The height of the board in pixels.
     */
    public static int getHeightBoard() {
        return height;
    }

    /**
     * Returns the width of the board.
     *
     * @return The width of the board in pixels.
     */
    public static int getWidthBoard() {
        return width;
    }

    /**
     * Sets the height of the board.
     *
     * @param height The new height of the board in pixels.
     */
    public static void setHeightBoard(int height) {
        Board.height = height;
    }

    /**
     * Sets the width of the board.
     *
     * @param width The new width of the board in pixels.
     */
    public static void setWidthBoard(int width) {
        Board.width = width;
    }
}