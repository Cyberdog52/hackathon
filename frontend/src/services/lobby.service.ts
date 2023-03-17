import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { GameDto, GameId } from "../model/lobby";

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

  createGame(): Observable<GameId> {
    const url = `${this.backendUrl}/game`;
    return this.httpClient.post<GameId>(url, {});
  }

  deleteGame(gameId: GameId): Observable<void> {
    const url = `${this.backendUrl}/game/${gameId.value}`;
    return this.httpClient.delete<void>(url);
  }

  startGame(gameId: GameId): Observable<void> {
    const url = `${this.backendUrl}/game/${gameId.value}/start`;
    return this.httpClient.post<void>(url, {});
  }
}
