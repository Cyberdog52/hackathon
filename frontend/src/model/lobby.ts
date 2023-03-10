export interface Game {
  id: number;
  players: Player[];
  state: GameState;
}

export interface Player {
  playerId: number;
  name: string;
}

export enum GameState {
  WAITING_FOR_PLAYERS = "WAITING_FOR_PLAYERS",
  IN_PROGRESS = "IN_PROGRESS",
  FINISHED = "FINISHED",
  DELETED = "DELETED"
}
