import {
  ChangeDetectorRef,
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges
} from "@angular/core";
import { UUID } from "../../../model/uuid";
import { Coordinate } from "../../../model/game/playing/events";

export enum MapValue {
  EMPTY = 0,
  BOAT = 1,
  HIT = 2,
  MISS = 3
}

export interface CellClickEvent {
  coordinate: Coordinate;
  value: MapValue
}

@Component({
  selector: "app-map",
  templateUrl: "./map.component.html",
  styleUrls: ["./map.component.scss"]
})
export class MapComponent implements OnInit, OnChanges {

  public readonly mapValuesEnum = MapValue;

  @Input()
  public disabled = true;

  @Input()
  public gameId!: UUID;

  @Input()
  public playerId!: UUID;

  @Input()
  public sizeX!: number;

  @Input()
  public sizeY!: number;

  @Output()
  public cellClick: EventEmitter<CellClickEvent> = new EventEmitter<CellClickEvent>();

  public map: number[][] = [[]];

  constructor(private ref: ChangeDetectorRef) {

  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.sizeX.previousValue !== changes.sizeX.currentValue
      || changes.sizeY.previousValue !== changes.sizeY.currentValue) {
      this.resetMap();
    }
  }

  ngOnInit(): void {
    this.resetMap();
  }

  public setHit(coordinate: Coordinate): void {
    this.setMapValue(coordinate, MapValue.HIT);
  }

  public setMiss(coordinate: Coordinate): void {
    this.setMapValue(coordinate, MapValue.MISS);
  }

  public setSingleBoatCoordinate(coordinate: Coordinate): void {
    this.setMapValue(coordinate, MapValue.BOAT);
  }

  public setBoatCoordinates(coordinates: Coordinate[]): void {
    coordinates.forEach(c => {
      this.setSingleBoatCoordinate(c);
    })
  }

  private setMapValue(coordinate: Coordinate, mapValue: MapValue): void {
    this.map[coordinate.x][coordinate.y] = mapValue;
    this.ref.markForCheck();
  }

  private getMapValue(coordinate: Coordinate): MapValue {
    return this.map[coordinate.x][coordinate.y] as MapValue;
  }

  public resetMap(): void {
    if (this.sizeY <= 0 && this.sizeX <= 0) {
      return;
    }
    this.map = [];
    for (let x = 0; x < this.sizeX; x++) {
      this.map.push(Array(this.sizeY).fill(0));
    }
  }

  public getRowName(index: number): string {
    return String.fromCharCode(65 + index);
  }

  public onCellClick(x: number, y: number): void {
    const coordinate = { x, y };
    this.cellClick.emit({
      coordinate,
      value: this.getMapValue(coordinate)
    })
  }
}
