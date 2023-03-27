import { map, Observable, Subject, Subscription } from "rxjs";
import { RxStomp } from "@stomp/rx-stomp";

export interface EventMapper<Event> {
  map(messageEvent: MessageEvent): Event;
}

export class WebsocketClient<Event, Action> {
  private sock?: WebSocket;
  private socketOpen = false;
  private subject: Subject<MessageEvent> = new Subject();
  private stomp?: RxStomp;
  private stompSubscription?: Subscription;

  constructor(
    private readonly brokerUrl: string,
    private readonly topic: string,
    private readonly eventMapper: EventMapper<Event>
  ) {
    // Create WebSocket connection.
    // this.sock = new WebSocket(this.brokerUrl);

    // Listen for messages
    /*this.sock.addEventListener("message", (event) => {
      console.log("Message from server ", event.data);
    });*/

    this.stomp = new RxStomp();
    this.stomp.configure({
      brokerURL: this.brokerUrl
    });

    this.stomp.activate();

    this.stompSubscription = this.stomp
      .watch({ destination: this.topic })
      .subscribe((message) => {
        console.log(message.body);
      });


    /*interval(1500).subscribe(() => {
      this.onMessage({
        data: JSON.stringify({
          type: "AttackEvent",
          status: AttackStatus.HIT,
          attackingPlayerId: "4ad1c837-21f0-4f8b-8e8a-994d969ba736",
          coordinate: { x: 11, y: 22 }
        })
      } as unknown as MessageEvent)
    })*/
  }
  public onEvent(): Observable<Event> {
    return this.subject.pipe(
      map((messageEvent) => this.eventMapper.map(messageEvent))
    );
  }

  public send(action: Action): void {
//     this.sock?.send(JSON.stringify(action));
    this.stomp?.publish({
      destination: this.topic,
      body: JSON.stringify(action),
    });
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
