import { interval, map, Observable, Subject } from "rxjs";
import { AttackStatus } from "../model/game/playing/actions";

export interface EventMapper<Event> {
  map(messageEvent: MessageEvent): Event;
}

export class WebsocketClient<Event, Action> {
  private sock?: WebSocket;
  private socketOpen = false;
  private subject: Subject<MessageEvent> = new Subject();

  constructor(
    private readonly websocketEndpoint: string,
    private readonly eventMapper: EventMapper<Event>
  ) {
    /*this.sock = new SockJS(this.websocketEndpoint);

    this.sock?.onopen = (): void => this.onOpen();
    this.sock?.onmessage = (message: MessageEvent): void => this.onMessage(message);
    this.sock?.onclose = (): void => this.onClose();*/

    interval(1500).subscribe(() => {
      this.onMessage({
        data: JSON.stringify({
          type: "AttackEvent",
          status: AttackStatus.HIT,
          attackingPlayerId: "4ad1c837-21f0-4f8b-8e8a-994d969ba736",
          coordinate: { x: 11, y: 22 }
        })
      } as unknown as MessageEvent)
    })
  }

  public onEvent(): Observable<Event> {
    return this.subject.pipe(
      map((messageEvent) => this.eventMapper.map(messageEvent))
    );
  }

  public send(action: Action): void {
    this.sock?.send(JSON.stringify(action));
  }

  public close(): void {
    this.sock?.close();
    this.socketOpen = false;
  }

  private onOpen(): void {
    this.socketOpen = true;
  }

  private onMessage(messageEvent: MessageEvent): void {
    this.subject.next(messageEvent)
  }

  private onClose(): void {
    this.socketOpen = false;
  }
}
