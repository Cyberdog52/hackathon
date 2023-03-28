import { Component } from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: "app-header",
  templateUrl: "./header.component.html",
  styleUrls: ["./header.component.scss"]
})
export class HeaderComponent {

  constructor(private router: Router) {

  }

  goToLobby() {

  }
}
