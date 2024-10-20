import { Component, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import INavLinks from '../../../interfaces/INavLinks.interface';
import { LoadBooksComponent } from "../../components/load-books/load-books.component";


@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterLink, LoadBooksComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {}
