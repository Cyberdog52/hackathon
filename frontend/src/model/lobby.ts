import { UUID } from "./uuid";

export interface GameDto {
  id: GameId;
  players: Player[];
  status: GameStatus;
  state: GameState;
  winner?: PlayerId;
}

export interface Player {
  id: PlayerId;
  name: PlayerName;
}

export type PlayerName = string;

export type GameId = UUID;

export type PlayerId = UUID;


export type GameState = any;

export interface PlayRequest {
  player: PlayerId;
  requestId: RequestId;
  possibleActions: GameAction[];
}

export interface RequestId {
  value: string;
}

export interface Round {
  moves: Move[];
  winner?: PlayerId;
}

export interface Move {
  playerId: PlayerId;
  requestId: RequestId;
  action: GameAction;
}

export enum GameStatus {
  NOT_STARTED = "NOT_STARTED",
  IN_PROGRESS = "IN_PROGRESS",
  FINISHED = "FINISHED",
  DELETED = "DELETED"
}

export enum GameAction {
  ROCK = "ROCK",
  PAPER = "PAPER",
  SCISSORS = "SCISSORS"
}
