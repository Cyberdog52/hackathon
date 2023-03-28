import { Component, Input } from "@angular/core";
import { GameAction, TournamentDto, TournamentStatus, PlayerId } from "../../model/lobby";
import { LobbyService } from "../../services/lobby.service";

@Component({
  selector: "app-tournament-card",
  templateUrl: "./tournament-card.component.html",
  styleUrls: ["./tournament-card.component.scss"],
})
export class TournamentCardComponent {
  @Input() tournament!: TournamentDto;

  MIN_PLAYERS = 2;
  MAX_PLAYERS = 20;
  TournamentStatus = TournamentStatus;

  constructor(private lobbyService: LobbyService) {}

  deleteTournament(): void {
    this.lobbyService.deleteTournament(this.tournament.id).subscribe({
      next: () => {
        // Improve: Do something with the gameId
        console.log("Deleted tournament with id: ", this.tournament.id);
      },
      error: (error) => {
        // Improve: Do something with the error
        console.log("Something went wrong during deletion of tournament: ", error);
      },
    });
  }

  startTournament(): void {
    this.lobbyService.startTournament(this.tournament.id).subscribe({
      next: () => {
        // Improve: Do something with the gameId
        console.log("Started tournament with id: ", this.tournament.id);
      },
      error: (error) => {
        // Improve: Do something with the error
        console.log("Something went wrong during start of tournament: ", error);
      },
    });
  }

  getPlayerName(key: PlayerId): string | undefined {
    return this.tournament.players.find((player) => player.id.value === key.value)
      ?.name.value;
  }
}
