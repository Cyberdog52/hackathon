import { UUID } from "../../uuid";
import { Coordinate } from "./events";

export interface AttackTurnAction {
  playerId: UUID;
  coordinate: Coordinate;
  gameId: UUID;
}
