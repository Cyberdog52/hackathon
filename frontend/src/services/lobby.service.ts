import { EMPTY, Observable, of } from "rxjs";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { GameDto, GameId, GameStatus } from "../model/lobby";
import { environment } from "../environments/environment";
import {
  STATIC_GAME_ID_PLAY,
  STATIC_GAME_ID_SPECTATE,
  STATIC_HUMAN_PLAYER_ID,
  STATIC_OPPONENT_PLAYER_ID,
  STATIC_OPPONENT_PLAYER_ID2
} from "../model/mocks/mock-data";
import { PlayerJoinedEvent } from "../model/game/playing/events";
import { PlayerJoinAction } from "../model/game/playing/actions";

@Injectable({
  providedIn: "root"
})
export class LobbyService {

  private backendUrl = "api/lobby";

  constructor(private httpClient: HttpClient) {
  }

  public getGames(): Observable<GameDto[]> {
    if (environment.mock || environment.mockGames) {
      return of([
        {
          id: STATIC_GAME_ID_PLAY,
          players: [{
            id: STATIC_OPPONENT_PLAYER_ID,
            name: "Walter Sobchak"
          }],
          status: GameStatus.NOT_STARTED,
          state: null
        },
        {
          id: STATIC_GAME_ID_SPECTATE,
          players: [
            {
              id: STATIC_OPPONENT_PLAYER_ID,
              name: "Jeffrey Lebowsky"
            },
            {
              id: STATIC_OPPONENT_PLAYER_ID2,
              name: "Jesus Quintana"
            }
          ],
          status: GameStatus.IN_PROGRESS,
          state: null
        },

      ]);
    }
    const url = `${this.backendUrl}/games`;
    return this.httpClient.get<GameDto[]>(url);
  }

  createGame(): Observable<GameId> {
    const url = `${this.backendUrl}/game`;
    return this.httpClient.post<GameId>(url, {});
  }

  deleteGame(gameId: GameId): Observable<void> {
    const url = `${this.backendUrl}/game/${gameId}`;
    return this.httpClient.delete<void>(url);
  }

  startGame(gameId: GameId): Observable<void> {
    const url = `${this.backendUrl}/game/${gameId}/start`;
    return this.httpClient.post<void>(url, {});
  }

  joinGame(gameId: GameId): Observable<PlayerJoinedEvent> {
    if (environment.mock) {
      return EMPTY;
    }
    const url = `${this.backendUrl}/join`;
    return this.httpClient.post<PlayerJoinedEvent>(url, {
      playerId: STATIC_HUMAN_PLAYER_ID,
      gameId
    } as PlayerJoinAction);
  }
}
