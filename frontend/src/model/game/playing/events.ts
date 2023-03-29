import { UUID } from "../../uuid";

export enum GamePlayingAction {
  ATTACK = "ATTACK"
}

export enum AttackStatus {
  HIT = "HIT",
  MISS = "MISS",
  IGNORED = "IGNORED"
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

export interface GameStartSetupEvent {
  type: "GameStartSetupEvent"
}

export interface GameStartSetupEvent {
  type: "GameStartSetupEvent";
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

export type TakeTurnEvent = {
  type: "TakeTurnEvent";
  playerId: UUID;
  actions: GamePlayingAction[];
}

export type PlayingEvent = AttackEvent
  | PlayerTurnEvent
  | GameStartSetupEvent
  | GameStartPlayingEvent
  | PlaceBoatEvent
  | TakeTurnEvent
  | GameEndEvent;
