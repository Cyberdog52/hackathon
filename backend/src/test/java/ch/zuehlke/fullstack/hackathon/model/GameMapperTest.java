//package ch.zuehlke.fullstack.hackathon.model;
//
//import ch.zuehlke.common.GameDto;
//import ch.zuehlke.common.GameId;
//import ch.zuehlke.common.GameState;
//import ch.zuehlke.common.GameStatus;
//import org.junit.jupiter.api.Test;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class GameMapperTest {
//
//    @Test
//    void map_newGame_successfully() {
//        GameId gameId = new GameId(1);
//        Match game = new Match(gameId);
//
//        GameDto gameDto = GameMapper.map(game);
//
//        assertThat(gameDto.id()).isEqualTo(gameId);
//        assertThat(gameDto.players()).isEqualTo(game.getPlayers());
//        assertEquals(GameStatus.NOT_STARTED, gameDto.status());
//        assertEquals(new GameState(), gameDto.state());
//    }
//
//}