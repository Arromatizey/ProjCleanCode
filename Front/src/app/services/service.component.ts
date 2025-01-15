import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

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
  };
}

export interface User {
  id: number;
  name: string;
  email: string;
  motDePasse: string;
}


@Injectable({
  providedIn: 'root',
})
export class Service {
  private apiUrlArticles = 'http://localhost:8080/api/articles';
  private apiUrlComments = 'http://localhost:8080/api/comments';
  private apiUrlLikes = 'http://localhost:8080/api/likes';
  private apiUrlUsers = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) {}

  getArticles(): Observable<Article[]> {
    return this.http.get<Article[]>(this.apiUrlArticles);
  }

  getArticleById(id: number): Observable<Article> {
    return this.http.get<Article>(`${this.apiUrlArticles}/${id}`);
  }

  getCommentsByArticleId(articleId: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.apiUrlComments}/${articleId}`);
  }

  getLikesByArticleId(articleId: number): Observable<Like[]> {
    return this.http.get<Like[]>(`${this.apiUrlLikes}/${articleId}`);
  }

  loginUser(email: string, password: string): Observable<User> {
    const url = `${this.apiUrlUsers}/login?email=${email}&password=${password}`;
    return this.http.post<User>(url, {});
  }

  signupUser(name: string, email: string, password: string): Observable<User>{
    const url = `${this.apiUrlUsers}/create-user?name=${name}&email=${email}&motDePasse=${password}`;
    return this.http.post<User>(url, {});
  }

  createArticle(title: string, content: string, id: number){
    const url = `${this.apiUrlArticles}/create-article?title=${title}&content=${content}&id=${id}`
    return this.http.post<User>(url, {});
  }

  createComment(content: string, authorId: number, articleId: number): Observable<Comment> {
    const url = `${this.apiUrlComments}/create-comment?content=${content}&authorId=${authorId}&articleId=${articleId}`;
    return this.http.post<Comment>(url, {});
  }

  likeArticle(userId: number, articleId: number): Observable<Like> {
    const url = `${this.apiUrlLikes}/create-jaime?userId=${userId}&articleId=${articleId}`;
    return this.http.post<Like>(url, {});
  }

}
