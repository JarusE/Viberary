import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BookService, Book } from '../services/book.service';
import { BookingService } from '../services/booking.service';
import { Booking } from '../models/booking.model';
import { Router, NavigationEnd } from '@angular/router';
import { Subscription } from 'rxjs';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MatButtonModule
  ],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit, OnDestroy {
  books: Book[] = [];
  filteredBooks: Book[] = [];
  userBookings: number[] = [];

  searchTerm: string = '';
  sortOption: string = '';

  private routerSubscription?: Subscription;

  constructor(
    private bookService: BookService,
    private bookingService: BookingService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadUserBookings();

    this.routerSubscription = this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd && this.router.url === '/home') {
        this.loadUserBookings();
      }
    });
  }

  loadUserBookings(): void {
    this.bookingService.getMyBookings().subscribe({
      next: (bookings: Booking[]) => {
        this.userBookings = bookings
          .filter(b => b.status === 'PENDING' || b.status === 'ACTIVE')
          .map(b => b.bookId);
        this.loadBooks();
      },
      error: () => {
        this.userBookings = [];
        this.loadBooks();
      }
    });
  }

  loadBooks(): void {
    this.bookService.getAllBooks().subscribe({
      next: (data: Book[]) => {
        this.books = data.filter(book => !this.userBookings.includes(book.id));
        this.applyFilters();
      },
      error: (err: any) => console.error('Помилка завантаження книг', err)
    });
  }

  applyFilters(): void {
    let books = [...this.books];

    if (this.searchTerm) {
      const term = this.searchTerm.toLowerCase();
      books = books.filter(b => b.title.toLowerCase().includes(term));
    }

    switch (this.sortOption) {
      case 'genre':
        books.sort((a, b) => a.genre.localeCompare(b.genre));
        break;
      case 'title':
        books.sort((a, b) => a.title.localeCompare(b.title));
        break;
    }

    this.filteredBooks = books;
  }

  goToDetails(bookId: number): void {
    this.router.navigate(['/books', bookId]);
  }

  ngOnDestroy(): void {
    this.routerSubscription?.unsubscribe();
  }
}
