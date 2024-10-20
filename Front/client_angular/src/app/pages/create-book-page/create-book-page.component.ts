import { Component, inject, OnInit, signal } from '@angular/core';
import INavLinks from '../../../interfaces/INavLinks.interface';
import { RouterLink } from '@angular/router';
import { FormBookComponent } from "../../components/form-book/form-book.component";
import { ApiService } from '../../services/api.service';
import IBook from '../../interfaces/ibook';

@Component({
  selector: 'app-create-book-page',
  standalone: true,
  imports: [RouterLink, FormBookComponent],
  templateUrl: './create-book-page.component.html',
  styleUrl: './create-book-page.component.scss'
})
export class CreateBookPageComponent{


  linksNavigation = signal<INavLinks[]>([
    { link: "", PageName: "Home"},
    { link: "../", PageName: "Books Page"},
  ])

}
