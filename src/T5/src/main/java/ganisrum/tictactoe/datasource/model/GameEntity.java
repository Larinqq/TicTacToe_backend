package ganisrum.tictactoe.datasource.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;


@Entity
@Table(name = "games")
public class GameEntity {

    @Id
    private UUID id;

    @Column(columnDefinition = "TEXT")
    private String field;

    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id", referencedColumnName = "id")
    private UserEntity host;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id", referencedColumnName = "id")
    private UserEntity guest;

    @Column(name = "vs_computer")
    private boolean vsComputer;

    @Column(name = "host_mark")
    private String hostMark;

    @Column(name = "guest_mark")
    private String guestMark;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public GameEntity() {

    }

    public static GameEntity createNew() {
        GameEntity entity = new GameEntity();
        entity.id = UUID.randomUUID();
        entity.field = "000000000";
        entity.host = null;
        entity.guest = null;
        entity.gameStatus = GameStatus.HOST_TURN;
        entity.vsComputer = true;
        entity.hostMark = null;
        entity.guestMark = null;
        return entity;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdDate) {
        this.createdAt = createdDate;
    }

    public String getHostMark() {
        return hostMark;
    }

    public void setHostMark(String hostMark) {
        this.hostMark = hostMark;
    }

    public String getGuestMark() {
        return guestMark;
    }

    public void setGuestMark(String guestMark) {
        this.guestMark = guestMark;
    }

    public boolean isVsComputer() {
        return vsComputer;
    }

    public void setVsComputer(boolean vsComputer) {
        this.vsComputer = vsComputer;
    }

    public UserEntity getHost() {
        return host;
    }

    public void setHost(UserEntity host) {
        this.host = host;
    }

    public UserEntity getGuest() {
        return guest;
    }

    public void setGuest(UserEntity guest) {
        this.guest = guest;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
