import { HeaderComponent } from "./header.component";

describe("HeaderComponent", () => {
  let component: HeaderComponent;

  beforeEach(async () => {
    component = new HeaderComponent();
  });

  it("should create", () => {
    expect(component).toBeTruthy();
  });
});
