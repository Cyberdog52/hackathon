import {Observable} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Game} from "../model/lobby";

@Injectable({
  providedIn: 'root'
})
export class LobbyService {

  private backendUrl = "api/lobby";

  constructor(private httpClient: HttpClient) {
  }

  public getGames(): Observable<Game[]>  {
    let url = `${this.backendUrl}/games`;
    return this.httpClient.get<Game[]>(url);
  }
}
