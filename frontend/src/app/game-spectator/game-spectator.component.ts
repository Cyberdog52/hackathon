import { Component, OnDestroy, OnInit, ViewChild } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { GameEventService } from "../../services/game-event.service";
import { map, Subject, Subscription, switchMap } from "rxjs";
import { UUID } from "../../model/uuid";
import { AttackStatus, PlayingEvent } from "../../model/game/playing/events";
import { MapComponent } from "../game/map/map.component";
import { EventType } from "../../model/game/event-type";
import { GameState } from "../../model/game/playing/game-state";
import { NameGeneratorService } from "../../services/name.service";

@Component({
  selector: "app-game-viewer",
  templateUrl: "./game-spectator.component.html",
  styleUrls: ["./game-spectator.component.scss"]
})
export class GameSpectatorComponent implements OnInit, OnDestroy {
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

  public gameWinnerId?: UUID;

  public gameId!: UUID;

  @ViewChild("mapPlayer1")
  public player1Map!: MapComponent;

  @ViewChild("mapPlayer2")
  public player2Map!: MapComponent;


  constructor(
    private readonly activatedRoute: ActivatedRoute,
    private gameViewerService: GameEventService,
    private readonly nameGeneratorService: NameGeneratorService) {

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
      if (this.player1Id === undefined) {
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

      this.player1Id = event.playerIds[0];
      this.player2Id = event.playerIds[1];
      this.player1Name = this.nameGeneratorService.getNameForUUID(this.player1Id);
      this.player2Name = this.nameGeneratorService.getNameForUUID(this.player2Id);


      return;
    }

    if (event.type === EventType.START_PLAYING) {
      this.gameState = GameState.PLAYING;
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
}
