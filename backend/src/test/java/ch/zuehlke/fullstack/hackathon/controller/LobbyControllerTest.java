package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.common.JoinRequest;
import ch.zuehlke.fullstack.hackathon.model.Player;
import ch.zuehlke.fullstack.hackathon.service.GameService;
import ch.zuehlke.fullstack.hackathon.service.MatchService;
import ch.zuehlke.fullstack.hackathon.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LobbyControllerTest {

    @Mock
    private PlayerService playerService;

    @Mock
    private GameService gameService;

    private LobbyController lobbyController;

    @BeforeEach
    void setUp() {
        final var matchService = new MatchService(gameService);
        this.lobbyController = new LobbyController(matchService, playerService);
    }

    @Test
    void itShouldCreateAndListMatches() {
        assertThat(this.lobbyController.getWaitingMatches()).asList().isEmpty();
        assertThat(this.lobbyController.createMatch()).isNotNull();
        assertThat(this.lobbyController.getWaitingMatches()).asList().hasSize(1);
        assertThat(this.lobbyController.createMatch()).isNotNull();
        assertThat(this.lobbyController.getWaitingMatches()).asList().hasSize(2);
    }

    @Test
    void itShouldAllowPlayersToJoinMatches() {
        final var player0Id = UUID.randomUUID();
        when(this.playerService.find(player0Id))
                .thenReturn(Optional.of(new Player(player0Id, "Alice", "\uD83D\uDC80")));
        final var matchId = this.lobbyController.createMatch();
        final var response = this.lobbyController.join(matchId, new JoinRequest(player0Id.toString()));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }

    @Test
    void itShouldBlockPlayersToJoin_whenTheyTryTwice() {
        final var player0Id = UUID.randomUUID();
        when(this.playerService.find(player0Id))
                .thenReturn(Optional.of(new Player(player0Id, "Alice", "\uD83D\uDC80")));
        final var matchId = this.lobbyController.createMatch();
        final var response = this.lobbyController.join(matchId, new JoinRequest(player0Id.toString()));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        final var response1 = this.lobbyController.join(matchId, new JoinRequest(player0Id.toString()));
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
    }

}