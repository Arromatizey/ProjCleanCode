// src/app/pages/article-details/article-details.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

import { Service, Article } from '../../services/service.component';

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
  comments: any[] = [];

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
          // Ici, vous pourrez appeler un service pour récupérer les commentaires liés
          // ex. this.commentService.getCommentsByArticleId(this.articleId)...
        },
        error: (err) => {
          console.error('Erreur récupération article :', err);
        },
      });
    }

    // Exemples de commentaires existants en dur :
    this.comments = [
      { id: 1, content: 'Premier commentaire', author: 'Alice' },
      { id: 2, content: 'Super article !', author: 'Bob' },
      // ...
    ];
  }

  onSubmitComment(): void {
    if (this.commentForm.valid) {
      const newComment = {
        id: Math.random(), // ID fictif
        content: this.commentForm.value.content,
        author: 'MonUser', // À ajuster selon votre auth
      };
      this.comments.push(newComment);
      this.commentForm.reset();
      console.log('Nouveau commentaire ajouté:', newComment);

      // Dans la vraie vie : commentService.addComment(this.articleId, newComment).subscribe(...)
    }
  }
}
