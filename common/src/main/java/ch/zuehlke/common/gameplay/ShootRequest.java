package ch.zuehlke.common.gameplay;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ShootRequest {

    private String gameId;
    private String playerId;
    private String playerToken;
    private int x;
    private int y;
}
