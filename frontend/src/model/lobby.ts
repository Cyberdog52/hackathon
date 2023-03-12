export interface GameDto {
  id: GameId;
  players: Player[];
  status: GameStatus;
  state: GameState;
}

export interface Player {
  id: PlayerId;
  name: PlayerName;
}

export interface PlayerName {
  value: string;
}

export interface GameId {
  value: number;
}

export interface PlayerId {
  value: number;
}

// eslint-disable-next-line @typescript-eslint/no-empty-interface
export interface GameState {
}

export enum GameStatus {
  WAITING_FOR_PLAYERS = "WAITING_FOR_PLAYERS",
  IN_PROGRESS = "IN_PROGRESS",
  FINISHED = "FINISHED",
  DELETED = "DELETED"
}
