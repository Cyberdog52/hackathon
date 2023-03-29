import {Component, Inject} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {MAT_DIALOG_DATA} from "@angular/material/dialog";
import {Player} from "../../model/lobby";
import {PlayerListResponse} from "../../services/lobby.service";

@Component({
  selector: 'app-create-game-dialog',
  templateUrl: './create-game-dialog.component.html',
  styleUrls: ['./create-game-dialog.component.scss']
})
export class CreateGameDialogComponent {

  createGameForm: FormGroup;

  constructor(@Inject(MAT_DIALOG_DATA) public data: DialogData, private fb: FormBuilder) {
    this.createGameForm = this.fb.group({
      player1: [''],
      player2: [''],
    })
  }

}

export interface DialogData {
  players: PlayerListResponse[];
}
