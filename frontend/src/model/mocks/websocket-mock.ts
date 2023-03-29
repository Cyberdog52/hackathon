import { Observable, Subject } from "rxjs";
import { PlayingEvent } from "../game/playing/events";

export class WebsocketMock<Event> {
  private readonly subject: Subject<Event> = new Subject();

  public onEvent(): Observable<Event> {
    return this.subject;
  }

  public returnEvent(event: Event): void {
    this.subject.next(event);
  }
}

export const websocketMock = new WebsocketMock<PlayingEvent>();
