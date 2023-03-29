import { Injectable } from "@angular/core";
import { adjectives, animals, colors, Config, starWars, uniqueNamesGenerator } from "unique-names-generator";
import { UUID } from "../model/uuid";

@Injectable({
  providedIn: "root"
})
export class NameGeneratorService {
  private readonly generatorConfig: Config;

  constructor() {
    this.generatorConfig = {
      dictionaries: [adjectives, colors, starWars],
      separator: " ",
      length: 3,
      style: "capital"
    };

  }

  public getNameForUUID(uuid: UUID): string {
    return uniqueNamesGenerator({ ...this.generatorConfig, seed: uuid });
  }
}
