import { Component, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { GameEventService } from "../../services/game-event.service";
import { map, Subject, Subscription, switchMap } from "rxjs";
import { UUID } from "../../model/uuid";
import { AttackStatus, PlayingEvent } from "../../model/game/playing/actions";
import { MapComponent } from "../game/map/map.component";
import { STATIC_HUMAN_PLAYER_ID, STATIC_OPPONENT_PLAYER_ID } from "../../model/mocks";
import { GameState } from "../../model/lobby";

@Component({
  selector: "app-game-playing",
  templateUrl: "./game-playing.component.html",
  styleUrls: ["./game-playing.component.scss"]
})
export class GamePlayingComponent implements OnInit, OnDestroy {
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

  public gamePhase: GameState;

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
          this.player1Id = STATIC_HUMAN_PLAYER_ID
          this.player2Id = STATIC_OPPONENT_PLAYER_ID
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
