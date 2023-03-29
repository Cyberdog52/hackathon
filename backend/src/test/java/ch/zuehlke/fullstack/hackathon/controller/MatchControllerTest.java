package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.common.JoinRequest;
import ch.zuehlke.common.Player;
import ch.zuehlke.fullstack.hackathon.service.GameService;
import ch.zuehlke.fullstack.hackathon.service.MatchService;
import ch.zuehlke.fullstack.hackathon.service.NotificationService;
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
class MatchControllerTest {

    @Mock
    private PlayerService playerService;

    @Mock
    private GameService gameService;

    @Mock
    private NotificationService notificationService;

    private LobbyController lobbyController;

    private MatchController matchController;

    @BeforeEach
    void setUp() {
        final var matchService = new MatchService(gameService);
        this.lobbyController = new LobbyController(matchService, playerService, notificationService);
        this.matchController = new MatchController(matchService);
    }

    @Test
    void itShouldNotStartMatches_whenTheyAreEmpty() {
        final var matchId = this.lobbyController.createMatch();
        final var response = this.matchController.startMatch(matchId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
    }

    @Test
    void itShouldStartMatches_whenThereAreTwoPlayers() {
        final var player0Id = UUID.randomUUID();
        final var player1Id = UUID.randomUUID();
        when(this.playerService.find(player0Id))
                .thenReturn(Optional.of(new Player(player0Id, "Alice", "\uD83D\uDC80")));
        when(this.playerService.find(player1Id))
                .thenReturn(Optional.of(new Player(player1Id, "Bob", "\uD83D\uDD25")));
        final var matchId = this.lobbyController.createMatch();
        this.lobbyController.join(matchId, new JoinRequest(player0Id.toString()));
        this.lobbyController.join(matchId, new JoinRequest(player1Id.toString()));
        final var response = this.matchController.startMatch(matchId);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(response.getBody().getId()).hasToString(matchId);
    }

}