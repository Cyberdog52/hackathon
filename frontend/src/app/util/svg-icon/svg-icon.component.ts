import { Component, Input, OnChanges, SecurityContext } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { DomSanitizer } from "@angular/platform-browser";
import { CardValues } from "../../model/card-values";
import { Suits } from "../../model/suits";

@Component({
  selector: "app-svg-icon",
  template: "<span [innerHTML]='svgIcon'></span>",
})
export class SvgIconComponent implements OnChanges {

  @Input()
  public cardValue?: CardValues;

  @Input()
  public suit?: Suits;
  @Input()
  public name?: string;

  public svgIcon: any;

  constructor(
    private httpClient: HttpClient,
    private sanitizer: DomSanitizer,
  ) {
  }

  public ngOnChanges(): void {
    if (this.name === "backSide") {
      this.httpClient
        .get("assets/img/cards/svg/backside.svg", { responseType: "text" })
        .subscribe(value => {
          this.svgIcon = this.sanitizer.bypassSecurityTrustHtml(value);
        });
    }
    else {
      this.httpClient
        .get(`assets/img/cards/svg/${this.cardValue}_${this.suit}.svg`, { responseType: "text" })
        .subscribe(value => {
          this.svgIcon = this.sanitizer.bypassSecurityTrustHtml(value);
        });
    }

  }

}
