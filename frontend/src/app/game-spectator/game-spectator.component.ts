import { Component, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { GameEventService } from "../../services/game-event.service";
import { map, Subject, Subscription, switchMap } from "rxjs";
import { UUID } from "../../model/uuid";
import { AttackStatus, PlayingEvent } from "../../model/game/playing/events";
import { MapComponent } from "../game/map/map.component";

@Component({
  selector: "app-game-viewer",
  templateUrl: "./game-spectator.component.html",
  styleUrls: ["./game-spectator.component.scss"]
})
export class GameSpectatorComponent implements OnInit, OnDestroy {
  private gameEventSubscription?: Subscription;

  public events$: Subject<string[]> = new Subject<string[]>();
  private events: string[] = [];

  public sizeX = 24.0;
  public sizeY = 24.0;

  public player1Id!: UUID;
  public player2Id!: UUID;
  public gameId!: UUID;

  @ViewChild("mapPlayer1")
  public player1Map!: MapComponent;

  @ViewChild("mapPlayer2")
  public player2Map!: MapComponent;

  constructor(
    private readonly activatedRoute: ActivatedRoute,
    private gameViewerService: GameEventService) {

  }

  ngOnInit(): void {
    this.gameEventSubscription = this.activatedRoute.params
      .pipe(
        map((params) => params.gameId),
        // TODO: Fetch game data here
        switchMap((gameId: UUID) => {
          this.gameId = gameId;
          this.player1Id = "1bcdebdc-8e97-4a6d-a0d8-c3f1cc0853f7"
          this.player1Id = "803b89be-deb1-427f-a582-b85739d6f4ec"
          return this.gameViewerService.listenToGameEvents(gameId);
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
    this.mapEventToMapChanges(event);
  }

  private mapEventToMapChanges(event: PlayingEvent): void {
    if (event.type === "GameEndEvent") {
      this.player1Map.resetMap();
      this.player2Map.resetMap();
      return;
    }

    if (event.type === "PlaceBoatEvent") {
      const map = this.getMapOfPlayer(event.playerId);
      map.setBoat(event.coordinate);
      return;
    }

    if (event.type === "AttackEvent") {
      const map = this.getMapOfAttackedPlayer(event.attackingPlayerId);
      if (event.status === AttackStatus.HIT) {
        map.setHit(event.coordinate);
      } else {
        map.setMiss(event.coordinate);
      }
      return;
    }

  }

  private getMapOfAttackedPlayer(attackingPlayerId: UUID): MapComponent {
    if (attackingPlayerId !== this.player1Id) {
      return this.player2Map;
    } else {
      return this.player1Map;
    }
  }

  private getMapOfPlayer(playerId: UUID): MapComponent {
    if (playerId !== this.player1Id) {
      return this.player1Map;
    } else {
      return this.player2Map;
    }
  }
}
