import { Component } from "@angular/core";
import { Match } from "../../model/match";
import { ActivatedRoute } from "@angular/router";
import { MatchService } from "../../services/MatchService";
import { parseUri } from "angular-in-memory-web-api";

@Component({
  selector: "app-match",
  templateUrl: "./match.component.html",
  styleUrls: ["./match.component.scss"]
})
export class MatchComponent {
  id: string | undefined;
  match: Match | undefined;

  constructor(
    private route: ActivatedRoute,
    private matchService: MatchService
  ) {}

  ngOnInit(): void {
    this.route.queryParams
      .subscribe(params => {
          this.id = params.id;
        }
      );

    this.getMatch();
  }

  getMatch(): void {
    this.matchService.getMatch(this.id!)
      .subscribe(match => this.match = match);
  }

}
