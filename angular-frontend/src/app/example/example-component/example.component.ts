import { Component, OnInit } from "@angular/core";
import { RemoteService } from "../../shared/remote-service/remote.service";
import { ExampleDto } from "../model/example-dto.model";

@Component({
  selector: "example-component",
  templateUrl: "./example.component.html",
  styleUrls: ["./example.component.scss"]
})
export class ExampleComponent implements OnInit {
  public exampleDto?: ExampleDto;
  public isLoading = true;

  constructor(private remoteService: RemoteService) {
  }

  public ngOnInit(): void {
    this.loadExample();
  }

  public async loadExample(): Promise<void> {
    this.isLoading = true;
    const exampleDto = await this.remoteService.get<ExampleDto>("");
    if (exampleDto) {
      this.exampleDto = exampleDto;
    }
    this.isLoading = false;
  }
}
