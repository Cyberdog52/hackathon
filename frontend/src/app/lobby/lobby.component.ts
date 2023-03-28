import { Component, OnInit } from "@angular/core";
import { LobbyService } from "../../services/lobby.service";
import {delay, Observable, repeat, retry} from "rxjs";
import {GameDto, Player, TopPlayers} from "../../model/lobby";
import {MatDialog} from "@angular/material/dialog";
import {CreateGameDialogComponent} from "../create-game-dialog/create-game-dialog.component";

@Component({
  selector: "app-lobby",
  templateUrl: "./lobby.component.html",
  styleUrls: ["./lobby.component.scss"]
})
export class LobbyComponent implements OnInit {

  games$: Observable<GameDto[]> | undefined;

  playerNames$: Observable<Player[]> | undefined;
  topPlayers$: Observable<TopPlayers[]> | undefined;

  constructor(public dialog: MatDialog, private lobbyService: LobbyService) {
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

  openCreateGameDialog(players: Player[]): void {
    const dialogRef = this.dialog.open(CreateGameDialogComponent, {
      data: {players: players},
    });

    dialogRef.afterClosed().subscribe(result => {
      this.lobbyService.createGame({firstPlayerId: result.player1, secondPlayerId: result.player2}).subscribe(() => {
        console.log("Game created");
      });
    });
  }
}
