import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { Booking } from '../models/booking.model';
import { BookingRequest } from '../models/booking-request.model';

@Injectable({ providedIn: 'root' })
export class BookingService {
  private apiUrl = '/api/bookings';

  constructor(private http: HttpClient) {}

  getMyBookings(): Observable<Booking[]> {
    return this.http.get<Booking[]>(`${this.apiUrl}/my`);
  }


  finishBooking(id: number): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/${id}/finish`, {});
  }

  cancelBooking(id: number): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/${id}/cancel`, {});
  }

  createReservation(reservation: BookingRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}`, reservation, {
      withCredentials: false,
    }).pipe(
      catchError((error) => {
        if (error.status === 400) {
          const message = (typeof error.error === 'string') ? error.error : '';
          if (message.includes('Ви вже забронювали цю книгу')) {
            alert('Ви вже читаєте цю книгу');
          } else if (message.includes('На вибраний період немає доступних копій')) {
            alert('Книга вже зарезервована на вибраний період');
          } else {
            alert('Помилка: ' + message);
          }
        } else {
          alert('Невідома помилка при бронюванні');
        }
        return throwError(() => error);
      })
    );

  }
}
