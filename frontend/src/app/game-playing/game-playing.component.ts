import { Component, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { GameEventService } from "../../services/game-event.service";
import { map, Subject, Subscription, switchMap } from "rxjs";
import { UUID } from "../../model/uuid";
import { AttackStatus, GamePlayingAction, PlayingEvent } from "../../model/game/playing/events";
import { CellClickEvent, MapComponent, MapValue } from "../game/map/map.component";
import { STATIC_HUMAN_PLAYER_ID, STATIC_OPPONENT_PLAYER_ID } from "../../model/mocks/mock-data";
import { GameState } from "src/model/game/playing/game-state";
import { GamePlayService } from "../../services/game-play.service";
import { EventType } from "../../model/game/event-type";

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
  public winnerId?: UUID;

  public player1Turn = true;
  public player2Turn = false;

  public gameId!: UUID;

  @ViewChild("mapPlayer1")
  public player1Map!: MapComponent;

  @ViewChild("mapPlayer2")
  public player2Map!: MapComponent;

  public gamePhase = GameState.SETUP;
  public readonly gamePhaseEnum = GameState;

  private playableActions: GamePlayingAction[] = [];

  constructor(
    private readonly activatedRoute: ActivatedRoute,
    private gameViewerService: GameEventService,
    private gamePlayService: GamePlayService) {
  }

  public onCellClickPlayer1Map(evt: CellClickEvent): void {
    console.log(evt);
    if (evt.value !== MapValue.EMPTY) {
      console.warn("Can not build an non-empty field");
      return;
    }
    if (this.gamePhase !== GameState.SETUP) {
      console.warn("Can not build outside of the SETUP phase");
      return;
    }
    // this.player1Turn = false;
    this.gamePlayService.placeBoat(evt.coordinate, this.player1Id, this.gameId).subscribe();
  }

  public onCellClickPlayer2Map(evt: CellClickEvent): void {
    console.log(evt);

    if (evt.value === MapValue.HIT || evt.value === MapValue.MISS) {
      console.warn("Can not attack already attacked field!");
      return;
    }

    if (!this.player1Turn) {
      console.warn("Cannot attack, it's not this players turn");
      return;
    }

    if (this.gamePhase !== GameState.PLAYING) {
      console.warn("Cannot attack in the non-play phase!");
      return;
    }

    // this.player1Turn = false;
    this.gamePlayService.attackPlayer(evt.coordinate, this.player1Id, this.gameId).subscribe();
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
    if (event.type === EventType.SETUP_GAME) {
      this.gamePhase = GameState.SETUP;
      this.player1Turn = true;
      this.player2Turn = true;
    }

    if (event.type === EventType.START_PLAYING) {
      this.gamePhase = GameState.PLAYING;
      this.player1Turn = false;
      this.player2Turn = false;
    }

    if (event.type === EventType.TAKE_TURN) {
      this.gamePhase = GameState.PLAYING;
      this.player1Turn = this.player1Id === event.playerId;
      this.player2Turn = this.player2Id === event.playerId;
      this.playableActions = event.actions;
    }

    if (event.type === EventType.GAME_ENDED) {
      this.gamePhase = GameState.END;
      this.winnerId = event.winnerId;
      this.player1Map.resetMap();
      this.player2Map.resetMap();
      return;
    }

    if (event.type === EventType.BOAT_PLACED) {
      const map = this.getMapOfPlayer(event.playerId);
      map.setBoatCoordinates(event.coordinates);
      return;
    }

    if (event.type === EventType.PLAYER_ATTACKED) {
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
      return this.player1Map;
    } else {
      return this.player2Map;
    }
  }

  private getMapOfPlayer(playerId: UUID): MapComponent {
    if (playerId === this.player1Id) {
      return this.player1Map;
    } else {
      return this.player2Map;
    }
  }

  public canAttack(): boolean {
    return this.player1Turn && this.playableActions.find((a) => a === GamePlayingAction.ATTACK) !== undefined;
  }
}
