import { Component, Injectable } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";
import { MessageService } from "../services/MessageService";
import { Subscription } from "rxjs";

@Component({
  selector: "app-messages",
  templateUrl: "./messages.component.html",
  styleUrls: ["./messages.component.scss"]
})
export class MessagesComponent {
  private subscription: Subscription | undefined;

  constructor(private _snackBar: MatSnackBar, private messageService: MessageService) {}

  ngOnInit(): void {
    this.subscription = this.messageService.messages$.subscribe(message => {
      if (message) {
        this.openSnackBar(message);
      }
    });
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }

  openSnackBar(message: string): void {
    this._snackBar.open(message, "",{
      duration: 5000,
    });
  }
}

