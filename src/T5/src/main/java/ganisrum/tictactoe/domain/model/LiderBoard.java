package ganisrum.tictactoe.domain.model;

import java.util.UUID;

public class LiderBoard {
    private UUID userid;
    private double winRate;

    public LiderBoard(UUID userid, double winRate) {
        this.userid = userid;
        this.winRate = winRate;
    }

    public UUID getUserid() {
        return userid;
    }

    public void setUserid(UUID userid) {
        this.userid = userid;
    }


    public double getWinRate() {
        return winRate;
    }

    public void setWinRate(double winRate) {
        if (winRate < 0) throw new IllegalArgumentException("win rate cannot be less than 0");
        this.winRate = winRate;
    }
}
