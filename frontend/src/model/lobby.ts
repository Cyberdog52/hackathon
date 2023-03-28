export interface GameDto {
  id: string;
  players: Player[];
  boards: Board[];
  status: GameStatus;
  state: GameState;
  winner?: PlayerId;
}

export interface Player {
  id: string;
  token: string;
  playerName: string;
}

export interface TopPlayers {
  id: string;
  playerName: string;
  score: number;
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
  rounds: Round[];
}

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
  CREATED = "CREATED",
  PLACE_SHIPS = "PLACE_SHIPS",
  SHOOT = "SHOOT",
  FINISHED = "FINISHED",
  DELETED = "DELETED"
}

export enum GameAction {
  ROCK = "ROCK",
  PAPER = "PAPER",
  SCISSORS = "SCISSORS"
}

export interface Board {
  playerId: string;
  ships: Ship[];
  shots: ShotResult[][];
}

export enum ShotResult {
  MISS = "MISS",
  HIT = "HIT",
  SUNK = "SUNK"
}

export interface ShipPosition {
  x: number;
  y: number;
}

export interface Ship {
  type: ShipType;
  x: number;
  y: number;
  positions: ShipPosition[];
  destroyed: ShipPosition[];
  orientation: Orientation;
}

export enum ShipType {
  AIRCRAFT_CARRIER = "AIRCRAFT_CARRIER",
  BATTLESHIP = "BATTLESHIP",
  SUBMARINE = "SUBMARINE",
  CRUISER = "CRUISER",
  DESTROYER = "DESTROYER"
}

export enum Orientation {
  HORIZONTAL = "HORIZONTAL",
  VERTICAL = "VERTICAL"
}
