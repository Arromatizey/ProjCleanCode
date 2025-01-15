// src/app/pages/home/home.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Service, Article } from '../../services/service.component';
@Component({
  standalone: true,
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  imports: [CommonModule, RouterModule],
})
export class HomeComponent implements OnInit {
  articles: Article[] = [];

  searchQuery = '';

  constructor(private service: Service) {}

  ngOnInit(): void {
    this.service.getArticles().subscribe({
      next: (data) => {
        this.articles = data;
        this.articles.forEach((article) => {
          this.service.getLikesByArticleId(article.id).subscribe({
            next: (likes) => {
              article.likesCount = likes.length;
            },
            error: (err) => {
              console.error('Erreur lors de la récupération des likes de l’article', article.id, err);
            }
          });
        });
      },
      error: (err) => {
        console.error('Erreur lors de la récupération des articles :', err);
      }
    });
  }

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
