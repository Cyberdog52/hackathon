import { UUID } from "../../uuid";

export interface GamePlayingAction {
  id: string
}

export enum AttackStatus {
  HIT = "hit",
  MISS = "miss",
  IGNORED = "ignored"
}

export interface Coordinate {
  x: number;
  y: number;
}

export interface AttackEvent {
  type: "AttackEvent";
  status: AttackStatus;
  attackingPlayerId: UUID;
  coordinate: Coordinate;

}

export interface PlayerTurnEvent {
  type: "PlayerTurnEvent";
  playerId: UUID;
  actions: GamePlayingAction[];
}

export type PlayingEvent = AttackEvent | PlayerTurnEvent;
