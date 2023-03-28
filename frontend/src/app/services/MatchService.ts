import { Injectable } from "@angular/core";

import { catchError, Observable, tap, throwError } from "rxjs";
import { Match } from "../model/match";
import { HttpClient, HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import { MessageService } from "./MessageService";
import { PlayerId } from "../model/playerId";
import { MatchId } from "../model/matchId";

@Injectable({ providedIn: "root" })
export class MatchService {

  private matchUrl = "api/lobby/match";

  constructor(private http: HttpClient,
              private messageService: MessageService) { }

  httpOptions = {
    headers: new HttpHeaders({ "Content-Type": "application/json" })
  };

  join(player_id: PlayerId, match_id: string): Observable<Match> {
    return this.http.post<Match>(this.matchUrl + "/" + match_id + "/join", player_id, this.httpOptions).pipe(
      tap((match: Match) => console.log("MatchService: joined match " + match.id)),
      catchError(this.handleError)
    );
  }

  createMatch(): Observable<string> {
    const options = { responseType: "text" as const };
    const body = { };
    return this.http.post(this.matchUrl, body, options).pipe(
      tap((match: string) => console.log("MatchService: created match " + match)),
      catchError(this.handleError)
    );
  }

  getMatch(id: string): Observable<Match> {
    return this.http.get<Match>(this.matchUrl + "/" + id, this.httpOptions).pipe(
      tap((match: Match) => console.log("MatchService: fetched match " + match.id)),
      catchError(this.handleError)
    );
  }

  getMatches(): Observable<Match[]> {
    return this.http.get<Match[]>( "api/lobby/waiting", this.httpOptions).pipe(
      tap((matches: Match[]) => console.log("MatchService: fetched matches " + matches.length)),
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

  start(match_id: string): Observable<MatchId> {
    const body = { };
    return this.http.post<MatchId>(this.matchUrl + "/" + match_id + "/start", body, this.httpOptions).pipe(
      tap((match: MatchId) => console.log("MatchService: started match " + match.id)),
      catchError(this.handleError)
    );
  }
}
