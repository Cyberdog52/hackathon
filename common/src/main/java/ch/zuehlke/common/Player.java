package ch.zuehlke.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Player {

    private String id;
    private String token;
    private String playerName;

    public Player(String playerName) {
        id = UUID.randomUUID().toString();
        token = UUID.randomUUID().toString();
        this.playerName = playerName;
    }

}
