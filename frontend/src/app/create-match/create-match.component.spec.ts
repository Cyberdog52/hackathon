import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateMatchComponent } from './create-match.component';

describe('CreateMatchComponent', () => {
  let component: CreateMatchComponent;
  let fixture: ComponentFixture<CreateMatchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateMatchComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateMatchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
