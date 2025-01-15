// src/app/pages/create-article/create-article.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-create-article',
  templateUrl: './create-article.component.html',
  styleUrls: ['./create-article.component.css'],
  imports: [CommonModule, ReactiveFormsModule, RouterModule]
})
export class CreateArticleComponent {
  articleForm: FormGroup;

  constructor(private formBuilder: FormBuilder) {
    this.articleForm = this.formBuilder.group({
      title: ['', [Validators.required, Validators.minLength(3)]],
      content: ['', [Validators.required, Validators.minLength(10)]],

    });
  }

  onSubmit(): void {
    if (this.articleForm.valid) {
      console.log('Article data:', this.articleForm.value);
      // Appel à un service (ex: articleService.createArticle(...))
      // Redirection si nécessaire : router.navigate(['/home']), etc.
    }
  }
}
