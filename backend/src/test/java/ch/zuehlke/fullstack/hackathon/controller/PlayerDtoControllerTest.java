package ch.zuehlke.fullstack.hackathon.controller;

import ch.zuehlke.common.PlayerCreateDto;
import ch.zuehlke.fullstack.hackathon.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PlayerDtoControllerTest {

    private PlayerController playerController;

    @BeforeEach
    void setUp() {
        final var playerService = new PlayerService();
        this.playerController = new PlayerController(playerService);
    }

    @Test
    void itShouldCreateNewUsers_whenNameDoesNotExits() {
        final var response = this.playerController.createPlayer(new PlayerCreateDto("Bob", "\uD83E\uDD78"));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void itShouldNotCreateNewUsers_wheNameAlreadyExists() {
        this.playerController.createPlayer(new PlayerCreateDto("Bob", "\uD83E\uDD78"));
        final var response = this.playerController.createPlayer(new PlayerCreateDto("Bob", "\uD83E\uDEE4"));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
    }

    @Test
    void itShouldReturnCreatedUsers_whenAllAreRequested() {
        this.playerController.createPlayer(new PlayerCreateDto("Alice", "\uD83E\uDD78"));
        this.playerController.createPlayer(new PlayerCreateDto("Bob", "\uD83E\uDEE4"));
        final var allPlayers = this.playerController.getAllPlayers().stream().toList();
        assertThat(allPlayers).asList()
                .isNotEmpty()
                .hasSize(2)
                .extracting("name")
                .containsAll(List.of("Alice", "Bob"));
    }

    @Test
    void itShouldDeleteAPlayer_whenItExists() {
        final var playerId = this.playerController.createPlayer(new PlayerCreateDto("Alice", "\uD83E\uDD78"))
                .getBody()
                .toString();
        this.playerController.createPlayer(new PlayerCreateDto("Bob", "\uD83E\uDEE4"));
        this.playerController.deletePlayer(playerId);
        final var response = this.playerController.getAllPlayers().stream().toList();
        assertThat(response).asList()
                .isNotEmpty()
                .hasSize(1)
                .extracting("name")
                .containsExactly("Bob");
    }
}