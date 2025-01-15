// src/app/pages/create-article/create-article.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import {Router, RouterModule} from '@angular/router';
import {Service, User} from '../../services/service.component';
import {AppComponent} from '../../app.component';

@Component({
  standalone: true,
  selector: 'app-create-article',
  templateUrl: './create-article.component.html',
  styleUrls: ['./create-article.component.css'],
  imports: [CommonModule, ReactiveFormsModule, RouterModule]
})
export class CreateArticleComponent {
  articleForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private router: Router, private service: Service) {
    this.articleForm = this.formBuilder.group({
      title: ['', [Validators.required, Validators.minLength(3)]],
      content: ['', [Validators.required, Validators.minLength(10)]],

    });
  }

  onSubmit(): void {
    if (this.articleForm.valid) {
      const title = this.articleForm.value.title;
      const content = this.articleForm.value.content;
      const id = AppComponent.userID;
      this.service.createArticle(title, content, id).subscribe({
        next: (user: User) => {
          console.log('Connexion réussie, utilisateur récupéré :', user);

          // Redirection vers la page Home
          this.router.navigate(['/home']);
        },
        error: (err) => {
          console.error('Erreur lors de la connexion :', err);

        }
      });
    }
  }
}
