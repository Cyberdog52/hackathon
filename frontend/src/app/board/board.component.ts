import { Component, Input } from '@angular/core';
import { Board } from '../../model/lobby';
import { getLetter } from 'src/helpers/helpers';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.scss'],
})
export class BoardComponent {
  @Input() board!: Board;
  getLetter = getLetter;
}
