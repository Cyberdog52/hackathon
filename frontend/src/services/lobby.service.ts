import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Game, GameId } from "../model/lobby";

@Injectable({
  providedIn: "root"
})
export class LobbyService {

  private backendUrl = "api/lobby";

  constructor(private httpClient: HttpClient) {
  }

  public getGames(): Observable<Game[]>  {
    const url = `${this.backendUrl}/games`;
    return this.httpClient.get<Game[]>(url);
  }

  createGame(): Observable<GameId> {
    const url = `${this.backendUrl}/game`;
    return this.httpClient.post<GameId>(url, {});
  }

  deleteGame(gameId: GameId): Observable<void> {
    const url = `${this.backendUrl}/game/${gameId.id}`;
    return this.httpClient.delete<void>(url);
  }
}
