package ch.zuehlke.common.gameplay;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreateGameRequest {

    private String firstPlayerId;
    private String secondPlayerId;

}
