import { Component, signal } from '@angular/core';
import INavLinks from '../../../interfaces/INavLinks.interface';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-clientes',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './clientes.component.html',
  styleUrl: './clientes.component.scss'
})
export class ClientesComponent {
  //Links de navegação dessa pagina
  linksNavigation = signal<INavLinks[]>([
    { link: "", PageName: "Home"},
  ])
}
