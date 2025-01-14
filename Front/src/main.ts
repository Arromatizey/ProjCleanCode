// src/main.ts
import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import { AppRouting } from './app/app.routes';

bootstrapApplication(AppComponent, {
  providers: [...AppRouting]
}).catch(err => console.error(err));
