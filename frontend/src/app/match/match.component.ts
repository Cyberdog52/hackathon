import {Component, OnDestroy, OnInit} from "@angular/core";
import { Match } from "../model/match";
import { ActivatedRoute, Router } from "@angular/router";
import { MatchService } from "../services/MatchService";
import { Player } from "../model/player";
import { PlayerService } from "../services/PlayerService";
import { PlayerDto } from "../model/playerDto";
import { MessageService } from "../services/MessageService";
import { SocketService } from "../services/socket.service";

@Component({
  selector: "app-match",
  templateUrl: "./match.component.html",
  styleUrls: ["./match.component.scss"]
})
export class MatchComponent implements OnInit, OnDestroy {
  match: Match | undefined;
  players: Array<Player> = new Array<Player>();

  inputPlayer: PlayerDto = {
    name: "",
    icon: "",
  };

  constructor(
    private route: ActivatedRoute,
    private matchService: MatchService,
    private router: Router,
    private playerService: PlayerService,
    private messageService: MessageService,
    private socketService: SocketService
  ) {
  }

  ngOnInit()
    :
    void {
    const id = this.route.snapshot.paramMap.get("id");
    if (id) {
      this.getMatch(id);
    } else {
      this.router.navigate(["/dashboard"]);
    }
    this.getPlayersAll();
    this.checkForJoiningPlayers();
  }

  getMatch(id
             :
             string
  ):
    void {
    this.matchService.getMatch(id)
      .subscribe(match => this.match = match);
  }

  getPlayersOfMatch()
    :
    Player[] {
    return this.match?.players || [];
  }

  getPlayersAll(): void {
    this.playerService.getPlayers()
      .subscribe(players => this.players = players);
  }

  addPlayer(player_dto
              :
              PlayerDto
  ):
    void {
    this.playerService.createPlayer(player_dto)
      .subscribe(player_id => {
        console.log("player_id: " + player_id);
      });
    this.getPlayersAll();
  }

  joinPlayer(player_id: string): void {
    if (this.match) {
      this.matchService.join({ playerId: player_id }, this.match.id)
        .subscribe(match => {
          this.match = match;
        });
    }
  }

  startMatch(): void {
    /*if (this.match) {
      this.matchService.start(this.match.id)
        .subscribe(match => {
          this.router.navigate(["/playing-match/" + this.match!.id]);
        });
      this.router.navigate(["/playing-match/" + this.match!.id]);
    }*/
    this.router.navigate(["/playing-match/" + this.match!.id]);
  }

  checkForJoiningPlayers(): void {
    this.socketService.subscribe("/topic/game/*", (message) => {
      this.getPlayersAll()
      this.match =  {
        id: this.route.snapshot.paramMap.get("id"),
        players: this.players,
        full: false
      } as Match
      console.log("body: " + message.body);
    });
  }

  ngOnDestroy(): void {
    this.socketService.unsubscribe("/topic/game/*");
  }
}
