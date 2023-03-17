package ch.zuehlke.fullstack.hackathon.model;

import ch.zuehlke.common.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {

    @Test
    void addPlayer_successfully() {
        Player player = new Player(new PlayerId(), new PlayerName("name"));
        Game game = new Game(new GameId(1));

        game.addPlayer(player);

        assertThat(game.getPlayers()).hasSize(1);
        assertThat(game.getPlayers().get(0)).isEqualTo(player);
    }

    @Test
    void startGame_withTwoPlayers_successfully() {
        Game game = new Game(new GameId(1));
        PlayerId playerId1 = new PlayerId();
        game.addPlayer(new Player(playerId1, new PlayerName("name1")));
        PlayerId playerId2 = new PlayerId();
        game.addPlayer(new Player(playerId2, new PlayerName("name2")));

        game.startGame();

        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
        assertThat(game.getState().currentRequests()).hasSize(2);
        assertThat(game.getState().currentRequests().stream().anyMatch(request -> request.playerId().equals(playerId1))).isTrue();
        assertThat(game.getState().currentRequests().stream().anyMatch(request -> request.playerId().equals(playerId2))).isTrue();
    }

    @Test
    void finishGame_successfully() {
        Game game = new Game(new GameId(1));

        game.finishGame();

        assertEquals(GameStatus.FINISHED, game.getStatus());
    }

    @Test
    void deleteGame_successfully() {
        Game game = new Game(new GameId(1));

        game.deleteGame();

        assertEquals(GameStatus.DELETED, game.getStatus());
    }

    @Test
    void canStartGame_withZeroPlayers_false() {
        Game game = new Game(new GameId(1));

        boolean canStart = game.canStartGame();

        assertThat(canStart).isFalse();
    }

    @Test
    void canStartGame_withOnePlayer_false() {
        Game game = new Game(new GameId(1));
        game.addPlayer(new Player(new PlayerId(), new PlayerName("name")));

        boolean canStart = game.canStartGame();

        assertThat(canStart).isFalse();
    }

    @Test
    void canStartGame_withTwoPlayers_true() {
        Game game = new Game(new GameId(1));
        game.addPlayer(new Player(new PlayerId(), new PlayerName("name1")));
        game.addPlayer(new Player(new PlayerId(), new PlayerName("name2")));

        boolean canStart = game.canStartGame();

        assertThat(canStart).isTrue();
    }
}