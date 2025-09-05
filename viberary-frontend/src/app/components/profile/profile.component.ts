import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BookingService } from '../../services/booking.service';
import { Booking } from '../../models/booking.model';
import { BookService } from '../../services/book.service';
import { NgIf, NgFor } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { AdminService, AdminUser, AdminBooking } from '../../services/admin.service';
import { AuthService, UserProfile } from '../../modules/auth/services/auth.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    CommonModule,
    NgIf,
    NgFor,
    RouterModule,
    MatButtonModule,
    MatCardModule,
    MatDividerModule,
    FormsModule
  ],
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  activeBookings: Booking[] = [];
  historyBookings: Booking[] = [];

  isAdmin: boolean = false;
  adminUsers: AdminUser[] = [];
  selectedUser: AdminUser | null = null;
  selectedUserBookings: AdminBooking[] = [];

  sortField: keyof AdminUser = 'name';
  sortDirection: 'asc' | 'desc' = 'asc';

  constructor(
    private bookingService: BookingService,
    private bookService: BookService,
    private adminService: AdminService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const navigationEntries = performance.getEntriesByType('navigation') as PerformanceNavigationTiming[];
    const wasReloaded = navigationEntries.some(e => e.type === 'reload');

    if (!wasReloaded) {
      location.reload();
      return;
    }

    this.authService.getUserProfile().subscribe(profile => {
      console.log('Отримано профіль:', profile);
      this.isAdmin = profile?.role === 'ADMIN';
      console.log('isAdmin:', this.isAdmin);

      if (this.isAdmin) {
        this.loadAllUsers();
      } else {
        this.loadBookings();
      }
    });
  }


  // === Клієнт ===
  loadBookings(): void {
    this.bookingService.getMyBookings().subscribe({
      next: (bookings: Booking[]) => {
        this.activeBookings = bookings.filter(b => b.status === 'PENDING' || b.status === 'ACTIVE');
        this.historyBookings = bookings.filter(b => b.status === 'FINISHED' || b.status === 'CANCELLED');

        const all = [...this.activeBookings, ...this.historyBookings];
        all.forEach(booking => {
          if (!booking.bookTitle) {
            this.bookService.getBookById(booking.bookId).subscribe({
              next: book => {
                booking.bookTitle = book.title;
              },
              error: () => {
                booking.bookTitle = 'Невідома книга';
              }
            });
          }
        });
      },
      error: err => console.error('Не вдалося отримати бронювання:', err)
    });
  }

  markAsRead(id: number): void {
    this.bookingService.finishBooking(id).subscribe({
      next: () => this.loadBookings(),
      error: () => alert('Помилка завершення бронювання')
    });
  }

  cancelBooking(id: number): void {
    if (confirm('Ви впевнені, що хочете скасувати бронювання?')) {
      this.bookingService.cancelBooking(id).subscribe({
        next: () => this.loadBookings(),
        error: () => alert('Помилка скасування бронювання')
      });
    }
  }

  // === ADMIN ===
  loadAllUsers(): void {
    this.adminService.getAllUsers().subscribe({
      next: users => this.adminUsers = users,
      error: () => alert('Помилка завантаження користувачів')
    });
  }

  selectUser(user: AdminUser): void {
    this.selectedUser = user;
    this.adminService.getBookingsByUserId(user.id).subscribe({
      next: bookings => {
        this.selectedUserBookings = bookings;

        bookings.forEach(booking => {
          if (!(booking as any).bookTitle) {
            this.bookService.getBookById(booking.bookId).subscribe({
              next: book => {
                (booking as any).bookTitle = book.title;
              },
              error: () => {
                (booking as any).bookTitle = 'Невідома книга';
              }
            });
          }
        });
      },
      error: () => alert('Помилка завантаження бронювань')
    });
  }


  cancelBookingAsAdmin(id: number): void {
    if (confirm('Скасувати це бронювання?')) {
      this.adminService.cancelBooking(id).subscribe({
        next: () => {
          if (this.selectedUser) this.selectUser(this.selectedUser);
        },
        error: () => alert('Помилка скасування бронювання')
      });
    }
  }
  searchTerm: string = '';
  get filteredUsers(): AdminUser[] {
    return this.adminUsers
      .filter(user =>
        user.name.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        user.email.toLowerCase().includes(this.searchTerm.toLowerCase())
      )
      .sort((a, b) => {
        const valA = a[this.sortField].toString().toLowerCase();
        const valB = b[this.sortField].toString().toLowerCase();
        return (valA < valB ? -1 : valA > valB ? 1 : 0) * (this.sortDirection === 'asc' ? 1 : -1);
      });
  }


  isCancelable(status: string): boolean {
    return status === 'PENDING' || status === 'ACTIVE';
  }


  sortUsers(field: keyof AdminUser): void {
    if (this.sortField === field) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortField = field;
      this.sortDirection = 'asc';
    }

    this.adminUsers.sort((a, b) => {
      const valA = a[field].toString().toLowerCase();
      const valB = b[field].toString().toLowerCase();
      return (valA < valB ? -1 : valA > valB ? 1 : 0) * (this.sortDirection === 'asc' ? 1 : -1);
    });
  }
}
