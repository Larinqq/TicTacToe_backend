package ganisrum.tictactoe.domain.model;

import java.time.Instant;
import java.util.UUID;

public class Game {
    private UUID id;
    private GameField field;
    private GameStatus gameStatus;
    private User host;
    private User guest;
    private boolean vsComputer;
    private String hostMark;
    private String guestMark;
    private Instant createdAt;


    public Game() {
        this.id = UUID.randomUUID();
        this.host = null;
        this.guest = null;
        this.vsComputer = true;
        this.field = new GameField();
        this.gameStatus = GameStatus.HOST_TURN;
        this.hostMark = "X (1)";
        this.guestMark = "0 (2)";
        this.createdAt = Instant.now();

    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void switchMarks() {
        this.hostMark = "0 (1)";
        this.guestMark = "X (2)";
    }

    public String getGuestMark() {
        return guestMark;
    }

    public void setGuestMark(String guestMark) {
        this.guestMark = guestMark;
    }

    public String getHostMark() {
        return hostMark;
    }

    public void setHostMark(String hostMark) {
        this.hostMark = hostMark;
    }

    public User getGuest() {
        if (guest == null) {
            return null;
        }
        return guest;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public boolean isVsComputer() {
        return vsComputer;
    }

    public void setVsComputer(boolean vsComputer) {
        this.vsComputer = vsComputer;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        this.id = id;
    }

    public GameField getField() {
        return field;
    }

    public void setField(GameField field) {
        if (field == null) {
            throw new IllegalArgumentException("Field cannot be null");
        }
        this.field = field;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus status) {
        this.gameStatus = status;
    }
}
