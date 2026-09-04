package ganisrum.tictactoe.domain.model;

public class GameField {
    private final int[][] field;

    public GameField() {
        field = new int[3][3];
    }

    public int getCell(int row, int col) {
        return field[row][col];
    }

    public void setCell(int row, int col, int value) {
        this.field[row][col] = value;
    }
}
