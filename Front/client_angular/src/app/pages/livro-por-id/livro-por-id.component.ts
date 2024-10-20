import { Component, DoCheck, inject, Input, input, OnInit, signal } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';

@Component({
  selector: 'app-livro-por-id',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './livro-por-id.component.html',
  styleUrl: './livro-por-id.component.scss'
})
export class LivroPorIdComponent implements OnInit, DoCheck{

  #route = inject(ActivatedRoute)

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


}
