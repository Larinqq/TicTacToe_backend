package ganisrum.tictactoe.web.model;

import java.time.Instant;
import java.util.UUID;

public class GameDTO {
    private UUID id;
    private GameFieldDTO field;
    private GameStatus gameStatus;
    private UserDTO host;
    private UserDTO guest;
    private boolean vsComputer;
    private String hostMark;
    private String guestMark;
    private Instant createdAt;

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public UserDTO getGuest() {
        return guest;
    }

    public void setGuest(UserDTO guest) {
        this.guest = guest;
    }

    public boolean isVsComputer() {
        return vsComputer;
    }

    public void setVsComputer(boolean vsComputer) {
        this.vsComputer = vsComputer;
    }

    public UserDTO getHost() {
        return host;
    }

    public void setHost(UserDTO host) {
        this.host = host;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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


    public GameFieldDTO getField() {
        return field;
    }

    public void setField(GameFieldDTO field) {
        this.field = field;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
}