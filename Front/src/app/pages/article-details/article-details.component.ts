// src/app/pages/article-details/article-details.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

import { Service, Article, Comment, Like } from '../../services/service.component'; // import Comment si besoin

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
    if (this.commentForm.valid) {
      // Exemple : vous envoyez un nouveau commentaire au backend
      const newComment = {
        content: this.commentForm.value.content,
        // Ajoutez d’autres champs si nécessaires (auteur, date, etc.)
      };

      console.log('Nouveau commentaire à envoyer :', newComment);

      // Si vous avez un endpoint du type POST /api/comments/{articleId}
      // vous pourriez faire quelque chose comme :
      //
      // this.service.addComment(this.articleId, newComment).subscribe({
      //   next: (savedComment) => {
      //     console.log('Commentaire créé :', savedComment);
      //     this.comments.push(savedComment);
      //     this.commentForm.reset();
      //   },
      //   error: (err) => {
      //     console.error('Erreur lors de la création du commentaire :', err);
      //   }
      // });

      // Pour l’instant, on simule juste l’ajout en local
      const commentLocal = {
        id: Math.random(), // ID fictif
        content: this.commentForm.value.content,
        publicationDate: new Date().toISOString(),
        author: 'MonUser'
      };
      this.comments.push(commentLocal);
      this.commentForm.reset();
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

  // likeArticle(): void {
  //   if (this.articleId) {
  //     // Supposons qu’on ait l’ID user dans une variable userId=1 (exemple)
  //     const userId = 1;
  //
  //     this.service.addLikeToArticle(this.articleId, userId).subscribe({
  //       next: (newLike) => {
  //         console.log('Nouveau like créé :', newLike);
  //         // Soit vous incrémentez localement:
  //         if (this.article) {
  //           this.article.jaime = (this.article.jaime || 0) + 1;
  //         }
  //       },
  //       error: (err) => {
  //         console.error('Erreur lors du like :', err);
  //       },
  //     });
  //   }
  // }

}
