import { finalize, Observable, ReplaySubject } from "rxjs";
import { Injectable } from "@angular/core";
import { GameAction } from "../model/lobby";
import { PlayingEvent } from "../model/game/playing/actions";
import { StompClient } from "./stomp-client";

@Injectable({
  providedIn: "root"
})
export class GameEventService {
  private readonly wsUrl = "ws://localhost:8080/topic"
  private subject: ReplaySubject<PlayingEvent> = new ReplaySubject<PlayingEvent>();

  public listenToGameEvents(gameId: string): Observable<PlayingEvent> {
    const websocketClient = new StompClient<PlayingEvent, GameAction>(
      this.wsUrl,
      `/topic/game/${gameId}/spectate`
    );

    websocketClient.onEvent().subscribe(e => {
      this.subject.next(e);
    })

    return this.subject.pipe(
      finalize(() => websocketClient.close())
    );
  }

}
