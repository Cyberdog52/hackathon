import { Injectable } from "@angular/core";
import { Client } from "@stomp/stompjs";
import {Player} from "../model/player";



@Injectable({
  providedIn: "root"
})
export class SocketService {

  public client: Client;
  constructor() {
    this.client = new Client({
      brokerURL: "ws://localhost:8080/ws",
    });
    this.client.activate();
    this.mockUsers();
  }

connect(): void {
    this.client.activate();
}

disconnect(): void {
    this.client.deactivate();
  }

subscribe(s: string, param2: (message: any) => void) : void{
  this.client.subscribe(s, message => {
    param2(message);
  });
}

  unsubscribe(s: string): void {
    this.client.unsubscribe(s);
  }

  mockUsers(): void {
    setInterval(() => {
    /*  i++;
      if (i > 5){
        clearInterval(interval);
      }*/
      this.client.publish({
        destination: "/topic/game/*/player",
        body: JSON.stringify({
          player:{
            id: String(Math.floor(Math.random() * 100)),
            name: "Player " + String(Math.floor(Math.random() * 100)),
            icon: "icon",
          } as Player,
          matchId: String(Math.floor(Math.random() * 100_000_000)),
          }
          ),
      });
    }, 4000, );
  }
}
