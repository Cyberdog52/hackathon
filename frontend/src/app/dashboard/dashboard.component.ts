import { Component } from "@angular/core";
import { MatchService } from "../services/MatchService";
import { Match } from "../model/match";
import { Router } from "@angular/router";

@Component({
  selector: "app-dashboard",
  templateUrl: "./dashboard.component.html",
  styleUrls: ["./dashboard.component.scss"]
})
export class DashboardComponent {
  showMatches():void {
    this.router.navigate(["/matches"])
  }

  constructor(
    private matchService: MatchService,
    private router: Router
  ) {}
}
