import { UUID } from "../../uuid";
import { Coordinate } from "./events";

export interface AttackTurnAction {
  playerId: UUID;
  coordinate: Coordinate;
  gameId: UUID;
}


export interface PlaceBoatAction {
  playerId: UUID;
  coordinate: Coordinate;
  gameId: UUID;
}

export interface  PlayerJoinAction {
  playerId: UUID;
  gameId: UUID;
}
