import { Component, Input } from "@angular/core";
import { GameAction, GameDto, GameStatus, PlayerId } from "../../model/lobby";
import { LobbyService } from "../../services/lobby.service";

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

  constructor(private lobbyService: LobbyService) {
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
    return this.game.players.find(player => player.id.value === key.value)?.name.value;
  }
}
