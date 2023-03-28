import { Component } from "@angular/core";
import { Match } from "../../model/match";
import { ActivatedRoute, Router } from "@angular/router";
import { MatchService } from "../../services/MatchService";
import { Player } from "../../model/player";
import { PlayerService } from "../../services/PlayerService";
import { PlayerDto } from "../../model/playerDto";
import { MessageService } from "../../services/MessageService";

@Component({
  selector: "app-match",
  templateUrl: "./match.component.html",
  styleUrls: ["./match.component.scss"]
})
export class MatchComponent {
  match: Match | undefined;
  players: Player[] = [];

  inputPlayer: PlayerDto = {
    name: "",
    icon: "",
  };

  constructor(
    private route: ActivatedRoute,
    private matchService: MatchService,
    private router: Router,
    private playerService: PlayerService,
    private messageService: MessageService
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

  getPlayersAll()
    :
    void {
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
    if (this.match) {
      this.matchService.start(this.match.id)
        .subscribe(match => {
          this.router.navigate(["/playing-match/" + this.match!.id]);
        });
    }
  }
}
