// src/app/pages/home/home.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  imports: [CommonModule, RouterModule]
})
export class HomeComponent {
  articles = [
    { id: 1, title: 'Mon premier article', content: 'Lorem ipsum dolor sit amet...' },
    { id: 2, title: 'Angular Tips', content: 'Découvrez comment utiliser Angular...' },
    { id: 3, title: 'Propreté du code', content: 'Quelques bonnes pratiques...' },
    // ... vous pourrez remplacer par des données réelles ou un service
  ];

  searchQuery = '';

  get filteredArticles() {
    const queryLower = this.searchQuery.toLowerCase();
    return this.articles.filter(article =>
      article.title.toLowerCase().includes(queryLower)
    );
  }

  onSearchChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.searchQuery = input.value;
  }
}
