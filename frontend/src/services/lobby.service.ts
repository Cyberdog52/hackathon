import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { GameDto, GameId, TournamentDto, TournamentId } from "../model/lobby";

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

  public getTournaments(): Observable<TournamentDto[]> {
    const url = `${this.backendUrl}/tournaments`;
    return this.httpClient.get<TournamentDto[]>(url);
  }

  createGame(): Observable<GameId> {
    const url = `${this.backendUrl}/game`;
    return this.httpClient.post<GameId>(url, {});
  }

  createTournament(): Observable<TournamentId> {
    const url = `${this.backendUrl}/tournament`;
    return this.httpClient.post<TournamentId>(url, {});
  }

  deleteGame(gameId: GameId): Observable<void> {
    const url = `${this.backendUrl}/game/${gameId.value}`;
    return this.httpClient.delete<void>(url);
  }

  deleteTournament(tournamentId: TournamentId): Observable<void> {
    const url = `${this.backendUrl}/tournament/${tournamentId.value}`;
    return this.httpClient.delete<void>(url);
  }


  startGame(gameId: GameId): Observable<void> {
    const url = `${this.backendUrl}/game/${gameId.value}/start`;
    return this.httpClient.post<void>(url, {});
  }

  startTournament(tournamentId: TournamentId): Observable<void> {
    const url = `${this.backendUrl}/tournament/${tournamentId.value}/start`;
    return this.httpClient.post<void>(url, {});
  }
}
