import { delay, EMPTY, finalize, from, Observable, of } from "rxjs";
import {
  AttackStatus,
  Coordinate,
  GamePlayingAction,
  GameStartPlayingEvent,
  PlayingEvent
} from "../game/playing/events";
import { websocketMock } from "./websocket-mock";
import { STATIC_HUMAN_PLAYER_ID, STATIC_OPPONENT_PLAYER_ID } from "./mock-data";
import { HttpClient } from "@angular/common/http";
import { UUID } from "../uuid";

export class HttpClientMock {
  public post<T>(url: string, body: any | null, options?: any): Observable<T> {
    if (url.includes("/attack")) {
      return this.attackAnswer<T>(url, body, options);
    }
    if (url.includes("/placeBoat")) {
      return this.placeBoatAnswer<T>(url, body, options);
    }
    return EMPTY;
  }

  private attackAnswer<T>(url: string, body: any, options: any): Observable<T> {
    return of().pipe(
      finalize(() => {
        const nextPlayerID = body.playerId === STATIC_HUMAN_PLAYER_ID ? STATIC_HUMAN_PLAYER_ID : STATIC_OPPONENT_PLAYER_ID;
        from([
          {
            type: "AttackEvent",
            status: AttackStatus.HIT,
            attackingPlayerId: body.attackingPlayerId,
            coordinate: body.coordinate as Coordinate
          },
          {
            type: "TakeTurnEvent",
            actions: [GamePlayingAction.ATTACK],
            playerId: nextPlayerID
          },
          {
            type: "AttackEvent",
            status: AttackStatus.MISS,
            attackingPlayerId: nextPlayerID,
            coordinate: {
              y: body.coordinate.x,
              x: body.coordinate.y
            } as Coordinate
          },
          {
            type: "TakeTurnEvent",
            actions: [GamePlayingAction.ATTACK],
            playerId: body.playerId
          }
        ]).pipe(
          delay(750)
        ).subscribe((e) => {
          websocketMock.returnEvent(e as PlayingEvent);
        })
      })
    );
  }

  private numBoats = 0;

  private placeBoatAnswer<T>(url: string, body: any, options: any): Observable<T> {
    this.numBoats++;
    const evts: PlayingEvent[] = [
      {
        type: "PlaceBoatEvent",
        playerId: body.playerId,
        coordinate: body.coordinate as Coordinate,
        successful: true
      },
      {
        type: "PlaceBoatEvent",
        playerId: STATIC_OPPONENT_PLAYER_ID,
        coordinate: { y: body.coordinate.x, x: body.coordinate.y } as Coordinate,
        successful: true
      }
    ];

    if (this.numBoats === 5) {
      evts.push({
          type: "GameStartPlayingEvent",
          playerTurnOrder: [body.playerId as UUID, STATIC_OPPONENT_PLAYER_ID as UUID]
        },
        {
          type: "TakeTurnEvent",
          actions: [GamePlayingAction.ATTACK],
          playerId: body.playerId
        }
      );
    }
    return of().pipe(
      finalize(() => {
        from(evts).pipe(
          delay(500)
        ).subscribe((e) => {
          websocketMock.returnEvent(e as PlayingEvent);
        })
      })
    );
  }
}

export const httpClientMock = new HttpClientMock() as unknown as HttpClient;
