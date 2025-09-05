export interface Booking {
  id: number;
  bookId: number;
  bookTitle?: string;
  startDate: string;
  endDate: string;
  status: 'PENDING' | 'ACTIVE' | 'FINISHED' | 'CANCELLED';
}
