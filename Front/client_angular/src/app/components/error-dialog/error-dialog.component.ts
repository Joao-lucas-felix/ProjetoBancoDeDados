import { Component, Inject, signal } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-error-dialog',
  standalone: true,
  imports: [MatDialogModule],
  templateUrl: './error-dialog.component.html',
  styleUrl: './error-dialog.component.scss'
})
export class ErrorDialogComponent {
  public getData = signal< {
    error: string, 
    message: string, 
    path: number, 
    status: string, 
    timestamp: string
  } | null>(null); 
  constructor( 
    private _dialogRef: MatDialogRef<ErrorDialogComponent>,
    @Inject(MAT_DIALOG_DATA) private _data: {
      error: string, 
      message: string, 
      path: number, 
      status: string, 
      timestamp: string
    }){}

  ngOnInit(): void {
    this.getData.set(this._data)
  }

  public closeModal(){
    this._dialogRef.close()
  }
}
