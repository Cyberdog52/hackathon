import { Component, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { GameEventService } from "../../services/game-event.service";
import { map, Subject, Subscription, switchMap } from "rxjs";
import { UUID } from "../../model/uuid";
import { AttackStatus, GamePlayingAction, PlayingEvent } from "../../model/game/playing/events";
import { CellClickEvent, MapComponent, MapValue } from "../game/map/map.component";
import { GameState } from "src/model/game/playing/game-state";
import { GamePlayService } from "../../services/game-play.service";
import { EventType } from "../../model/game/event-type";
import { NameGeneratorService } from "../../services/name.service";
import { GameClient } from "../../services/game-client";

@Component({
  selector: "app-game-playing",
  templateUrl: "./game-playing.component.html",
  styleUrls: ["./game-playing.component.scss"]
})
export class GamePlayingComponent implements OnInit, OnDestroy {
  private gameEventSubscription?: Subscription;
  public readonly gameStateEnum = GameState;

  public eventsClean$: Subject<string[][]> = new Subject<string[][]>();
  private events: PlayingEvent[] = [];

  public sizeX = 0;
  public sizeY = 0;

  public player1Id!: UUID;
  public player2Id!: UUID;

  public player1Name = "";
  public player2Name = "";

  public player1Turn = true;
  public player2Turn = false;

  public gameWinnerId?: UUID;

  public gameId!: UUID;

  @ViewChild("mapPlayer1")
  public player1Map!: MapComponent;

  @ViewChild("mapPlayer2")
  public player2Map!: MapComponent;
  private playableActions: GamePlayingAction[] = [];

  constructor(
    private readonly activatedRoute: ActivatedRoute,
    private gameViewerService: GameEventService,
    private gamePlayService: GamePlayService,
    private gameClient: GameClient,
    private readonly nameGeneratorService: NameGeneratorService) {

  }

  ngOnInit(): void {
    this.gameEventSubscription = this.activatedRoute.params
      .pipe(
        map((params) => params.gameId),
        // TODO: Fetch game data here
        switchMap((gameId: UUID) => {
          this.gameId = gameId;
          this.player1Id = this.gameClient.getCurrentPlayerId() as string;
          return this.gameViewerService.listenToGameEvents(gameId);
        })
      ).subscribe((event) => {
        this.onEvent(event);
      });

  }

  ngOnDestroy(): void {
    this.gameEventSubscription?.unsubscribe();
  }


  public onCellClickPlayer1Map(evt: CellClickEvent): void {
    console.log(evt);
    if (evt.value !== MapValue.EMPTY) {
      console.warn("Can not build an non-empty field");
      return;
    }
    if (this.gameState !== GameState.SETUP) {
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

    if (this.gameState !== GameState.PLAYING) {
      console.warn("Cannot attack in the non-play phase!");
      return;
    }

    // this.player1Turn = false;
    this.gamePlayService.attackPlayer(evt.coordinate, this.player1Id, this.gameId).subscribe();
  }

  private onEvent(event: PlayingEvent): void {
    this.events = this.events.concat([event]);
    this.eventsClean$.next(this.formatEvents(this.events));
    this.mapEventToMapChanges(event);
  }

  public formatEvents(events: PlayingEvent[]): string[][] {
    return events.map(event => {
      return [event.type + ":", JSON.stringify(event)];
    });
  }

  public gameState = GameState.LOBBY;

  public eventList: PlayingEvent[] = []

  private mapEventToMapChanges(event: PlayingEvent): void {
    this.eventList.push(event);
    console.log(event.type, event);

    if (event.type === EventType.PLAYER_JOINED) {
      if (event.playerId === this.gameClient.getCurrentPlayerId()) {
        this.player1Id = event.playerId;
        this.player1Name = this.nameGeneratorService.getNameForUUID(this.player1Id);
      } else {
        this.player2Id = event.playerId;
        this.player2Name = this.nameGeneratorService.getNameForUUID(this.player2Id);
      }
    }

    if (event.type === EventType.SETUP_GAME) {
      this.gameState = GameState.SETUP;

      this.sizeX = event.mapSizeX;
      this.sizeY = event.mapSizeY;

      this.player2Id = event.playerIds.find(s => s !== this.gameClient.getCurrentPlayerId()) ?? "unknown";
      this.player1Name = this.nameGeneratorService.getNameForUUID(this.player1Id);
      this.player2Name = this.nameGeneratorService.getNameForUUID(this.player2Id);

      this.player1Turn = true;
      this.player2Turn = true;

      return;
    }

    if (event.type === EventType.START_PLAYING) {
      this.gameState = GameState.PLAYING;
      this.player1Turn = false;
      this.player2Turn = false;
    }

    if (event.type === EventType.TAKE_TURN) {
      this.gameState = GameState.PLAYING;
      this.player1Turn = this.player1Id === event.playerId;
      this.player2Turn = this.player2Id === event.playerId;
      this.playableActions = event.actions;
    }

    if (event.type === EventType.GAME_ENDED) {
      this.gameState = GameState.END;
      this.gameWinnerId = event.winnerId;
      console.table(this.eventList);
      return;
    }

    if (event.type === EventType.BOAT_PLACED) {
      const map = this.getMapOfPlayer(event.playerId);
      map.setBoatCoordinates(event.coordinates);
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
    if (playerId === this.player1Id) {
      return this.player1Map;
    } else {
      return this.player2Map;
    }
  }

  public canRenderMap(): boolean {
    return this.sizeX > 0 && this.sizeY > 0;
  }

  public canAttack(): boolean {
    return this.player1Turn && this.playableActions.find((a) => a === GamePlayingAction.ATTACK) !== undefined;
  }
}
