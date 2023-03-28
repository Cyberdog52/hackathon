import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import {GameDto, GameId, Player, TopPlayers} from "../model/lobby";

@Injectable({
  providedIn: "root"
})
export class LobbyService {

  private backendUrl = "api/lobby";

  constructor(private httpClient: HttpClient) {
  }

  public getGames(): Observable<GameDto[]> {
    const url = `${this.backendUrl}/games`;
    return this.httpClient.get<GameDto[]>(url);
  }

  createGame(createGameRequest: CreateGameRequest): Observable<GameId> {
    const url = `${this.backendUrl}/create`;
    return this.httpClient.post<GameId>(url, createGameRequest);
  }

  deleteGame(gameId: string): Observable<void> {
    const url = `${this.backendUrl}/game/${gameId}`;
    return this.httpClient.delete<void>(url);
  }

  startGame(gameId: string): Observable<void> {
    const url = `${this.backendUrl}/game/${gameId}/start`;
    return this.httpClient.post<void>(url, {});
  }

  getPlayers(): Observable<Player[]> {
    const url = `${this.backendUrl}/players`;
    return this.httpClient.get<Player[]>(url);
  }

  getTop10Players(): Observable<TopPlayers[]> {
    const url = `${this.backendUrl}/top10`;
    return this.httpClient.get<TopPlayers[]>(url);
  }
}

export interface CreateGameRequest {
  firstPlayerId: string;
  secondPlayerId: string;
}
