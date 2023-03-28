package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.common.*;
import ch.zuehlke.common.gameplay.CreateGameRequest;
import ch.zuehlke.common.gameplay.PlaceShipsRequest;
import ch.zuehlke.common.gameplay.ShootRequest;
import ch.zuehlke.fullstack.hackathon.service.GameService;
import ch.zuehlke.fullstack.hackathon.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class LobbyControllerTest {

    private LobbyController lobbyController;
    private GameService gameServiceMock;

    private NotificationService notificationServiceMock;

    @BeforeEach
    void setUp() {
        gameServiceMock = mock(GameService.class);
        notificationServiceMock = mock(NotificationService.class);
        lobbyController = new LobbyController(gameServiceMock, notificationServiceMock);
    }

    @Test
    void getGames_emptyList_successfully() {
        when(gameServiceMock.getGames()).thenReturn(List.of());

        ResponseEntity<List<GameDto>> response = lobbyController.getGames();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(response.getBody()).isEqualTo(List.of());
        verify(gameServiceMock, times(1)).getGames();
    }

    @Test
    void playCompleteGame_successfully() {
        // setup
        var gameService = new GameService();
        var controller = new LobbyController(gameService, notificationServiceMock);

        // register players
        var playerOne = registerPlayer(controller, "Player One");
        var playerTwo = registerPlayer(controller, "Player Two");
        assertThat(gameService.getRegisteredPlayers()).hasSize(2);

        // create game
        var createGameRequest = new CreateGameRequest();
        createGameRequest.setFirstPlayerId(playerOne.getId());
        createGameRequest.setSecondPlayerId(playerTwo.getId());

        var createGameResponse = controller.createGame(createGameRequest);
        assertThat(createGameResponse.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(createGameResponse.hasBody()).isTrue();
        var gameId = createGameResponse.getBody();
        assertThat(gameService.getGame(gameId).orElseThrow().getStatus()).isEqualTo(GameStatus.PLACE_SHIPS);

        // place ships
        var ship = new Ship(ShipType.AIRCRAFT_CARRIER, 0, 0, Orientation.HORIZONTAL);
        controller.placeShips(createPlaceShipsRequest(playerOne, gameId, ship));
        assertThat(gameService.getGame(gameId).orElseThrow().getStatus()).isEqualTo(GameStatus.PLACE_SHIPS);
        controller.placeShips(createPlaceShipsRequest(playerTwo, gameId, ship));
        assertThat(gameService.getGame(gameId).orElseThrow().getStatus()).isEqualTo(GameStatus.SHOOT);

        // shoot!
        ShootRequest shootRequestOfPlayerOne = createShootRequest(playerOne, gameId, ship);
        ShootRequest shootRequestOfPlayerTwo = createShootRequest(playerTwo, gameId, ship);
        // what happens, if player shoots twice?
        controller.shoot(shootRequestOfPlayerOne);
        controller.shoot(shootRequestOfPlayerTwo);
    }

    private static ShootRequest createShootRequest(Player playerOne, String gameId, Ship ship) {
        var shootRequest = new ShootRequest();
        shootRequest.setGameId(gameId);
        shootRequest.setPlayerId(playerOne.getId());
        shootRequest.setX(ship.getX());
        shootRequest.setY(ship.getY());
        return shootRequest;
    }

    private static PlaceShipsRequest createPlaceShipsRequest(Player playerOne, String gameId, Ship ship) {
        var placeShipsRequestOfPlayerOne = new PlaceShipsRequest();
        placeShipsRequestOfPlayerOne.setGameId(gameId);
        placeShipsRequestOfPlayerOne.setPlayer(playerOne);
        placeShipsRequestOfPlayerOne.setShips(List.of(ship));
        return placeShipsRequestOfPlayerOne;
    }

    private static Player registerPlayer(LobbyController controller, String playerName) {
        var playerOneRequest = new RegisterRequest(playerName);
        return controller.register(playerOneRequest).getBody().getPlayer();
    }
/*
    @Test
    void createGame_successfully() {
        GameId gameId = new GameId(42);
        when(gameServiceMock.createGame()).thenReturn(new Game(gameId));

        ResponseEntity<GameId> response = lobbyController.createGame();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(response.getBody()).isEqualTo(gameId);
        verify(gameServiceMock, times(1)).createGame();
    }


    @Test
    void join_successfully() {
        JoinResult joinResult = new JoinResult(new PlayerId(), JoinResultType.SUCCESS);
        when(gameServiceMock.join(anyInt(), any())).thenReturn(joinResult);

        PlayerName playerName = new PlayerName("name");
        JoinRequest joinRequest = new JoinRequest(playerName);
        ResponseEntity<JoinResponse> response = lobbyController.join(42, joinRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(response.getBody()).isEqualTo(new JoinResponse(joinResult.playerId()));
        verify(gameServiceMock, times(1)).join(42, playerName);
        verify(notificationServiceMock, times(1)).notifyGameUpdate(new GameId(42));
    }

    @Test
    void join_whenGameIsFull_returns400() {
        JoinResult joinResult = new JoinResult(null, JoinResultType.GAME_FULL);
        when(gameServiceMock.join(anyInt(), any())).thenReturn(joinResult);

        PlayerName playerName = new PlayerName("name");
        JoinRequest joinRequest = new JoinRequest(playerName);
        ResponseEntity<JoinResponse> response = lobbyController.join(42, joinRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
        assertThat(response.getBody()).isNull();
        verify(gameServiceMock, times(1)).join(42, playerName);
        verify(notificationServiceMock, never()).notifyGameUpdate(any());
    }

    @Test
    void join_whenGameIsNotFound_returns404() {
        JoinResult joinResult = new JoinResult(null, JoinResultType.GAME_NOT_FOUND);
        when(gameServiceMock.join(anyInt(), any())).thenReturn(joinResult);

        PlayerName playerName = new PlayerName("name");
        JoinRequest joinRequest = new JoinRequest(playerName);
        ResponseEntity<JoinResponse> response = lobbyController.join(42, joinRequest);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
        assertThat(response.getBody()).isNull();
        verify(gameServiceMock, times(1)).join(42, playerName);
        verify(notificationServiceMock, never()).notifyGameUpdate(any());
    }*/

/*    @Test
    void deleteGame_successfully() {
        when(gameServiceMock.deleteGame(anyInt())).thenReturn(true);

        ResponseEntity<Void> response = lobbyController.deleteGame(42);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(response.getBody()).isNull();
    }

    @Test
    void deleteGame_whenGameDidNotExist_returns404() {
        when(gameServiceMock.deleteGame(anyInt())).thenReturn(false);

        ResponseEntity<Void> response = lobbyController.deleteGame(666);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
        assertThat(response.getBody()).isNull();
    }

    @Test
    void startGame_successfully() {
        when(gameServiceMock.startGame(anyInt())).thenReturn(new StartResult(StartResultType.SUCCESS));

        ResponseEntity<Void> response = lobbyController.startGame(42);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(response.getBody()).isNull();
        verify(notificationServiceMock, times(1)).notifyGameUpdate(new GameId(42));
    }

    @Test
    void startGame_whenGameIsNotFound_returns404() {
        when(gameServiceMock.startGame(anyInt())).thenReturn(new StartResult(StartResultType.GAME_NOT_FOUND));

        ResponseEntity<Void> response = lobbyController.startGame(666);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
        assertThat(response.getBody()).isNull();
        verify(notificationServiceMock, never()).notifyGameUpdate(any());

    }

    @Test
    void startGame_whenGameHasNotEnoughPlayers_returns400() {
        when(gameServiceMock.startGame(anyInt())).thenReturn(new StartResult(StartResultType.NOT_ENOUGH_PLAYERS));

        ResponseEntity<Void> response = lobbyController.startGame(666);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
        assertThat(response.getBody()).isNull();
        verify(notificationServiceMock, never()).notifyGameUpdate(any());
    }

    @Test
    void play_successfully() {
        Move move = new Move(new PlayerId(), new RequestId(), GameAction.ROCK);
        GameId gameId = new GameId(42);
        when(gameServiceMock.play(eq(move), eq(gameId))).thenReturn(new PlayResult(PlayResultType.SUCCESS));

        ResponseEntity<Void> response = lobbyController.play(gameId.value(), move);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(response.getBody()).isNull();
        verify(notificationServiceMock, times(1)).notifyGameUpdate(gameId);
    }

    @Test
    void play_whenGameIsNotFound_returns404() {
        Move move = new Move(new PlayerId(), new RequestId(), GameAction.ROCK);
        GameId gameId = new GameId(42);
        when(gameServiceMock.play(eq(move), eq(gameId))).thenReturn(new PlayResult(PlayResultType.GAME_NOT_FOUND));

        ResponseEntity<Void> response = lobbyController.play(gameId.value(), move);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
        assertThat(response.getBody()).isNull();
        verify(notificationServiceMock, never()).notifyGameUpdate(any());
    }

    @Test
    void play_whenPlayerIsNotPartOfTheGame_returns400() {
        Move move = new Move(new PlayerId(), new RequestId(), GameAction.ROCK);
        GameId gameId = new GameId(42);
        when(gameServiceMock.play(eq(move), eq(gameId))).thenReturn(new PlayResult(PlayResultType.PLAYER_NOT_PART_OF_GAME));

        ResponseEntity<Void> response = lobbyController.play(gameId.value(), move);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
        assertThat(response.getBody()).isNull();
        verify(notificationServiceMock, never()).notifyGameUpdate(any());
    }

    @Test
    void play_whenActionIsInvalid_returns400() {
        Move move = new Move(new PlayerId(), new RequestId(), GameAction.ROCK);
        GameId gameId = new GameId(42);
        when(gameServiceMock.play(eq(move), eq(gameId))).thenReturn(new PlayResult(PlayResultType.INVALID_ACTION));

        ResponseEntity<Void> response = lobbyController.play(gameId.value(), move);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
        assertThat(response.getBody()).isNull();
        verify(notificationServiceMock, never()).notifyGameUpdate(any());
    }*/
}