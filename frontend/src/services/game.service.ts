import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {GameDto} from "../model/lobby";

@Injectable({
  providedIn: 'root'
})
export class GameService {

  private backendUrl = "api/lobby";

  constructor(private httpClient: HttpClient) {
  }

  public getGame(gameId: string): Observable<GameDto> {
    const url = `${this.backendUrl}/game/${gameId}`;
    return this.httpClient.get<GameDto>(url);
  }
}
