import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface AdminUser {
  id: number;
  name: string;
  email: string;
  role: string;
}

export interface AdminBooking {
  id: number;
  bookId: number;
  startDate: string;
  endDate: string;
  status: 'PENDING' | 'ACTIVE' | 'CANCELLED' | 'FINISHED';
  bookTitle?: string;
}

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private userApiUrl = 'http://localhost:8080/api/users/admin';
  private bookingApiUrl = 'http://localhost:8080/api/bookings/admin';

  constructor(private http: HttpClient) {}

  getAllUsers(): Observable<AdminUser[]> {
    return this.http.get<AdminUser[]>(`${this.userApiUrl}/all`);
  }

  getBookingsByUserId(userId: number): Observable<AdminBooking[]> {
    return this.http.get<AdminBooking[]>(`${this.bookingApiUrl}/user/${userId}`);
  }

  cancelBooking(bookingId: number): Observable<void> {
    return this.http.put<void>(`${this.bookingApiUrl}/${bookingId}/cancel`, {});
  }
}
