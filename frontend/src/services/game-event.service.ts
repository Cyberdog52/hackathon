import { finalize, Observable, ReplaySubject } from "rxjs";
import { Injectable } from "@angular/core";
import { GameAction } from "../model/lobby";
import { PlayingEvent } from "../model/game/playing/actions";
import { EventMapper, WebsocketClient } from "./websocket-client";

class PlayingEventMapper implements EventMapper<PlayingEvent> {
  public map(message: MessageEvent): PlayingEvent {
    return JSON.parse(message.data);
  }
}

@Injectable({
  providedIn: "root"
})
export class GameEventService {
  private readonly wsUrl = "ws://localhost:8080/update"
  private subject: ReplaySubject<PlayingEvent> = new ReplaySubject<PlayingEvent>();

  public listenToGameEvents(gameId: string): Observable<PlayingEvent> {
    const websocketClient = new WebsocketClient<PlayingEvent, GameAction>(
      this.wsUrl,
      `/topic/game/${gameId}/spectate`,
      new PlayingEventMapper()
    );

    websocketClient.onEvent().subscribe(e => {
      this.subject.next(e);
    })

    return this.subject.pipe(
      finalize(() => websocketClient.close())
    );
  }

}
