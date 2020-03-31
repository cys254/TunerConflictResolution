import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TunerConflictResolverTest {

    @org.junit.jupiter.api.Test
    void test001_one_tuner_one_booking() {
        List<Booking> existingBookings = new ArrayList<>();
        Booking newBooking = new Booking(0, 1, 0);

        TunerConflictResolver resolver = new TunerConflictResolver(1, existingBookings, newBooking);

        assertFalse(resolver.detectConflict());
    }

    @org.junit.jupiter.api.Test
    void test002_one_tuner_two_bookings_no_conflict() {
        List<Booking> existingBookings = Arrays.asList(new Booking(0, 1, 0));
        Booking newBooking = new Booking(1, 2, 0);

        TunerConflictResolver resolver = new TunerConflictResolver(1, existingBookings, newBooking);

        assertFalse(resolver.detectConflict());
    }

    @org.junit.jupiter.api.Test
    void test003_one_tuner_two_bookings_full_conflict() {
        Booking booking1 = new Booking(0, 1, 0);
        List<Booking> existingBookings = Arrays.asList(booking1);
        Booking newBooking = new Booking(0, 1, 0);

        TunerConflictResolver resolver = new TunerConflictResolver(1, existingBookings, newBooking);

        assertTrue(resolver.detectConflict());

        List<ConflictResolutionSet> expectedResolutions = Arrays.asList(new ConflictResolutionSet(booking1));
        assertTrue(expectedResolutions.containsAll(resolver.getResolutions()));
        assertTrue(resolver.getResolutions().containsAll(expectedResolutions));
    }

    @org.junit.jupiter.api.Test
    void test004_one_tuner_two_bookings_partial_conflict_1() {
        Booking booking1 = new Booking(0, 2, 0);
        List<Booking> existingBookings = Arrays.asList(booking1);
        Booking newBooking = new Booking(1, 2, 0);

        TunerConflictResolver resolver = new TunerConflictResolver(1, existingBookings, newBooking);

        assertTrue(resolver.detectConflict());

        List<ConflictResolutionSet> expectedResolutions = Arrays.asList(new ConflictResolutionSet(booking1));
        assertTrue(expectedResolutions.containsAll(resolver.getResolutions()));
        assertTrue(resolver.getResolutions().containsAll(expectedResolutions));
    }

    @org.junit.jupiter.api.Test
    void test005_one_tuner_two_bookings_partial_conflict_2() {
        Booking booking1 = new Booking(1, 2, 0);
        List<Booking> existingBookings = Arrays.asList(booking1);
        Booking newBooking = new Booking(0, 2, 0);

        TunerConflictResolver resolver = new TunerConflictResolver(1, existingBookings, newBooking);

        assertTrue(resolver.detectConflict());

        List<ConflictResolutionSet> expectedResolutions = Arrays.asList(new ConflictResolutionSet(booking1));
        assertTrue(expectedResolutions.containsAll(resolver.getResolutions()));
        assertTrue(resolver.getResolutions().containsAll(expectedResolutions));
    }

    @org.junit.jupiter.api.Test
    void test006_two_tuner_two_bookings_no_conflict_1() {
        List<Booking> existingBookings = Arrays.asList(new Booking(0, 1, 0));
        Booking newBooking = new Booking(0, 1, 0);

        TunerConflictResolver resolver = new TunerConflictResolver(2, existingBookings, newBooking);

        assertFalse(resolver.detectConflict());
    }

    @org.junit.jupiter.api.Test
    void test007_two_tuner_two_bookings_no_conflict_2() {
        List<Booking> existingBookings = Arrays.asList(new Booking(0, 1, 0),
                new Booking(1, 2, 0));
        Booking newBooking = new Booking(0, 2, 0);

        TunerConflictResolver resolver = new TunerConflictResolver(2, existingBookings, newBooking);

        assertFalse(resolver.detectConflict());
    }

    @org.junit.jupiter.api.Test
    void test008_two_tuner_three_bookings_with_conflict_1() {
        Booking booking1 = new Booking(0, 1, 0);
        Booking booking2 = new Booking(1, 3, 0);
        Booking booking3 = new Booking(2, 4, 0);
        List<Booking> existingBookings = Arrays.asList(booking1, booking2, booking3);
        Booking newBooking = new Booking(1, 4, 0);

        TunerConflictResolver resolver = new TunerConflictResolver(2, existingBookings, newBooking);

        assertTrue(resolver.detectConflict());

        List<ConflictResolutionSet> expectedResolutions = Arrays.asList(new ConflictResolutionSet(booking2),
                new ConflictResolutionSet(booking3));
        assertTrue(expectedResolutions.containsAll(resolver.getResolutions()));
        assertTrue(resolver.getResolutions().containsAll(expectedResolutions));
    }

    @org.junit.jupiter.api.Test
    void test009_two_tuner_three_bookings_with_conflict_2() {
        Booking booking1 = new Booking(0, 1, 0);
        Booking booking2 = new Booking(1, 3, 0);
        Booking booking3 = new Booking(2, 4, 0);
        List<Booking> existingBookings = Arrays.asList(booking1, booking2, booking3);
        Booking newBooking = new Booking(0, 4, 0);

        TunerConflictResolver resolver = new TunerConflictResolver(2, existingBookings, newBooking);

        assertTrue(resolver.detectConflict());

        List<ConflictResolutionSet> expectedResolutions = Arrays.asList(new ConflictResolutionSet(booking2),
                new ConflictResolutionSet(booking3));
        assertTrue(expectedResolutions.containsAll(resolver.getResolutions()));
        assertTrue(resolver.getResolutions().containsAll(expectedResolutions));
    }

    @org.junit.jupiter.api.Test
    void test010_two_tuner_four_bookings_with_conflict() {
        Booking booking1 = new Booking(0, 3, 0);
        Booking booking2 = new Booking(2, 5, 0);
        Booking booking3 = new Booking(4, 7, 0);
        Booking booking4 = new Booking(6, 9, 0);
        List<Booking> existingBookings = Arrays.asList(booking1, booking2, booking3, booking4);
        Booking newBooking = new Booking(0, 9, 0);

        TunerConflictResolver resolver = new TunerConflictResolver(2, existingBookings, newBooking);

        assertTrue(resolver.detectConflict());

        List<ConflictResolutionSet> expectedResolutions = Arrays.asList(
                new ConflictResolutionSet(Arrays.asList(booking1, booking3)),
                new ConflictResolutionSet(Arrays.asList(booking2, booking3)),
                new ConflictResolutionSet(Arrays.asList(booking2, booking4)));
        assertTrue(expectedResolutions.containsAll(resolver.getResolutions()));
        assertTrue(resolver.getResolutions().containsAll(expectedResolutions));
    }
}