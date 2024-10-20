import { Component, inject, Input, signal } from '@angular/core';
import INavLinks from '../../../interfaces/INavLinks.interface';
import { Router, RouterLink } from '@angular/router';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { ApiService } from '../../services/api.service';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { IPedidoFeito } from '../../interfaces/ipedido-feito';
import { DialogPedidoCriadoComponent } from '../../components/dialog-pedido-criado/dialog-pedido-criado.component';
import { EDialogPainelClass } from '../../enum/EDialogPainelClasse.enum';
import { ErrorDialogComponent } from '../../components/error-dialog/error-dialog.component';


@Component({
  selector: 'app-pedidos',
  standalone: true,
  imports: [RouterLink, MatDialogModule, ReactiveFormsModule],
  templateUrl: './pedidos.component.html',
  styleUrl: './pedidos.component.scss'
})
export class PedidosComponent {
  linksNavigation = signal<INavLinks[]>([
    { link: "../../", PageName: "Voltar"},
  ])

  public cpfIsInvalid = signal<boolean>(false)
  #router = inject(Router)
  private dialog = inject(MatDialog);
  private cpf = signal<string>("")
  #apiService = inject(ApiService)

  public getID = signal<string | null>(null)
  @Input() set id(id: string){  
    this.getID.set(id)
  }



  #fb = inject(FormBuilder);


  
  public pedidoForm = this.#fb.group({
      userName: ['', [Validators.maxLength(100), Validators.minLength(5), Validators.required]],

      // XXX.XXX.XXX-XX
      cpf: ['', [Validators.maxLength(14), Validators.minLength(11), Validators.required]],
      end: ['', [Validators.maxLength(100), Validators.minLength(5), Validators.required]],
      quantidade: ['', [Validators.min(1), Validators.required]],
      funcionarioName: ['',[Validators.maxLength(100), Validators.minLength(5), Validators.required]],
      data: ['', Validators.required],
  })

  public isSubmitting = false;

  public onSubmit() { 
    this.cpf.set(this.formatString(this.pedidoForm.value.cpf!))

    if (this.cpf() === "Formato inválido"){
      console.log("formato do cpf invalido! ")
      this.cpfIsInvalid.set(true)
    }else{
      if (this.pedidoForm.valid && !this.isSubmitting) {
        this.isSubmitting = true;
        
        this.createPedido({
          idLivro: this.getID()!,
          userName: this.pedidoForm.value.userName,
          cpf: this.cpf(),
          end: this.pedidoForm.value.end,
          quantidade: this.pedidoForm.value.quantidade,
          funcionarioName: this.pedidoForm.value.funcionarioName,
          data: this.pedidoForm.value.data
        });
      }
    }


  }

  public openDialog(data: IPedidoFeito) {
    this.dialog.open(DialogPedidoCriadoComponent, {
      data: data,
      panelClass: EDialogPainelClass.PEDIDO
    });
  }


  public createPedido(pedido: {
    idLivro: string | null | undefined, 
    userName: string | null | undefined,
    cpf: string | null | undefined,
    end: string | null | undefined,
    quantidade: number | string | null | undefined,
    funcionarioName: string | null | undefined,
    data: string | null | undefined
  }) {
    console.log(pedido)
    this.#apiService.httpPedidoCreate$(pedido).subscribe({
      next: (next) => {
        this.openDialog(next);
        this.isSubmitting = false;
      },
      error: (error) => {
        this.openErrorDialog(error.error)
        this.isSubmitting = false; // Reset the flag in case of error
      },
      complete: () => {
        setTimeout(() => {this.#router.navigate(["../../"])}, 3000) 
      }
    })

  }
  openErrorDialog(error: any) {
    this.dialog.open(ErrorDialogComponent, {
      data: error,
      panelClass: EDialogPainelClass.ERROR
    });
  }

  formatString(input: string): string {
    if (input.length === 11 && /^\d{11}$/.test(input)) {
      return input.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
    } else if (input.length === 14 && /^\d{3}\.\d{3}\.\d{3}-\d{2}$/.test(input)) {
      return input;
    } else {
      return "Formato inválido";
    }
  }  

}
