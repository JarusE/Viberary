import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { RouterModule, Router } from '@angular/router';

import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  name = '';
  email = '';
  password = '';
  confirmPassword = '';
  errorMessage: string | null = null;

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    this.errorMessage = null;

    if (this.password !== this.confirmPassword) {
      this.errorMessage = 'Паролі не співпадають';
      return;
    }

    this.authService.logout();

    this.authService.register({
      name: this.name,
      email: this.email,
      password: this.password
    }).subscribe({
      next: () => {
        this.router.navigate(['/login']);
      },
      error: (err) => {
        this.errorMessage = 'Не вдалося зареєструвати користувача';
        console.error('Помилка реєстрації:', err);
      }
    });
  }
}
