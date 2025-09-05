import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {
  Observable,
  of,
  tap,
  catchError,
  BehaviorSubject
} from 'rxjs';

export interface RegisterRequest {
  name: string;
  email: string;
  password: string;
}

export interface AuthRequest {
  email: string;
  password: string;
}

export interface AuthResponse {
  token?: string;
  access_token?: string;
}

export interface UserProfile {
  id: number;
  name: string;
  email: string;
  role: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:9000/api/auth';
  private profileUrl = 'http://localhost:8080/api/users/me';

  private authStatus = new BehaviorSubject<UserProfile | null>(null);

  constructor(private http: HttpClient) {}

  register(data: RegisterRequest): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, data);
  }

  login(data: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.baseUrl}/login`, data).pipe(
      tap(res => {
        const token = res.token || res.access_token;
        if (typeof token === 'string') {
          localStorage.setItem('token', token);
          this.getUserProfile().subscribe();
        }
      })
    );
  }

  logout(): void {
    localStorage.removeItem('token');
    this.authStatus.next(null);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getUserProfile(): Observable<UserProfile | null> {
    const token = this.getToken();
    if (!token) return of(null);

    return this.http.get<UserProfile>(this.profileUrl, {
      headers: { Authorization: `Bearer ${token}` }
    }).pipe(
      tap(user => {
        console.log('Отримано профіль користувача:', user);
        this.authStatus.next(user);
      }),
      catchError(err => {
        console.warn('Не вдалося завантажити профіль:', err);
        this.authStatus.next(null);
        return of(null);
      })
    );
  }


  observeAuthStatus(): Observable<UserProfile | null> {
    return this.authStatus.asObservable();
  }

  getUserProfileSnapshot(): UserProfile | null {
    return this.authStatus.getValue();
  }

}
