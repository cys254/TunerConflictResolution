import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class ConflictResolutionSet implements Comparable<ConflictResolutionSet> {

    private int priority;

    private List<Booking> bookings = new ArrayList<>();

    public ConflictResolutionSet(Booking booking) {
        bookings.add(booking);
        priority = booking.getPriority();
    }

    public ConflictResolutionSet(ConflictResolutionSet x) {
        this.priority = x.priority;
        this.bookings = new ArrayList<>(x.bookings);
    }

    public ConflictResolutionSet(List<Booking> bookings) {
        this.bookings = bookings;
        if (bookings != null && bookings.size() > 0) {
            priority = bookings.get(0).getPriority();
            for (int i = 1; i < bookings.size(); i++) {
                int bookingPriority = bookings.get(i).getPriority();
                if (bookingPriority > priority) {
                    priority = bookingPriority;
                }
            }
        }
    }

    public int getPriority() {
        return priority;
    }

    public boolean add(Booking booking) {
        if (booking.getPriority() > priority) {
            priority = booking.getPriority();
        }
        return bookings.add(booking);
    }

    @Override
    public int compareTo(ConflictResolutionSet o) {
        return priority - o.priority;
    }

    @Override
    public String toString() {
        String s = "priority=" + priority + "\n";
        for (Booking booking : bookings) {
            s += booking.toString() + "\n";
        }
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConflictResolutionSet that = (ConflictResolutionSet) o;
        return bookings.containsAll(that.bookings) && that.bookings.containsAll(bookings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookings);
    }
}
