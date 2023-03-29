import {Component, OnInit} from "@angular/core";
import {SocketService} from "../services/socket.service";
import {MatchService} from "../services/MatchService";
import {ActivatedRoute} from "@angular/router";
import {Match} from "../model/match";
import {Player} from "../model/player";
import {Card} from "../model/card";

export interface Round {
  currentPlayer: Player;
  openCards: Card[];
  discardedCards: Card[];
  discardedCard: Card;
}


@Component({
  selector: "app-playing-match",
  templateUrl: "./playing-match.component.html",
  styleUrls: ["./playing-match.component.scss"]
})
export class PlayingMatchComponent implements OnInit {

  id: string | null = null;
  match: Match | undefined;
  currentRound: Round | undefined;

  constructor(
    private socketService: SocketService,
    private matchService: MatchService,

    private route: ActivatedRoute,
  ) {
  }
  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get("id");
    this.matchService.getMatch(id!)
      .subscribe(match => this.match = match);
    this.checkForRounds(id!)
    this.simulateRounds()
  }

  checkForRounds(id: string): void {
    this.socketService.subscribe("/topic/game/" + id + "/round", (message) => {
      this.currentRound = message.body as Round
    })
  }

  private simulateRounds() {
    setInterval(() => {

    }, 5000)
  }
}
