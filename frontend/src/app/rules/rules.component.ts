import { Component } from '@angular/core';
import { Board, Field, FieldState } from 'src/model/lobby';

@Component({
  selector: 'app-rules',
  templateUrl: './rules.component.html',
  styleUrls: ['./rules.component.scss'],
})
export class RulesComponent {
  board: Board = this.createBoard([
    [0, 0, 0, 1, 1, 1, 0, 0, 0],
    [0, 0, 0, 0, 1, 0, 0, 0, 0],
    [0, 0, 0, 0, 2, 0, 0, 0, 0],
    [1, 0, 0, 0, 2, 0, 0, 0, 1],
    [1, 1, 2, 2, 3, 2, 2, 1, 1],
    [1, 0, 0, 0, 2, 0, 0, 0, 1],
    [0, 0, 0, 0, 2, 0, 0, 0, 0],
    [0, 0, 0, 0, 1, 0, 0, 0, 0],
    [0, 0, 0, 1, 1, 1, 0, 0, 0],
  ]);

  board1a: Board = this.createBoard([
    [2, 0, 0, 0, 0],
    [0, 0, 0, 0, 0],
  ]);

  board1b: Board = this.createBoard([
    [0, 0, 0, 0, 2],
    [0, 0, 0, 0, 0],
  ]);

  board2a: Board = this.createBoard([
    [1, 0, 0],
    [0, 2, 1],
    [0, 0, 0],
  ]);

  board2b: Board = this.createBoard([
    [0, 0, 0],
    [1, 0, 1],
    [0, 0, 0],
  ]);

  createBoard(b: number[][]): Board {
    const board: Board = { fields: [] };
    for (let y = 0; y < b.length; y++) {
      const row: Field[] = [];
      for (let x = 0; x < b[y].length; x++) {
        row.push({ state: Object.values(FieldState)[b[y][x]] });
      }
      board.fields.push(row);
    }
    return board;
  }
}
