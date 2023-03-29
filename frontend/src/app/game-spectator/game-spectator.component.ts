import { Component, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { GameEventService } from "../../services/game-event.service";
import { map, Subject, Subscription, switchMap } from "rxjs";
import { UUID } from "../../model/uuid";
import { AttackStatus, PlayingEvent } from "../../model/game/playing/events";
import { MapComponent } from "../game/map/map.component";
import { EventType } from "../../model/game/event-type";

@Component({
  selector: "app-game-viewer",
  templateUrl: "./game-spectator.component.html",
  styleUrls: ["./game-spectator.component.scss"]
})
export class GameSpectatorComponent implements OnInit, OnDestroy {
  private gameEventSubscription?: Subscription;

  public events$: Subject<string[]> = new Subject<string[]>();
  private events: string[] = [];

  public sizeX = 24;
  public sizeY = 24;

  public player1Id!: UUID;
  public player2Id!: UUID;
  public gameWinnerId?: UUID;

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
    if (event.type === EventType.SETUP_GAME) {
      console.log(EventType.SETUP_GAME);
      this.sizeX = event.mapSizeX;
      this.sizeY = event.mapSizeY;
      this.player1Id = event.playerIds[0];
      this.player2Id = event.playerIds[1];
      return;
    }

    if (event.type === EventType.GAME_ENDED) {
      console.log(EventType.GAME_ENDED);
      this.gameWinnerId = event.winnerId;
      return;
    }

    if (event.type === EventType.BOAT_PLACED) {
      console.log(EventType.BOAT_PLACED);
      const map = this.getMapOfPlayer(event.playerId);
      map.setBoat(event.coordinate);
      return;
    }

    if (event.type === EventType.PLAYER_ATTACKED) {
      console.log(EventType.PLAYER_ATTACKED);
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
    if (attackingPlayerId === this.player1Id) {
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
