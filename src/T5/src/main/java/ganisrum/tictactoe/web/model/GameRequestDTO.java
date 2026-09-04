package ganisrum.tictactoe.web.model;

import jakarta.validation.constraints.NotNull;

public class GameRequestDTO {
    @NotNull(message = "Set game type! vsComputer true/false")
    private Boolean vsComputer;
    @NotNull(message = "Set your mark! HostMarkX true/false")
    private Boolean HostMarkX;

    public boolean isHostMarkX() {
        return this.HostMarkX;
    }

    public void setHostMarkX(boolean hostMarkX) {
        this.HostMarkX = hostMarkX;
    }


    public boolean isVsComputer() {
        return this.vsComputer;
    }

    public void setVsComputer(boolean vsComputer) {
        this.vsComputer = vsComputer;
    }
}

