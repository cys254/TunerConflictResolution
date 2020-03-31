import java.util.*;

public class TunerConflictResolver {

    private int numberOfTuners;
    private List<Booking> existingBookings;
    private Booking newBooking;
    private List<ConflictResolutionSet> resolutions;

    private List<BookingTimeInterval> bookingTimeIntervalList;

    public TunerConflictResolver(int numberOfTuners, List<Booking> existingBookings, Booking newBooking) {
        this.numberOfTuners = numberOfTuners;
        this.existingBookings = existingBookings;
        this.newBooking = newBooking;
    }

    public boolean hasConflict() {
        return resolutions.size() > 0;
    }

    public List<ConflictResolutionSet> getResolutions() {
        return resolutions;
    }

    public void printConflictResolutions() {
        System.out.println("Number of conflict resolutions: " + resolutions.size());
        for (ConflictResolutionSet resolution : resolutions) {
            System.out.println(resolution);
        }
    }

    /**
     * This function detects tuner conflicts and store conflict resolution sets in resolutions.
     * @return true if conflicts found, false otherwise
     */
    public boolean detectConflict() {
        if (resolutions != null) {
            return hasConflict();
        }
        bookingTimeIntervalList = new ArrayList<>();
        bookingTimeIntervalList.add(new BookingTimeInterval(newBooking.getStartTime(), newBooking.getEndTime()));
        for (Booking booking : existingBookings) {
            List<BookingTimeInterval> newIntervals = new ArrayList<>();
            for (BookingTimeInterval interval : bookingTimeIntervalList) {
                if (interval.getEnd() <= booking.getStartTime()) {
                    continue;
                }
                if (interval.getBegin() >= booking.getEndTime()) {
                    break;
                }
                newIntervals.addAll(interval.addBookingAndSplitInterval(booking));
            }
            bookingTimeIntervalList.addAll(newIntervals);
            Collections.sort(bookingTimeIntervalList);
        }
        resolutions = new ArrayList<>();
        for (int i = 0; i < bookingTimeIntervalList.size(); i++) {
            BookingTimeInterval interval = bookingTimeIntervalList.get(i);
            List<Booking> bookings = interval.getBookings();
            if (bookings.size() < numberOfTuners) {
                continue;
            }
            for (Booking booking: bookings) {
                ConflictResolutionSet resolution = new ConflictResolutionSet(booking);
                resolveConflictResolutionSet(resolution, i+1, interval.getBegin(), booking.getEndTime());
            }
            break;
        }
        Collections.sort(resolutions);
        return hasConflict();
    }

    private void resolveConflictResolutionSet(ConflictResolutionSet resolution, int i, long prevConflictBegin, long prevBookingEnd) {
        for (;i<bookingTimeIntervalList.size();i++) {
            BookingTimeInterval interval = bookingTimeIntervalList.get(i);
            List<Booking> bookings = interval.getBookings();
            if (bookings.size() < numberOfTuners) {
                continue;
            }
            if (interval.getEnd() <= prevBookingEnd) {
                continue;
            }
            for (Booking booking: bookings) {
                if (booking.getStartTime() <= prevConflictBegin) {
                    continue;
                }
                ConflictResolutionSet newResolution = new ConflictResolutionSet(resolution);
                newResolution.add(booking);
                resolveConflictResolutionSet(newResolution, i+1, interval.getBegin(), booking.getEndTime());
            }
            return;
        }
        resolutions.add(resolution);
    }
}
