import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule, formatDate } from '@angular/common';
import { BookService, Book } from '../services/book.service';
import { BookingService } from '../services/booking.service';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-book-detail',
  standalone: true,
  imports: [
    CommonModule,
    MatButtonModule
  ],
  templateUrl: './book-detail.component.html',
  styleUrls: ['./book-detail.component.scss']
})
export class BookDetailComponent implements OnInit {
  bookId!: number;
  book?: Book;
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private bookService: BookService,
    private bookingService: BookingService
  ) {}

  ngOnInit(): void {
    this.bookId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadBook();
  }

  loadBook(): void {
    this.bookService.getBookById(this.bookId).subscribe({
      next: (data) => this.book = data,
      error: (err) => {
        console.error('Книгу не знайдено:', err);
        this.errorMessage = 'Книгу не знайдено';
      }
    });
  }

  bookNow(): void {
    if (!this.book?.id) return;

    const today = new Date();
    const end = new Date();
    end.setDate(today.getDate() + 14);

    const reservation = {
      bookId: this.book.id,
      startDate: formatDate(today, 'yyyy-MM-dd', 'en'),
      endDate: formatDate(end, 'yyyy-MM-dd', 'en'),
    };

    this.bookingService.createReservation(reservation).subscribe({
      next: () => {
        this.loadBook();
        setTimeout(() => {
          alert('Заброньовано успішно');
          this.router.navigate(['/profile']);
        }, 500);
      },
      error: (err) => alert('Помилка бронювання: ' + err.message)
    });
  }
}
