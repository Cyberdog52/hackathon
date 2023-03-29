import {Component, OnInit} from "@angular/core";
import {CreateGameResponse, LobbyService, PlayerListResponse} from "../../services/lobby.service";
import {delay, distinctUntilChanged, Observable, repeat, retry} from "rxjs";
import {GameDto, Player, TopPlayers} from "../../model/lobby";
import {MatDialog} from "@angular/material/dialog";
import {CreateGameDialogComponent} from "../create-game-dialog/create-game-dialog.component";
import {Router} from "@angular/router";

@Component({
  selector: "app-lobby",
  templateUrl: "./lobby.component.html",
  styleUrls: ["./lobby.component.scss"]
})
export class LobbyComponent implements OnInit {

  games$: Observable<GameDto[]> | undefined;

  playerNames$: Observable<PlayerListResponse[]> | undefined;
  topPlayers$: Observable<TopPlayers[]> | undefined;

  constructor(public dialog: MatDialog, private lobbyService: LobbyService, private router: Router) {
  }

  ngOnInit(): void {
    this.pollGamesRegularly();
    this.pollPlayersRegularly();
    this.pollTop10PlayersRegularly();
  }

  private pollGamesRegularly(): void {
    this.games$ = this.lobbyService.getGames()
      .pipe(
        repeat({count: Infinity, delay: 1000}), // repeat every second
        distinctUntilChanged(), // only emit when the value changes
      );
  }

  private pollPlayersRegularly(): void {
    this.playerNames$ = this.lobbyService.getPlayers().pipe(
      repeat({
        count: Infinity,
        delay: 1000
      }),
      distinctUntilChanged()
    ); // only emit when the value changes);
  }

  private pollTop10PlayersRegularly(): void {
    this.topPlayers$ = this.lobbyService.getTop10Players();
  }

  openCreateGameDialog(players: PlayerListResponse[]): void {
    const dialogRef = this.dialog.open(CreateGameDialogComponent, {
      data: {players: players},
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.lobbyService.createGame({
          firstPlayerId: result.data.player1,
          secondPlayerId: result.data.player2
        }).subscribe((response: CreateGameResponse) => {
          if (result.action === 'inspect') {
            this.router.navigate(['/game'], {queryParams: {gameId: response.gameId}});
          }
        });
      }
    });
  }
}
