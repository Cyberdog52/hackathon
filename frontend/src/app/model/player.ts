import {Card} from "./card";

export interface Player {
  id: string;
  name: string;
  icon: string;
  currentDeck: Card[];
}
