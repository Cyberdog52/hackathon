import { finalize, Observable, ReplaySubject } from "rxjs";
import { Injectable } from "@angular/core";
import { GameAction, GameId, PlayerId } from "../model/lobby";
import { Coordinate, PlayingEvent } from "../model/game/playing/events";
import { StompClient } from "./stomp-client";
import { HttpClient } from "@angular/common/http";
import { AttackTurnAction } from "../model/game/playing/actions";

@Injectable({
  providedIn: "root"
})
export class GamePlayService {
  private backendUrl = "api/game";

  constructor(private httpClient: HttpClient) {
  }

  attackPlayer(coordinate: Coordinate, playerId: PlayerId, gameId: GameId): Observable<void> {
    const url = `${this.backendUrl}/attack`;
    const attackTurnAction: AttackTurnAction = {
      playerId,
      coordinate,
      gameId
    }
    return this.httpClient.post<void>(url, attackTurnAction);
  }

}
