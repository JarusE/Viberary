import { Component, OnInit, OnDestroy } from '@angular/core';
import { RouterModule, Router } from '@angular/router';
import { AuthService, UserProfile } from './modules/auth/services/auth.service';
import { CommonModule } from '@angular/common';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, OnDestroy {
  isAuthenticated = false;
  userName: string | null = null;

  private destroy$ = new Subject<void>();

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.authService.observeAuthStatus()
      .pipe(takeUntil(this.destroy$))
      .subscribe((user: UserProfile | null) => {
        this.isAuthenticated = !!user;
        this.userName = user?.name || null;
        console.log('Стан авторизації оновлено:', this.isAuthenticated, this.userName);
      });


    const token = this.authService.getToken();
    if (token) {
      this.authService.getUserProfile().subscribe();
    }
  }

  onProfileOrLoginClick(): void {
    if (this.isAuthenticated) {
      this.router.navigate(['/profile']);
    } else {
      this.router.navigate(['/login']);
    }
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
