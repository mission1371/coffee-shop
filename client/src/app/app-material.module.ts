import {NgModule} from '@angular/core';
import {ReactiveFormsModule} from '@angular/forms';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from "@angular/material/input";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatIconModule} from "@angular/material/icon";
import {MatDividerModule} from "@angular/material/divider";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatExpansionModule} from "@angular/material/expansion";
import {MatCardModule} from "@angular/material/card";
import {MatBadgeModule} from "@angular/material/badge";
import {MatDialogModule} from "@angular/material/dialog";
import {MatTableModule} from "@angular/material/table";

const materials = [
  //
  MatCardModule,
  MatIconModule,
  MatBadgeModule,
  MatInputModule,
  MatTableModule,
  MatButtonModule,
  MatDialogModule,
  MatDividerModule,
  MatToolbarModule,
  MatSnackBarModule,
  MatExpansionModule,
  MatFormFieldModule,
];

@NgModule({
  imports: [...materials, ReactiveFormsModule],
  exports: [...materials, ReactiveFormsModule],
})
export class AppMaterialModule {
}
