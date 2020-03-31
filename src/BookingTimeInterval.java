import java.util.ArrayList;
import java.util.List;

public class BookingTimeInterval implements Comparable<BookingTimeInterval> {
    private long begin;
    private long end;
    List<Booking> bookings = new ArrayList<>();

    BookingTimeInterval(long begin, long end) {
        this.begin = begin;
        this.end = end;
    }

    BookingTimeInterval(Booking booking) {
        this.begin = booking.getStartTime();
        this.end = booking.getEndTime();
    }

    public long getBegin() {
        return begin;
    }

    public long getEnd() {
        return end;
    }

    List<Booking> getBookings() {
        return bookings;
    }

    public List<BookingTimeInterval> addBookingAndSplitInterval(Booking booking) {
        List<BookingTimeInterval> newIntervals = new ArrayList<>();
        if (booking.getStartTime() < end && booking.getEndTime() >= begin) {
            if (booking.getStartTime() > begin) {
                BookingTimeInterval leftInterval = new BookingTimeInterval(begin, booking.getStartTime());
                leftInterval.bookings.addAll(bookings);
                begin = booking.getStartTime();
                newIntervals.add(leftInterval);
            }
            if (booking.getEndTime() < end) {
                BookingTimeInterval rightInterval = new BookingTimeInterval(booking.getEndTime(), end);
                rightInterval.bookings.addAll(bookings);
                end = booking.getEndTime();
                newIntervals.add(rightInterval);
            }
            bookings.add(booking);
        }
        return newIntervals;
    }

    @Override
    public int compareTo(BookingTimeInterval o) {
        return Long.signum(begin - o.end);
    }
}
