import { Observable } from "rxjs";
import { Injectable } from "@angular/core";
import { GameId, PlayerId } from "../model/lobby";
import { Coordinate } from "../model/game/playing/events";
import { HttpClient } from "@angular/common/http";
import { AttackTurnAction, BoatDirection, BoatType, PlaceBoatAction } from "../model/game/playing/actions";
import { httpClientMock } from "../model/mocks/httpclient-mock";
import { environment } from "../environments/environment";

@Injectable({
  providedIn: "root"
})
export class GamePlayService {
  private backendUrl = "api";

  constructor(private httpClient: HttpClient) {
    if (environment.mock) {
      this.httpClient = httpClientMock;
    }
  }

  attackPlayer(coordinate: Coordinate, playerId: PlayerId, gameId: GameId): Observable<void> {
    const url = `${this.backendUrl}/playing/attack`;

    return this.httpClient.post<void>(url, {
      playerId,
      coordinate,
      gameId
    } as AttackTurnAction);
  }


  placeBoat(coordinate: Coordinate, playerId: PlayerId, gameId: GameId, boatType: BoatType, boatDirection: BoatDirection): Observable<void> {
    const url = `${this.backendUrl}/setup/place-boat`;
    return this.httpClient.post<void>(url, {
      playerId,
      coordinate,
      gameId,
      boatType,
      boatDirection
    } as PlaceBoatAction);
  }
}
