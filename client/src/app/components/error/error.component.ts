import {Component} from '@angular/core';
import {MatIconModule} from "@angular/material/icon";
import {MatToolbarModule} from "@angular/material/toolbar";

@Component({
  standalone: true,
  selector: 'app-error',
  templateUrl: './error.component.html',
  imports: [MatIconModule, MatToolbarModule]
})
export class ErrorComponent {
  errorMessage: string = 'Something went wrong! Please try again later.';

  constructor() {
    this.errorMessage = history.state?.errorMessage || this.errorMessage;
  }
}
