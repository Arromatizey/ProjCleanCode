// src/app/pages/home/home.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Service, Article } from '../../services/service.component'; // ajustez le chemin
// si vous renommez
@Component({
  standalone: true,
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  imports: [CommonModule, RouterModule],
})
export class HomeComponent implements OnInit {
  // On stocke ici les articles récupérés
  articles: Article[] = [];

  searchQuery = '';

  constructor(private service: Service) {}

  ngOnInit(): void {
    // Au chargement du composant, on récupère la liste des articles depuis le backend
    this.service.getArticles().subscribe({
      next: (data) => {
        this.articles = data;
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des articles :', err);
      },
    });
  }

  // Filtre la liste des articles par titre
  get filteredArticles() {
    const queryLower = this.searchQuery.toLowerCase();
    return this.articles.filter((article) =>
      article.title.toLowerCase().includes(queryLower)
    );
  }

  onSearchChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.searchQuery = input.value;
  }
}
