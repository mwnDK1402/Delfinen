package dk.ek.stud.group8.delfinen.domain;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

// Cannot enforce date invariant through a single public constructor
// if TrainingResult is a record (the canonical constructor is always public).
@SuppressWarnings("ClassCanBeRecord")
public final class TrainingResult {
    public static final TrainingResult NONE = new TrainingResult();

    private final LocalDate date;
    private final Duration time;

    private TrainingResult() {
        date = null;
        time = null;
    }

    public TrainingResult(LocalDate now, LocalDate date, Duration time) {
        if (date.isAfter(now)) {
            throw new FutureDateException(now, date);
        }

        this.date = Objects.requireNonNull(date);
        this.time = Objects.requireNonNull(time);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TrainingResult that = (TrainingResult) o;
        return Objects.equals(date, that.date) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, time);
    }

    public LocalDate date() {
        return date;
    }

    public Duration time() {
        return time;
    }

    public static final class FutureDateException extends RuntimeException {
        public final LocalDate now;
        public final LocalDate date;

        public FutureDateException(LocalDate now, LocalDate date) {
            super("The given training result date was in the future: " + date);
            this.now = now;
            this.date = date;
        }
    }
}
