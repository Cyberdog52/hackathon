package ch.zuehlke.common;

public class RegisterResponse {

    private final Player player;

    public RegisterResponse(Player player) {
        this.player = player;
    }

    public String uuid() {
        return player.id();
    }
}
