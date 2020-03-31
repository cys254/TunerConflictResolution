import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String []argv) {
        Booking booking1 = new Booking(0, 3, 0);
        Booking booking2 = new Booking(2, 5, 0);
        Booking booking3 = new Booking(4, 7, 0);
        Booking booking4 = new Booking(6, 9, 0);
        List<Booking> existingBookings = Arrays.asList(booking1, booking2, booking3, booking4);
        Booking newBooking = new Booking(0, 9, 0);

        TunerConflictResolver resolver = new TunerConflictResolver(2, existingBookings, newBooking);
        resolver.detectConflict();
        resolver.printConflictResolutions();
    }
}
