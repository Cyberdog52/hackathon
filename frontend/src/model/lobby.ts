export interface Game {
  gameId: GameId;
  players: Player[];
  state: GameState;
}

export interface Player {
  playerId: PlayerId;
  name: string;
}

export interface GameId {
  value: number;
}

export interface PlayerId {
  value: number;
}

export enum GameState {
  WAITING_FOR_PLAYERS = "WAITING_FOR_PLAYERS",
  IN_PROGRESS = "IN_PROGRESS",
  FINISHED = "FINISHED",
  DELETED = "DELETED"
}
