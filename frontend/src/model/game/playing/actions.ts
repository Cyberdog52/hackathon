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

export interface GameStartPlayingEvent {
  type: "GameStartPlayingEvent";
  playerTurnOrder: UUID[];
}

export interface GameEndEvent {
  type: "GameEndEvent";
  winnerId: UUID;
}

export type PlaceBoatEvent = {
  type: "PlaceBoatEvent";
  playerId: UUID;
  coordinate: Coordinate;
  successful: boolean;
}

export type PlayingEvent = AttackEvent | PlayerTurnEvent | GameStartPlayingEvent | PlaceBoatEvent | GameEndEvent;
