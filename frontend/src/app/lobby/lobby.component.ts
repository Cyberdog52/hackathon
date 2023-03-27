import { Component, OnInit } from '@angular/core';
import { LobbyService } from '../../services/lobby.service';
import { Observable, repeat, retry } from 'rxjs';
import { GameDto } from '../../model/lobby';

@Component({
  selector: 'app-lobby',
  templateUrl: './lobby.component.html',
  styleUrls: ['./lobby.component.scss'],
})
export class LobbyComponent implements OnInit {
  games$: Observable<GameDto[]> | undefined;

  constructor(private lobbyService: LobbyService) {}

  ngOnInit(): void {
    this.pollGamesRegularly();
  }

  private pollGamesRegularly(): void {
    this.games$ = this.lobbyService.getGames().pipe(
      repeat({ count: Infinity, delay: 1000 }), // repeat every second
      retry({ count: Infinity, delay: 1000 }) // when it fails, retry forever
    );
  }

  createGame(): void {
    this.lobbyService.createGame().subscribe({
      next: (gameId) => {
        // Improve: Do something with the gameId
        console.log('Created new game with id: ', gameId);
      },
      error: (error) => {
        // Improve: Do something with the error
        console.log(
          'Something went wrong during creation of new game: ',
          error
        );
      },
    });
  }
}
