// src/app/pages/article-details/article-details.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

import { Service, Article, Comment, Like } from '../../services/service.component';
import {AppComponent} from '../../app.component'; // import Comment si besoin

@Component({
  standalone: true,
  selector: 'app-article-details',
  templateUrl: './article-details.component.html',
  styleUrls: ['./article-details.component.css'],
  imports: [CommonModule, ReactiveFormsModule],
})
export class ArticleDetailsComponent implements OnInit {
  articleId: number | null = null;
  article: Article | null = null;
  comments: Comment[] = [];   // Tableau typé selon votre interface
  likes: Like[] = [];

  commentForm: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder,
    private service: Service
  ) {
    this.commentForm = this.formBuilder.group({
      content: ['', [Validators.required, Validators.minLength(3)]],
    });
  }

  ngOnInit(): void {
    // Récupération du paramètre d'URL
    this.articleId = Number(this.route.snapshot.paramMap.get('id'));

    // On récupère l'article depuis le backend
    if (this.articleId) {
      this.service.getArticleById(this.articleId).subscribe({
        next: (data) => {
          this.article = data;
          console.log('Article récupéré :', data);

          // Maintenant, on récupère les commentaires et les likes de cet article
          if (this.articleId) {
            this.loadComments(this.articleId);
            this.loadLikes(this.articleId);
          }
        },
        error: (err) => {
          console.error('Erreur récupération article :', err);
        },
      });
    }
  }

  loadComments(articleId: number): void {
    this.service.getCommentsByArticleId(articleId).subscribe({
      next: (data) => {
        this.comments = data;
        console.log('Commentaires récupérés :', data);
      },
      error: (err) => {
        console.error('Erreur récupération commentaires :', err);
      },
    });
  }

  onSubmitComment(): void {
    if (this.commentForm.valid && this.articleId) {
      // 1) Récupérer le contenu du commentaire
      const content = this.commentForm.value.content;
      const authorId = AppComponent.userID;
      this.service.createComment(content, authorId, this.articleId).subscribe({
        next: (createdComment) => {
          console.log('Commentaire créé :', createdComment);

          this.comments.push(createdComment);

          this.commentForm.reset();
        },
        error: (err) => {
          console.error('Erreur lors de la création du commentaire :', err);
        },
      });
    }
  }

  isAuthorObject(author: Comment['author']): author is { id: number; name: string; email: string } {
    // on vérifie si "author" est un objet et qu'il possède la propriété "name"
    return typeof author === 'object' && author !== null && 'name' in author;
  }

  loadLikes(articleId: number): void {
    this.service.getLikesByArticleId(articleId).subscribe({
      next: (data) => {
        this.likes = data;
        console.log('Likes récupérés :', data);
      },
      error: (err) => {
        console.error('Erreur récupération likes :', err);
      },
    });
  }

  likeArticle(): void {
    if (this.articleId) {
      const userId = AppComponent.userID;

      this.service.likeArticle(userId, this.articleId).subscribe({
        next: (newLike: Like) => {
          console.log('Nouveau like créé :', newLike);
          // On ajoute le nouveau Like dans le tableau local
          this.likes.push(newLike);
        },
        error: (err) => {
          console.error('Erreur lors de l’ajout du like :', err);
        }
      });
    }
  }
}
