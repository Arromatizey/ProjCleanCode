// src/app/app.config.ts
import { provideRouter, RouterModule, Routes } from '@angular/router';
import { importProvidersFrom } from '@angular/core';

import { HomeComponent } from './pages/home/home.component';
import { CreateArticleComponent } from './pages/create-article/create-article.component';
import { ArticleDetailsComponent } from './pages/article-details/article-details.component';
import { SignupComponent } from './pages/signup/signup.component';
import { LoginComponent } from './pages/login/login.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'create-article', component: CreateArticleComponent },
  { path: 'article/:id', component: ArticleDetailsComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'login', component: LoginComponent },
  // Autres routes si besoin...
];

export const AppRouting = [
  provideRouter(routes),
  importProvidersFrom(RouterModule)
];
