import { Component, Inject, signal } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import IBook from '../../interfaces/ibook';

@Component({
  selector: 'app-dialog-livro-criado',
  standalone: true,
  imports: [MatDialogModule],
  templateUrl: './dialog-livro-criado.component.html',
  styleUrl: './dialog-livro-criado.component.scss'
})
export class DialogLivroCriadoComponent {
  public getData = signal<IBook| null>(null); 
  constructor( 
    private _dialogRef: MatDialogRef<DialogLivroCriadoComponent>,
    @Inject(MAT_DIALOG_DATA) private _data: IBook ){}

  ngOnInit(): void {
    this.getData.set(this._data)
  }

  public closeModal(){
    this._dialogRef.close()
  }
}
