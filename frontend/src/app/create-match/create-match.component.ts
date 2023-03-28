import { Component } from "@angular/core";
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
    this.matchService.createMatch()
      .subscribe(match => {
        console.log("match_id: " + match);
        this.messageService.add("Match created. Match ID: " + match);
        this.router.navigate(["/match/" + match]);
      });
  }
}
