import { Component, inject, Input, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { ApiService } from '../../services/api.service';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { IUsuarios } from '../../interfaces/iusuarios';
import { UserEditedDialogComponent } from '../../components/user-edited-dialog/user-edited-dialog.component';
import { EDialogPainelClass } from '../../enum/EDialogPainelClasse.enum';

@Component({
  selector: 'app-edit-usuario',
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink],
  templateUrl: './edit-usuario.component.html',
  styleUrl: './edit-usuario.component.scss'
})
export class EditUsuarioComponent {

  #router = inject(Router)
  #apiService = inject(ApiService)

  public getID = signal<string | null>(null)
  @Input() set id(id: string){  
    this.getID.set(id)
  }
  
  ngOnInit(): void {
    this.#apiService.httpGetUserByID$(this.getID()!).subscribe({
      next: (user) => {
        this.userForm.patchValue({
          nome: user.nome,
          cpf: user.cpf,
          end: user.end,
        });
      },
      error: (err) => {
        console.error('Erro ao carregar o Usuario:', err);
      }
    });
  }
  


  #fb = inject(FormBuilder);
  private dialog = inject(MatDialog);

  public userForm = this.#fb.group({
    nome: ["", [Validators.maxLength(100), Validators.minLength(5), Validators.required]],
    cpf: ["", [Validators.minLength(5), Validators.maxLength(1000), Validators.required]],
    end: ["", Validators.required],
  });

  public isSubmitting = false;

  public onSubmit() {
    if (this.userForm.valid && !this.isSubmitting) {
      this.isSubmitting = true;
      this.submitUser({
        key: this.getID()!,
        nome: this.userForm.value.nome,
        cpf: this.userForm.value.cpf,
        end: this.userForm.value.end,
      });
    }
  }

  public submitUser(user: {
    key: string,
    nome: string | null | undefined,
    cpf: string | null | undefined,
    end: string | null | undefined,
  }) {
    return this.#apiService.htppUpdateUser$(user).subscribe({
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

  public openDialog(data: IUsuarios) {
    this.dialog.open(UserEditedDialogComponent, {
      data: data,
      panelClass: EDialogPainelClass.USUARIO
    });
  }
}
