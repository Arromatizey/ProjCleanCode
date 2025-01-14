// src/app/pages/article-details/article-details.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';


@Component({
  standalone: true,
  selector: 'app-article-details',
  templateUrl: './article-details.component.html',
  styleUrls: ['./article-details.component.css'],
  imports: [CommonModule, ReactiveFormsModule]
})
export class ArticleDetailsComponent implements OnInit {
  articleId: number | null = null;
  article: any; // Dans la vraie appli, vous aurez un modèle / interface
  comments: any[] = [];

  commentForm: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private formBuilder: FormBuilder
  ) {
    this.commentForm = this.formBuilder.group({
      content: ['', [Validators.required, Validators.minLength(3)]]
    });
  }

  ngOnInit(): void {
    // Récupération du paramètre d'URL
    this.articleId = Number(this.route.snapshot.paramMap.get('id'));

    // Simuler la récupération de l'article
    this.article = {
      id: this.articleId,
      title: 'Exemple d’article',
      content: 'Contenu complet de l’article...',
      publicationDate: '2025-01-14'
    };

    // Simuler la récupération de commentaires
    this.comments = [
      { id: 1, content: 'Premier commentaire', author: 'Alice' },
      { id: 2, content: 'Super article !', author: 'Bob' }
      // ...
    ];

    // Dans la vraie vie : articleService.getArticleById(this.articleId).subscribe(...)
    //                 : commentService.getCommentsByArticleId(...)
  }

  onSubmitComment(): void {
    if (this.commentForm.valid) {
      const newComment = {
        id: Math.random(), // ID fictif
        content: this.commentForm.value.content,
        author: 'MonUser' // À ajuster selon votre auth
      };
      this.comments.push(newComment);

      // Reset du formulaire
      this.commentForm.reset();
      console.log('Nouveau commentaire ajouté:', newComment);

      // Dans la vraie vie : commentService.addComment(this.articleId, newComment).subscribe(...)
    }
  }
}
