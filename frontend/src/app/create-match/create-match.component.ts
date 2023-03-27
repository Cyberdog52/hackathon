import { Component } from "@angular/core";
import { Match } from "../../model/match";
import { MatchService } from "../../services/MatchService";
import { Router } from "@angular/router";
import { MessageService } from "../../services/MessageService";

@Component({
  selector: "app-create-match",
  templateUrl: "./create-match.component.html",
  styleUrls: ["./create-match.component.scss"]
})
export class CreateMatchComponent {
  constructor(
    private matchService: MatchService,
    private router: Router,
    private messageService: MessageService
  ) {}

  createMatch(): void {
    let match_id = "";
    this.matchService.createMatch()
      .subscribe(match => match_id = match);
    console.log("match_id: " + match_id);
    this.messageService.add("Match created. Match ID: " + match_id);
    this.router.navigate(["/match/" + match_id]);
  }
}
