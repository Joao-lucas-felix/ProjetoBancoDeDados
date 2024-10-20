import { Component, inject, Input, signal,OnInit } from '@angular/core';
import {  Router, RouterLink } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { DialogLivroCriadoComponent } from '../../components/dialog-livro-criado/dialog-livro-criado.component';
import IBook from '../../interfaces/ibook';
import { EDialogPainelClass } from '../../enum/EDialogPainelClasse.enum';

@Component({
  selector: 'app-edit-book',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './edit-book.component.html',
  styleUrl: './edit-book.component.scss'
})
export class EditBookComponent implements OnInit {

  #router = inject(Router)
  #apiService = inject(ApiService)
  livro = this.#apiService.getBookByID

  nomeAutores = this.livro()?.autores?.join(",")!
  public getID = signal<string | null>(null)
  @Input() set id(id: string){  
    this.getID.set(id)
  }
  
  ngOnInit(): void {
    this.#apiService.httpBookByID$(this.getID()!).subscribe({
      next: (livro) => {
        this.bookForm.patchValue({
          titulo: livro.titulo,
          description: livro.description,
          preco: livro.preco,
          dataLancamento: livro.dataLancamento,
          qtdEstoque: livro.qtdEstoque,
          nomeEditora: livro.nomeEditora,
          autores: livro.autores?.join(", ")
        });
      },
      error: (err) => {
        console.error('Erro ao carregar o livro:', err);
      }
    });
  }
  


  #fb = inject(FormBuilder);
  private dialog = inject(MatDialog);

  public bookForm = this.#fb.group({
    titulo: ['', [Validators.maxLength(100), Validators.minLength(5), Validators.required]],
    description: [this.livro()?.description, [Validators.minLength(5), Validators.maxLength(1000), Validators.required]],
    preco: [this.livro()?.preco, Validators.required],
    dataLancamento: [this.livro()?.dataLancamento, Validators.required],
    qtdEstoque: [this.livro()?.qtdEstoque, [Validators.min(0), Validators.required]],
    nomeEditora: [this.livro()?.nomeEditora, [Validators.maxLength(100), Validators.minLength(5), Validators.required]],
    autores: ['', [Validators.maxLength(100), Validators.minLength(0), Validators.required]]
  });

  public isSubmitting = false;

  public onSubmit() {
    if (this.bookForm.valid && !this.isSubmitting) {
      this.isSubmitting = true;
      this.createBook({
        key: String(this.livro()?.key),
        titulo: this.bookForm.value.titulo,
        description: this.bookForm.value.description,
        preco: this.bookForm.value.preco,
        dataLancamento: this.bookForm.value.dataLancamento,
        qtdEstoque: this.bookForm.value.qtdEstoque,
        nomeEditora: String(this.bookForm.value.nomeEditora),
        autores: this.bookForm.value.autores?.split(",").map(str => str.trim())
      });
    }
  }

  public openDialog(data: IBook) {
    this.dialog.open(DialogLivroCriadoComponent, {
      data: data,
      panelClass: EDialogPainelClass.LIVRO
    });
  }

  public createBook(book: {
    key: string,
    titulo: string | null | undefined,
    description: string | null | undefined,
    preco: number | string | null | undefined,
    dataLancamento: string | null | undefined,
    qtdEstoque: string | number | null | undefined,
    nomeEditora: string | null | undefined,
    autores: Array<string> | undefined
  }) {
    return this.#apiService.httpBookUpdate$(book).subscribe({
      next: (next) => {
        this.openDialog(next);
        this.isSubmitting = false;
      },
      error: () => {
        this.isSubmitting = false; // Reset the flag in case of error
      },
      complete: () => {
        setTimeout(() => {this.#router.navigate(["../../"])}, 3000) 
      }
    });
  }

}
