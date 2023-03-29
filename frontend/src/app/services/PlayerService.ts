import { Injectable } from "@angular/core";

import { catchError, Observable, of, tap, throwError } from "rxjs";
import { HttpClient, HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import { MessageService } from "./MessageService";
import { PlayerDto } from "../model/playerDto";
import { Player } from "../model/player";

@Injectable({ providedIn: "root" })
export class PlayerService {

  private createPlayerUrl = "api/player/create";

  constructor(private http: HttpClient,
              private messageService: MessageService) { }

  httpOptions = {
    headers: new HttpHeaders({ "Content-Type": "application/json" })
  };

  createPlayer(playerDto: PlayerDto): Observable<string> {
    const options = { responseType: "text" as const };
    console.log("try to create player " + playerDto.name);
    return this.http.post(this.createPlayerUrl, playerDto, options).pipe(
      tap((player_id: string) => this.messageService.add("PlayerService: created player " + player_id)),
      catchError(this.handleError)
    );
  }

  getPlayers(): Observable<Player[]> {
    return this.http.get<Player[]>("api/player", this.httpOptions).pipe(
      tap((players: Player[]) => console.log("PlayerService: fetched player " + players.length)),
      catchError(this.handleError)
    );
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error("An error occurred:", error.error);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, body was: `, error.error);
    }
    // Return an observable with a user-facing error message.
    return throwError(() => new Error("Something bad happened; please try again later."));
  }
}
