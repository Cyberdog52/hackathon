import { Component } from "@angular/core";
import { MatchService } from "../../services/MatchService";
import { Router } from "@angular/router";

@Component({
  selector: "app-header",
  templateUrl: "./header.component.html",
  styleUrls: ["./header.component.scss"]
})
export class HeaderComponent {

  routeToDashboard(): void {
    this.router.navigate(["/dashboard"])
  }

  constructor(
    private router: Router
  ) {}
}
