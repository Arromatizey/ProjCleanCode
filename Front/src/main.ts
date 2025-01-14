// src/main.ts
import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import { AppRouting } from './app/app.routes';

bootstrapApplication(AppComponent, {
  providers: [...AppRouting]
}).catch(err => console.error(err));
