import { UUID } from "../../uuid";
import { EventType } from "../event-type";

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
  type: EventType.PLAYER_ATTACKED;
  status: AttackStatus;
  attackingPlayerId: UUID;
  coordinate: Coordinate;
}

export interface GameStartSetupEvent {
  type: EventType.SETUP_GAME,
  gameId: UUID;
  mapSizeX: number;
  mapSizeY: number;
  numberOfBoats: number;
  playerIds: UUID[];
}

export interface GameStartPlayingEvent {
  type: EventType.START_PLAYING;
  playerTurnOrder: UUID[];
}

export interface GameEndEvent {
  type: EventType.GAME_ENDED;
  winnerId: UUID;
}

export type PlaceBoatEvent = {
  type: EventType.BOAT_PLACED;
  playerId: UUID;
  coordinates: Coordinate[];
  successful: boolean;
}

export type TakeTurnEvent = {
  type: EventType.TAKE_TURN;
  playerId: UUID;
  actions: GamePlayingAction[];
}


export type PlayerJoinedEvent = {
  type: EventType.PLAYER_JOINED;
  playerId: UUID;
  gameId: UUID;
}

export type PlayingEvent = AttackEvent
  | GameStartSetupEvent
  | GameStartPlayingEvent
  | PlaceBoatEvent
  | TakeTurnEvent
  | GameEndEvent
  | PlayerJoinedEvent;
