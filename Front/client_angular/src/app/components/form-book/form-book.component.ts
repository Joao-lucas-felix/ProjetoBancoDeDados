import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ApiService } from '../../services/api.service';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import IBook from '../../interfaces/ibook';
import { DialogLivroCriadoComponent } from '../dialog-livro-criado/dialog-livro-criado.component';
import { EDialogPainelClass } from '../../enum/EDialogPainelClasse.enum';
import { ErrorDialogComponent } from '../error-dialog/error-dialog.component';

@Component({
  selector: 'app-form-book',
  standalone: true,
  imports: [MatDialogModule, ReactiveFormsModule],
  templateUrl: './form-book.component.html',
  styleUrl: './form-book.component.scss'
})
export class FormBookComponent {

  // Injeção de dependência
  #fb = inject(FormBuilder);
  #apiService = inject(ApiService);
  private dialog = inject(MatDialog);
 

  public bookForm = this.#fb.group({
    titulo: ['',Validators.required ],
    description: ['',Validators.required ],
    preco: ['', Validators.required],
    dataLancamento: ['', Validators.required],
    qtdEstoque: ['',Validators.required],
    nomeEditora: ['',Validators.required],
    autores: ['',Validators.required]
  });


  public isSubmitting = false;

  public onSubmit() {
    if (this.bookForm.valid && !this.isSubmitting) {
      this.isSubmitting = true;
      this.createBook({
        titulo: this.bookForm.value.titulo,
        description: this.bookForm.value.description,
        preco: this.bookForm.value.preco,
        dataLancamento: this.bookForm.value.dataLancamento,
        qtdEstoque: this.bookForm.value.qtdEstoque,
        nomeEditora: this.bookForm.value.nomeEditora,
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
    titulo: string | null | undefined,
    description: string | null | undefined,
    preco: number | string | null | undefined,
    dataLancamento: string | null | undefined,
    qtdEstoque: string | number | null | undefined,
    nomeEditora: string | null | undefined,
    autores: Array<string> | undefined
  }) {

    return this.#apiService.httpBookCreate$(book).subscribe({
      next: (next) => {
        this.openDialog(next);
        this.isSubmitting = false; // Reset the flag after the request completes

      },
      error: (error) => {
        this.openErrorDialog(error.error)
        this.isSubmitting = false; // Reset the flag in case of error
      }, 
      complete: () => {} 
    });

  }
  openErrorDialog(error: any) {
    this.dialog.open(ErrorDialogComponent, {
      data: error,
      panelClass: EDialogPainelClass.ERROR
    });
  }


}
