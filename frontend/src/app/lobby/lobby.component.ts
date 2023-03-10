import { Component, OnInit } from "@angular/core";
import { LobbyService } from "../../services/lobby.service";
import { Observable } from "rxjs";
import { Game } from "../../model/lobby";

@Component({
  selector: "app-lobby",
  templateUrl: "./lobby.component.html",
  styleUrls: ["./lobby.component.scss"]
})
export class LobbyComponent implements OnInit {

  games$: Observable<Game[]> | undefined;

  constructor(private lobbyService: LobbyService) { }

  ngOnInit(): void {
    this.games$ = this.lobbyService.getGames();
  }

}
