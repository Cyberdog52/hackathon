import { Component, Input } from "@angular/core";
import { Game } from "../../model/lobby";
import { LobbyService } from "../../services/lobby.service";

@Component({
  selector: "app-game-card",
  templateUrl: "./game-card.component.html",
  styleUrls: ["./game-card.component.scss"]
})
export class GameCardComponent {

  @Input() game!: Game;


  constructor(private lobbyService: LobbyService) {
  }

  deleteGame(): void {
    this.lobbyService.deleteGame(this.game.gameId).subscribe({
      next: () => {
        // Improve: Do something with the gameId
        console.log("Deleted game with id: ", this.game.gameId);
      },
      error: (error) => {
        // Improve: Do something with the error
        console.log("Something went wrong during deletion of game: ", error);
      }
    });
  }
}
