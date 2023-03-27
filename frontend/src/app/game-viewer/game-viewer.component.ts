import { Component, OnDestroy, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { GameEventService } from "../../services/game-event.service";
import { map, Subject, Subscription, switchMap } from "rxjs";
import { UUID } from "../../model/uuid";
import { PlayingEvent } from "../../model/game/playing/actions";

@Component({
  selector: "app-game-viewer",
  templateUrl: "./game-viewer.component.html",
  styleUrls: ["./game-viewer.component.scss"]
})
export class GameViewerComponent implements OnInit, OnDestroy {
  private gameEventSubscription?: Subscription;

  public events$: Subject<string[]> = new Subject<string[]>();
  private events: string[] = [];

  constructor(
    private readonly activatedRoute: ActivatedRoute,
    private gameViewerService: GameEventService) {

  }

  ngOnInit(): void {
    this.gameEventSubscription = this.activatedRoute.params
      .pipe(
        map((params) => params.gameId),
        switchMap((gameId: UUID) => {
          return this.gameViewerService
            .listenToGameEvents(gameId);
        })
      ).subscribe((event) => {
        this.onEvent(event);
      });

  }

  ngOnDestroy(): void {
    this.gameEventSubscription?.unsubscribe();
  }

  private onEvent(event: PlayingEvent): void {
    this.events = this.events.concat([JSON.stringify(event)]);
    this.events$.next(this.events);
  }
}
