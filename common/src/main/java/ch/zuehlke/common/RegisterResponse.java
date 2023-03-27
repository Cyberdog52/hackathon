package ch.zuehlke.common;

public class RegisterResponse {

    private final Player player;

    public RegisterResponse() {
        this(new Player(""));
    }

    public RegisterResponse(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public String uuid() {
        return player.getId();
    }
}
