// src/app/pages/signup/signup.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import {Router, RouterModule} from '@angular/router';
import {Service, User} from '../../services/service.component';
import {AppComponent} from '../../app.component';

@Component({
  standalone: true,
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
  imports: [CommonModule, ReactiveFormsModule, RouterModule]
})
export class SignupComponent {
  signupForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private router: Router, private service: Service) {
    this.signupForm = this.formBuilder.group({
      userName: ['', [Validators.required, Validators.minLength(3)]],
      userEmail: ['', [Validators.required, Validators.email]],
      userPassword: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  onSubmit(): void {
    if (this.signupForm.valid) {
      console.log('Form values:', this.signupForm.value);
      const name = this.signupForm.value.userName;
      const email = this.signupForm.value.userEmail;
      const password = this.signupForm.value.userPassword;
      this.service.signupUser(name, email, password).subscribe({
        next: (user: User) => {
          console.log('Connexion réussie, utilisateur récupéré :', user);
          AppComponent.userID = user.id;
          console.log('L\'ID EST : ', AppComponent.userID);
          this.router.navigate(['/home']);
        },
        error: (err) => {
          console.error('Erreur lors de la connexion :', err);
        }
      });
    }
  }
}
