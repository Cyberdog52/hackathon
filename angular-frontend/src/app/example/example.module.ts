import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ExampleComponent } from './example-component/example.component';
import { HttpClientModule } from '@angular/common/http';
import { StyledTitleComponent } from '../shared/layout/styled-title/styled-title.component';
import { StyledButtonComponent } from '../shared/layout/styled-button/styled-button.component';
import { OtherComponent } from './other-component/other.component';

@NgModule({
  declarations: [
    ExampleComponent,
    OtherComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    StyledTitleComponent,
    StyledButtonComponent
  ],
  exports: [
    ExampleComponent,
    OtherComponent
  ]
})
export class ExampleModule { }
