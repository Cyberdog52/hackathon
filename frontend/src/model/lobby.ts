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

export interface PlayerName {
  value: string;
}

export interface GameId {
  value: number;
}

export interface PlayerId {
  value: string;
}

export interface GameState {
  currentRequests: PlayRequest[];
  moves: Move[];
  board: number[][];
}

export interface PlayRequest {
  player: PlayerId;
  requestId: RequestId;
  possibleActions: GameAction[];
}

export interface RequestId {
  value: string;
}

export interface Move {
  playerId: PlayerId;
  requestId: RequestId;
  action: GameAction;
}

export enum GameStatus {
  NOT_STARTED = 'NOT_STARTED',
  IN_PROGRESS = 'IN_PROGRESS',
  FINISHED = 'FINISHED',
  DELETED = 'DELETED',
}

export interface GameAction {
  from: Position;
  to: Position;
}

export interface Position {
  x: number;
  y: number;
}
