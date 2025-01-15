import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Exemple d'interface pour un Article
export interface Article {
  id: number;
  title: string;
  content: string;
  publicationDate: string;
  likesCount?: number;
}

export interface Comment {
  id: number;
  content: string;
  publicationDate: string;
  author?: string | {
    id: number;
    name: string;
    email: string;
  };
  article?: {
    id: number;
    title: string;
  };
}

export interface Like {
  id: number;
  user: {
    id: number;
    name: string;
    email: string;
  };
  article: {
    id: number;
    title: string;
    content: string;
    publicationDate: string;
    // etc.
  };
}


@Injectable({
  providedIn: 'root',
})
export class Service {
  private apiUrlArticles = 'http://localhost:8080/api/articles';
  private apiUrlComments = 'http://localhost:8080/api/comments';
  private apiUrlLikes = 'http://localhost:8080/api/likes';

  constructor(private http: HttpClient) {}

  // Récupérer la liste de tous les articles
  getArticles(): Observable<Article[]> {
    return this.http.get<Article[]>(this.apiUrlArticles);
  }

  // Récupérer UN article par son ID
  getArticleById(id: number): Observable<Article> {
    return this.http.get<Article>(`${this.apiUrlArticles}/${id}`);
  }

  // Récupérer TOUS les commentaires pour un article donné
  getCommentsByArticleId(articleId: number): Observable<Comment[]> {
    // L’API attend /api/comments/{articleId}, qui retourne un tableau
    return this.http.get<Comment[]>(`${this.apiUrlComments}/${articleId}`);
  }

  // Récupérer TOUS les likes pour un article donné
  getLikesByArticleId(articleId: number): Observable<Like[]> {
    return this.http.get<Like[]>(`${this.apiUrlLikes}/${articleId}`);
  }

  // Méthode pour ajouter un like (POST)
  // addLikeToArticle(articleId: number, userId: number): Observable<Like> {
  //   // Selon votre backend, ça peut être /api/likes/{articleId}/{userId} ou un body JSON
  //   return this.http.post<Like>(`${this.apiUrlLikes}/${articleId}`, { userId });
  // }
}
