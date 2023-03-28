package ch.zuehlke.common.gameplay;

import ch.zuehlke.common.Player;
import ch.zuehlke.common.Ship;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PlaceShipsRequest {

    private String gameId;
    private Player player;
    private List<Ship> ships;

}