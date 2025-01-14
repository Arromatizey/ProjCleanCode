import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Exemple d'interface pour un Article
export interface Article {
  id: number;
  title: string;
  content: string;
  publicationDate: string;
  // Ajoutez d'autres champs si besoin (author, etc.)
}

@Injectable({
  providedIn: 'root',
})
export class Service {
  private apiUrl = 'http://localhost:8080/api/articles';

  constructor(private http: HttpClient) {}

  // Récupérer la liste de tous les articles
  getArticles(): Observable<Article[]> {
    return this.http.get<Article[]>(this.apiUrl);
  }

  // Récupérer UN article par son ID
  getArticleById(id: number): Observable<Article> {
    return this.http.get<Article>(`${this.apiUrl}/${id}`);
  }
}
