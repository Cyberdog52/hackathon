import {Suits} from "./suits";
import {CardValues} from "./card-values";

export interface Card {
  suit: Suits;
  value: CardValues
}
