package ganisrum.tictactoe.web.model;

import jakarta.validation.constraints.NotNull;

public class GameFieldDTO {
    @NotNull
    private final int[][] field = new int[3][3];

    public int[][] getFieldCells() {
        return field.clone();
    }

    public void setFieldCells(int[][] field) {
        for (int i = 0; i < 3; i++) {
            System.arraycopy(field[i], 0, this.field[i], 0, 3);
        }
    }


}
