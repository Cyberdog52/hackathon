import { Component, OnInit } from "@angular/core";
import { LobbyService } from "../../services/lobby.service";
import { Observable, repeat, retry } from "rxjs";
import { GameDto } from "../../model/lobby";
import { STATIC_BOT_GAME_ID_PLAY } from "../../model/mocks/mock-data";
import { Router } from "@angular/router";
import { GameClient } from "../../services/game-client";

@Component({
  selector: "app-lobby",
  templateUrl: "./lobby.component.html",
  styleUrls: ["./lobby.component.scss"]
})
export class LobbyComponent implements OnInit {

  games$: Observable<GameDto[]> | undefined;

  constructor(
    private lobbyService: LobbyService,
    private gameClient: GameClient,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.pollGamesRegularly();
  }

  private pollGamesRegularly(): void {
    /*this.games$ = this.lobbyService.getGames()
      .pipe(
        repeat({ count: Infinity, delay: 1000 }), // repeat every second
        retry({ count: Infinity, delay: 1000 }), // when it fails, retry forever
      );*/
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

  public joinLobby(): void {
    this.lobbyService.joinGame(STATIC_BOT_GAME_ID_PLAY).subscribe((result) => {
      this.gameClient.setCurrentPlayerId(result.playerId);
      this.router.navigateByUrl("/play/" + result.gameId)
    });
  }
}
