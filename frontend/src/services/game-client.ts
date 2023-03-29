import { Injectable } from "@angular/core";
import { UUID } from "../model/uuid";

@Injectable({
  providedIn: "root"
})
export class GameClient {

  private playerId?: UUID;

  public setCurrentPlayerId(id: UUID): void {
    this.playerId = id;
  }

  public getCurrentPlayerId(): UUID | undefined {
    return this.playerId;
  }

  public isPlayerIdSet(): boolean {
    return this.playerId !== undefined;
  }
}
