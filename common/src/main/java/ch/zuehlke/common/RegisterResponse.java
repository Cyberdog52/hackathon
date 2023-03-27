package ch.zuehlke.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterResponse {

    private Player player;

    public RegisterResponse(Player player) {
        this.player = player;
    }

    public String uuid() {
        return player.getId();
    }
}
