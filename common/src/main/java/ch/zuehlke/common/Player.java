package ch.zuehlke.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class Player {

    private String id;
    private String token;
    private String playerName;
    private Board board;

    public Player(String playerName) {
        this.board = new Board();
        id = UUID.randomUUID().toString();
        token = UUID.randomUUID().toString();
        this.playerName = playerName;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id='" + id + '\'' +
                ", token='" + token + '\'' +
                ", playerName='" + playerName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id) && Objects.equals(playerName, player.playerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, playerName);
    }
}
