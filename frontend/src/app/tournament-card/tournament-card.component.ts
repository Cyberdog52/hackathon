import { Component, Input, SimpleChanges } from '@angular/core';
import { TournamentDto, TournamentStatus, PlayerId } from '../../model/lobby';
import { LobbyService } from '../../services/lobby.service';

@Component({
  selector: 'app-tournament-card',
  templateUrl: './tournament-card.component.html',
  styleUrls: ['./tournament-card.component.scss'],
})
export class TournamentCardComponent {
  @Input() tournament!: TournamentDto;

  MIN_PLAYERS = 2;
  MAX_PLAYERS = 20;
  TournamentStatus = TournamentStatus;

  constructor(private lobbyService: LobbyService) {}

  ngOnChanges(changes: SimpleChanges) {
    if (changes.tournament && changes.tournament.currentValue) {
      this.tournament.players.sort(
        (a, b) => this.getScore(b.id) - this.getScore(a.id)
      );
    }
  }

  deleteTournament(): void {
    this.lobbyService.deleteTournament(this.tournament.id).subscribe({
      next: () => {
        // Improve: Do something with the gameId
        console.log('Deleted tournament with id: ', this.tournament.id);
      },
      error: (error) => {
        // Improve: Do something with the error
        console.log(
          'Something went wrong during deletion of tournament: ',
          error
        );
      },
    });
  }

  startTournament(): void {
    this.lobbyService.startTournament(this.tournament.id).subscribe({
      next: () => {
        // Improve: Do something with the gameId
        console.log('Started tournament with id: ', this.tournament.id);
      },
      error: (error) => {
        // Improve: Do something with the error
        console.log('Something went wrong during start of tournament: ', error);
      },
    });
  }

  getPlayerName(key: PlayerId): string | undefined {
    return this.tournament.players.find(
      (player) => player.id.value === key.value
    )?.name.value;
  }

  getScore(key: PlayerId): number {
    return (
      this.tournament.scores.find((score) => score.playerId.value === key.value)
        ?.score ?? 0
    );
  }
}
