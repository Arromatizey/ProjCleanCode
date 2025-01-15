// src/app/pages/login/login.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { Service, User } from '../../services/service.component';
import { AppComponent } from '../../app.component';


@Component({
  standalone: true,
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  imports: [CommonModule, ReactiveFormsModule, RouterModule]
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private router: Router, private service: Service) {
    this.loginForm = this.formBuilder.group({
      userEmail: ['', [Validators.required, Validators.email]],
      userPassword: ['', [Validators.required]]
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const email = this.loginForm.value.userEmail;
      const password = this.loginForm.value.userPassword;

      this.service.loginUser(email, password).subscribe({
        next: (user: User) => {
          console.log('Connexion réussie, utilisateur récupéré :', user);

          AppComponent.userID = user.id;
          console.log('L\'ID EST : ', AppComponent.userID);

          // Redirection vers la page Home
          this.router.navigate(['/home']);
        },
        error: (err) => {
          console.error('Erreur lors de la connexion :', err);
          // Afficher un message d'erreur si besoin
        }
      });
    }
  }
}
