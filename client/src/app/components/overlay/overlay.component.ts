import {Component} from '@angular/core';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {NgIf} from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-overlay',
  imports: [NgIf, MatProgressSpinnerModule],
  template: `
    <div class="jx-overlay">
      <mat-spinner color="accent" strokeWidth="3" diameter="50"></mat-spinner>
    </div>
  `,
  styles: [
    `
      .jx-overlay {
        width: 100%;
        height: 100%;
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background-color: rgba(255, 255, 255, 0.5);
        display: flex;
        justify-content: center;
        align-items: center;
        z-index: 1001;
      }
    `,
  ],
})
export class OverlayComponent {
}
