import {Component, OnInit} from '@angular/core';
import {GameService} from "../../services/game.service";
import {Observable} from "rxjs";
import {GameDto, GameStatus, Orientation, Ship, ShipType, ShotResult} from "../../model/lobby";
import {ActivatedRoute} from "@angular/router";
import {webSocket, WebSocketSubject} from 'rxjs/webSocket';
import {RxStompService} from "../../services/rx-stomp.service";
import {topic} from "../../services/stomp.cfg";


@Component({
  selector: 'app-game-overview',
  templateUrl: './game-overview.component.html',
  styleUrls: ['./game-overview.component.scss']
})
export class GameOverviewComponent implements OnInit {

  game$: Observable<GameDto> | undefined;
  game?: { gameDto: GameDto } | null = null;

  placeShips: GameStatus = GameStatus.PLACE_SHIPS;
  shoot: GameStatus = GameStatus.SHOOT;
  finished: GameStatus = GameStatus.FINISHED;


  hit: ShotResult = ShotResult.HIT;
  miss: ShotResult = ShotResult.MISS;
  sunk: ShotResult = ShotResult.SUNK;

  constructor(private gameService: GameService, private route: ActivatedRoute, private stomp: RxStompService) {
  }

  ngOnInit(): void {
    //this.game$ = this.gameService.getGame(this.route.snapshot.queryParamMap.get("gameId") || "");

    this.stomp.watch(topic).subscribe((response) => {
      this.game = JSON.parse(response.body);
      console.log(response.body);
    });
  }

  shipIsOnField(y: number, x: number, ships: Ship[]): boolean {
    let isOnField: boolean = false;
    ships.forEach(ship => {
        if (ship.positions.some(position => position.x === x && position.y === y)) {
          isOnField = true;
        }
      }
    );

    return isOnField;
  }

  private getShipLength(type: ShipType): number {
    if (type === ShipType.AIRCRAFT_CARRIER) {
      return 5;
    } else if (type === ShipType.BATTLESHIP) {
      return 4;
    } else if (type === ShipType.SUBMARINE) {
      return 3;
    } else if (type === ShipType.CRUISER) {
      return 3;
    } else {
      return 2;
    }
  }
}
