import { Component, Input } from "@angular/core";
import { Field } from "../../model/lobby";

@Component({
  selector: "app-field",
  templateUrl: "./field.component.html",
  styleUrls: ["./field.component.scss"],
})
export class FieldComponent {
  @Input() field!: Field;
  @Input() castle!: boolean;
}
