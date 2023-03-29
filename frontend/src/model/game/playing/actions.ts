import { UUID } from "../../uuid";
import { Coordinate } from "./events";

export enum BoatType {
  LARGE = 3,
  MEDIUM = 2,
  SMALL = 1,
}


export enum BoatDirection {
  UP = "UP",
  DOWN = "DOWN",
  LEFT = "LEFT",
  RIGHT = "RIGHT",
}


export interface AttackTurnAction {
  playerId: UUID;
  coordinate: Coordinate;
  gameId: UUID;
}


export interface PlaceBoatAction {
  playerId: UUID;
  coordinate: Coordinate;
  gameId: UUID;
  boatType: BoatType;
  boatDirection: BoatDirection;
}

export interface  PlayerJoinAction {
  playerId: UUID;
  gameId: UUID;
}
