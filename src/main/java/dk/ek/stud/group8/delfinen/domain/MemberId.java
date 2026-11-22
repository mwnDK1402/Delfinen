package dk.ek.stud.group8.delfinen.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

// TODO: Rethink this whole class
public final class MemberId {
    private final int value;

    MemberId(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        return switch (o) {
            case MemberId mid -> value == mid.value;
            case Integer id -> value == id;
            default -> false;
        };
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    public static final class Factory {
        private final Random rng;
        private final Set<Integer> loadableIds;
        private final Set<Integer> activeIds;

        public Factory(Random rng, Set<Integer> loadableIds) {
            this.rng = rng;
            this.loadableIds = new HashSet<>(loadableIds);
            this.activeIds = new HashSet<>();
        }

        public MemberId generate() {
            return new MemberId(nextId());
        }

        /**
         * @throws IllegalArgumentException when id not found in database.
         */
        public MemberId load(int id) {
            if (!loadableIds.contains(id)) {
                throw new IllegalArgumentException("Cannot load id " + id + ": not found in database");
            }

            if (activeIds.contains(id)) {
                throw new IllegalArgumentException("Cannot load id " + id + ": already active");
            }

            loadableIds.remove(id);
            activeIds.add(id);

            return new MemberId(id);
        }

        private int nextId() {
            var range = IdRange.forActiveCount(activeIds.size());

            int id;
            do {
                id = rng.nextInt(range.min, range.max);
            } while (!activeIds.add(id));

            return id;
        }

        record IdRange(int min, int max) {
            /**
             * Returns the half-open range {@code [min, max)} that new IDs are drawn from,
             * given how many IDs are already in use.
             *
             * <p>The range depends on {@code n}, the number of decimal digits in
             * {@code activeCount} (0 counts as one digit):
             * <ul>
             *   <li>{@code n <= 3} (fewer than 1,000 IDs): {@code [0, 10^4)}. IDs are not
             *       zero-padded, so they may have fewer than 4 digits.</li>
             *   <li>{@code n >= 4}: {@code [10^(n-1), 10^(n+1))}, so IDs have {@code n} or
             *       {@code n + 1} digits.</li>
             * </ul>
             *
             * <p>For example, 1,000 to 9,999 IDs gives {@code [1000, 100000)}, and
             * 10,000 to 99,999 gives {@code [10000, 1000000)}.
             *
             * <p>Each range holds roughly ten times more values than the highest count
             * that selects it, so at most about 10% of the range is ever taken and
             * drawing a free ID takes about one attempt on average. The range grows
             * every time {@code activeCount} gains a digit, so it never fills up.
             *
             * @param activeCount the number of IDs already in use; must not be negative
             * @return the range to draw new IDs from; {@code max} is exclusive
             */
            static IdRange forActiveCount(int activeCount) {
                int countDigits = activeCount == 0 ? 1 : (int) Math.log10(activeCount) + 1;
                int idDigits = Math.max(3, countDigits) + 1;
                int min = countDigits <= 3 ? 0 : (int) Math.pow(10, idDigits - 2);
                int max = (int) Math.pow(10, idDigits);
                return new IdRange(min, max);
            }
        }
    }
}
