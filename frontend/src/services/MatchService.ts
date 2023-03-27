import { Injectable } from "@angular/core";

import { Observable, of } from "rxjs";
import { Match } from "../model/match";
import { MATCHES } from "../mock/mock-matches";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { MessageService } from "./MessageService";

@Injectable({ providedIn: "root" })
export class MatchService {

  private matchUrl = "api/lobby/match";

  constructor(private http: HttpClient,
              private messageService: MessageService) { }

  httpOptions = {
    headers: new HttpHeaders({ "Content-Type": "application/json" })
  };

  createMatch(): Observable<string> {
    return this.http.post<string>(this.matchUrl, this.httpOptions)
  }

  getMatch(id: string): Observable<Match> {
    return this.http.get<Match>(this.matchUrl + "/" + id, this.httpOptions)
  }

  getMatches(): Observable<Match[]> {
    const matches = of(MATCHES);
    this.messageService.add("MatchService: fetched matches");
    return matches;
  }
}
