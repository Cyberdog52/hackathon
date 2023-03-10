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
  id: number;
}

export interface  PlayerId {
  id: number;
}

export enum GameState {
  WAITING_FOR_PLAYERS = "WAITING_FOR_PLAYERS",
  IN_PROGRESS = "IN_PROGRESS",
  FINISHED = "FINISHED",
  DELETED = "DELETED"
}
