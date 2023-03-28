import { Player } from "./player";

export interface Match {
  id: string;
  players: Player[];
  full: boolean;
}
