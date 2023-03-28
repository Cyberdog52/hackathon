import { Component, OnInit } from "@angular/core";
import { LobbyService } from "../../services/lobby.service";
import {delay, Observable, repeat, retry} from "rxjs";
import {GameDto, Player, TopPlayers} from "../../model/lobby";

@Component({
  selector: "app-lobby",
  templateUrl: "./lobby.component.html",
  styleUrls: ["./lobby.component.scss"]
})
export class LobbyComponent implements OnInit {

  games$: Observable<GameDto[]> | undefined;

  playerNames$: Observable<Player[]> | undefined;
  topPlayers$: Observable<TopPlayers[]> | undefined;

  constructor(private lobbyService: LobbyService) {
  }

  ngOnInit(): void {
    this.pollGamesRegularly();
    this.pollPlayersRegularly();
    this.pollTop10PlayersRegularly();
  }

  private pollGamesRegularly(): void {
    this.games$ = this.lobbyService.getGames()
      .pipe(
        repeat({ count: Infinity, delay: 1000 }), // repeat every second
        retry({ count: Infinity, delay: 1000 }), // when it fails, retry forever
      );
  }

  private pollPlayersRegularly(): void {
    this.playerNames$ = this.lobbyService.getPlayers().pipe(repeat({ count: Infinity, delay: 1000 }));
  }

  private pollTop10PlayersRegularly(): void {
    this.topPlayers$ = this.lobbyService.getTop10Players().pipe(repeat({ count: Infinity, delay: 1000 }));
    console.log(this.topPlayers$)
  }

  createGame(): void {
    this.lobbyService.createGame().subscribe({
      next: (gameId) => {
        // Improve: Do something with the gameId
        console.log("Created new game with id: ", gameId);
      },
      error: (error) => {
        // Improve: Do something with the error
        console.log("Something went wrong during creation of new game: ", error);
      }
    });
  }
}
