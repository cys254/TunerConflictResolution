import java.util.*;

public class TunerConflictResolver {

    private int numberOfTuners;
    private List<Booking> existingBookings;
    private Booking newBooking;
    private List<ConflictResolutionSet> resolutions;

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
        // TODO
        resolutions = new ArrayList<>();
        // TODO Detect tuner conflict and generation resolution sets
        return hasConflict();
    }
}
