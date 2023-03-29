import { Injectable } from "@angular/core";
import { adjectives, animals, colors, Config, uniqueNamesGenerator } from "unique-names-generator";
import { UUID } from "../model/uuid";

@Injectable({
  providedIn: "root"
})
export class NameGeneratorService {
  private readonly generatorConfig: Config;
  private readonly seedSuffix: string;

  constructor() {
    this.seedSuffix = (new Date()).getTime() + "";
    this.generatorConfig = {
      dictionaries: [adjectives, colors, animals],
      separator: " ",
      length: 3,
      style: "capital"
    };

  }

  public getNameForUUID(uuid: UUID): string {
    return uniqueNamesGenerator({ ...this.generatorConfig, seed: uuid + this.seedSuffix });
  }
}
