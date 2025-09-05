import { Routes } from '@angular/router';
import { RegisterComponent } from './modules/auth/pages/register/register.component';
import { LoginComponent } from './modules/auth/pages/login/login.component';
import { HomeComponent } from './pages/home.component';
import { ProfileComponent } from './components/profile/profile.component';


export const routes: Routes = [
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent },
  { path: 'profile', component: ProfileComponent },
  {
    path: 'books/:id',
    loadComponent: () =>
      import('./pages/book-detail.component').then(m => m.BookDetailComponent)
  },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];
