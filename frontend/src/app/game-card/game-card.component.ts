import { Component, Input } from "@angular/core";
import { GameAction, GameDto, GameStatus, PlayerId } from "../../model/lobby";
import { LobbyService } from "../../services/lobby.service";
import { STATIC_HUMAN_PLAYER_ID } from "../../model/mocks";
import { Router } from "@angular/router";

@Component({
  selector: "app-game-card",
  templateUrl: "./game-card.component.html",
  styleUrls: ["./game-card.component.scss"]
})
export class GameCardComponent {

  @Input() game!: GameDto;

  MAX_PLAYERS = 2;
  MIN_PLAYERS = 2;
  GameStatus = GameStatus;
  GameAction = GameAction;

  constructor(private readonly lobbyService: LobbyService, private readonly router: Router) {
  }

  spectateGame(): void {
    this.router.navigateByUrl(`/spectator/${this.game.id}`)
  }

  deleteGame(): void {
    this.lobbyService.deleteGame(this.game.id).subscribe({
      next: () => {
        // Improve: Do something with the gameId
        console.log("Deleted game with id: ", this.game.id);
      },
      error: (error) => {
        // Improve: Do something with the error
        console.log("Something went wrong during deletion of game: ", error);
      }
    });
  }

  startGame(): void {
    if(this.isInLobby()) {
      this.router.navigateByUrl(`/play/${this.game.id}`)
    }

    this.lobbyService.startGame(this.game.id).subscribe({
      next: () => {
        // Improve: Do something with the gameId
        console.log("Started game with id: ", this.game.id);
      },
      error: (error) => {
        // Improve: Do something with the error
        console.log("Something went wrong during start of game: ", error);
      }
    });
  }

  getPlayerName(key: PlayerId): string | undefined {
    return this.game.players.find(player => player.id === key)?.name;
  }

  joinGame(): void {
    this.lobbyService.joinGame(this.game.id);
    this.game.players.push({ id: STATIC_HUMAN_PLAYER_ID, name: "ME" });
  }

  isInLobby(): boolean {
    return this.game.players.find((p) => p.id === STATIC_HUMAN_PLAYER_ID) !== undefined;
  }
}
