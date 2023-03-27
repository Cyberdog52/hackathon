package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.common.JoinRequest;
import ch.zuehlke.fullstack.hackathon.model.Player;
import ch.zuehlke.fullstack.hackathon.service.MatchService;
import ch.zuehlke.fullstack.hackathon.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatchControllerTest {

    @Mock
    private PlayerService playerService;

    private MatchController matchController;

    @BeforeEach
    void setUp() {
        final var matchService = new MatchService();
        this.matchController = new MatchController(matchService, playerService);
    }

    @Test
    void itShouldCreateAndListMatches() {
        assertThat(this.matchController.getWaitingMatches()).asList().isEmpty();
        assertThat(this.matchController.createMatch()).isNotNull();
        assertThat(this.matchController.getWaitingMatches()).asList().hasSize(1);
        assertThat(this.matchController.createMatch()).isNotNull();
        assertThat(this.matchController.getWaitingMatches()).asList().hasSize(2);
    }

    @Test
    void itShouldNotStartMatches_whenTheyAreEmpty() {
        final var matchLobby = this.matchController.createMatch().getId();
        final var response = this.matchController.startMatch(matchLobby.toString());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
    }

    @Test
    void itShouldAllowPlayersToJoinMatches() {
        final var player0Id = UUID.randomUUID();
        when(this.playerService.find(player0Id))
                .thenReturn(Optional.of(new Player(player0Id, "Alice", "\uD83D\uDC80", Collections.emptyList())));
        final var matchId = this.matchController.createMatch().getId();
        final var response = this.matchController.join(matchId.toString(), new JoinRequest(player0Id.toString()));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }

    @Test
    void itShouldBlockPlayersToJoin_whenTheyTryTwice() {
        final var player0Id = UUID.randomUUID();
        when(this.playerService.find(player0Id))
                .thenReturn(Optional.of(new Player(player0Id, "Alice", "\uD83D\uDC80", Collections.emptyList())));
        final var matchId = this.matchController.createMatch().getId();
        final var response = this.matchController.join(matchId.toString(), new JoinRequest(player0Id.toString()));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        final var response1 = this.matchController.join(matchId.toString(), new JoinRequest(player0Id.toString()));
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
    }

    @Test
    void itShouldStartMatches_whenThereAreTwoPlayers() throws Exception {
        final var player0Id = UUID.randomUUID();
        final var player1Id = UUID.randomUUID();
        when(this.playerService.find(player0Id))
                .thenReturn(Optional.of(new Player(player0Id, "Alice", "\uD83D\uDC80", Collections.emptyList())));
        when(this.playerService.find(player1Id))
                .thenReturn(Optional.of(new Player(player1Id, "Bob", "\uD83D\uDD25", Collections.emptyList())));
        final var matchId = this.matchController.createMatch().getId();
        this.matchController.join(matchId.toString(), new JoinRequest(player0Id.toString()));
        this.matchController.join(matchId.toString(), new JoinRequest(player1Id.toString()));
        final var response = this.matchController.startMatch(matchId.toString());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(response.getBody().getId()).isEqualTo(matchId);
    }

}