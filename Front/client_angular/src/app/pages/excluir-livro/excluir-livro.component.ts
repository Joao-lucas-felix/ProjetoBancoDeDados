import { Component, DoCheck, inject, Input, OnInit, signal } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { L } from '@angular/cdk/keycodes';
import IBook from '../../interfaces/ibook';
import { DialogLivroCriadoComponent } from '../../components/dialog-livro-criado/dialog-livro-criado.component';
import { EDialogPainelClass } from '../../enum/EDialogPainelClasse.enum';

@Component({
  selector: 'app-excluir-livro',
  standalone: true,
  imports: [CommonModule, RouterLink, MatDialogModule],
  templateUrl: './excluir-livro.component.html',
  styleUrl: './excluir-livro.component.scss'
})
export class ExcluirLivroComponent implements OnInit, DoCheck {
  #router = inject(Router)
  private dialog = inject(MatDialog);
  ngDoCheck(): void {
    console.log(this.livro())
  }
  #apiService = inject(ApiService)
  livro = this.#apiService.getBookByID

  public getID = signal<string | null>(null)
  @Input() set id(id: string){  
    this.getID.set(id)
  }
  
  ngOnInit(): void {
    this.#apiService.httpBookByID$(this.getID()!).subscribe({
      next: next => console.log(next)
    }) 
  }

  public openDialog(data: IBook) {
    this.dialog.open(DialogLivroCriadoComponent, {
      data: data,
      panelClass: EDialogPainelClass.LIVRO
    });
  }
  delete(): void{
    this.#apiService.httpBookDelete$(String(this.livro()?.key)).subscribe({
      next:  (next) => this.#router.navigate(["../../../"])
    })

  }
}
