import { filter, map, Observable, Subject, Subscription } from "rxjs";
import { IFrame, RxStomp } from "@stomp/rx-stomp";

export class StompClient<Event, Action> {
  private subject: Subject<Event> = new Subject();
  private stomp?: RxStomp;
  private stompSubscription?: Subscription;

  constructor(
    private readonly brokerUrl: string,
    private readonly topic: string
  ) {
    this.stomp = new RxStomp();
    this.stomp.configure({
      brokerURL: "ws://localhost:8080/update",
      debug: (msg: string): void => {
        // console.log(new Date(), msg);
      }
    });

    this.stomp.activate();

    this.stompSubscription = this.stomp
      .watch({ destination: this.topic })
      .pipe(
        filter((message) => message.command === "MESSAGE"),
        map(this.mapSTOMPFrameToJSON)
      ).subscribe((message) => {
        this.subject.next(message);
      });
  }

  private mapSTOMPFrameToJSON(message: IFrame): Event {
    let json = "{}";
    if (message.isBinaryBody) {
      json = (new TextDecoder().decode(message.binaryBody));
    } else {
      json = message.body as string;
    }

    const obj = JSON.parse(json);
    obj.type = message.headers["EventType"];
    return obj;
  }

  public onEvent(): Observable<Event> {
    return this.subject;
  }

  public send(action: Action): void {
    // TODO: Untested
    this.stomp?.publish({
      destination: this.topic,
      body: JSON.stringify(action),
    });
  }

  public close(): void {
    this.stomp?.deactivate();
  }

}
