import { Observable } from "rxjs";
import { Injectable } from "@angular/core";
import { GameId, PlayerId } from "../model/lobby";
import { Coordinate } from "../model/game/playing/events";
import { HttpClient } from "@angular/common/http";
import { AttackTurnAction, PlaceBoatAction } from "../model/game/playing/actions";
import { httpClientMock } from "../model/mocks/httpclient-mock";
import { environment } from "../environments/environment";

@Injectable({
  providedIn: "root"
})
export class GamePlayService {
  private backendUrl = "api/game";

  constructor(private httpClient: HttpClient) {
    if (environment.mock) {
      this.httpClient = httpClientMock;
    }
  }

  attackPlayer(coordinate: Coordinate, playerId: PlayerId, gameId: GameId): Observable<void> {
    const url = `${this.backendUrl}/attack`;

    return this.httpClient.post<void>(url, {
      playerId,
      coordinate,
      gameId
    } as AttackTurnAction);
  }


  placeBoat(coordinate: Coordinate, playerId: PlayerId, gameId: GameId): Observable<void> {
    const url = `${this.backendUrl}/placeBoat`;
    return this.httpClient.post<void>(url, {
      playerId,
      coordinate,
      gameId
    } as PlaceBoatAction);
  }
}
