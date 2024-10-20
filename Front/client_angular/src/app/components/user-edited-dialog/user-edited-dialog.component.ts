import { Component, Inject, signal } from '@angular/core';
import { IUsuarios } from '../../interfaces/iusuarios';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-user-edited-dialog',
  standalone: true,
  imports: [MatDialogModule],
  templateUrl: './user-edited-dialog.component.html',
  styleUrl: './user-edited-dialog.component.scss'
})
export class UserEditedDialogComponent {
  public getData = signal<IUsuarios| null>(null); 
  constructor( 
    private _dialogRef: MatDialogRef<UserEditedDialogComponent>,
    @Inject(MAT_DIALOG_DATA) private _data: IUsuarios ){}

  ngOnInit(): void {
    this.getData.set(this._data)
  }

  public closeModal(){
    this._dialogRef.close()
  }
}
