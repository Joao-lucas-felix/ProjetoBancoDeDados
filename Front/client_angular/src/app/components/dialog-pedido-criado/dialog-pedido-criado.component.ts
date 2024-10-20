import { Component, Inject, signal } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogModule, MatDialogRef } from '@angular/material/dialog';
import { IPedido } from '../../interfaces/ipedido';
import { DialogLivroCriadoComponent } from '../dialog-livro-criado/dialog-livro-criado.component';
import { IPedidoFeito } from '../../interfaces/ipedido-feito';

@Component({
  selector: 'app-dialog-pedido-criado',
  standalone: true,
  imports: [MatDialogModule],
  templateUrl: './dialog-pedido-criado.component.html',
  styleUrl: './dialog-pedido-criado.component.scss'
})
export class DialogPedidoCriadoComponent {
  public getData = signal<IPedidoFeito| null>(null); 
  constructor( 
    private _dialogRef: MatDialogRef<DialogLivroCriadoComponent>,
    @Inject(MAT_DIALOG_DATA) private _data: IPedidoFeito ){}

  ngOnInit(): void {
    this.getData.set(this._data)
  }

  public closeModal(){
    this._dialogRef.close()
  }
}
