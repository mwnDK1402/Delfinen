package dk.ek.stud.group8.delfinen.domain;

record IdRange(int min, int max) {
    /**
     * Returns the half-open range {@code [min, max)} that new IDs are drawn from,
     * given how many IDs are already in use.
     *
     * <p>The range depends on {@code n}, the number of decimal digits in
     * {@code memberCount} (0 counts as one digit):
     * <ul>
     *   <li>{@code n <= 3} (fewer than 1,000 IDs): {@code [1, 10^4)}. IDs are not
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
     * every time {@code memberCount} gains a digit, so it never fills up.
     *
     * @param memberCount the number of IDs already in use; must not be negative
     * @return the range to draw new IDs from; {@code max} is exclusive
     */
    static IdRange forMemberCount(int memberCount) {
        int countDigits = memberCount == 0 ? 1 : (int) Math.log10(memberCount) + 1;
        int idDigits = Math.max(3, countDigits) + 1;
        int min = countDigits <= 3 ? 1 : (int) Math.pow(10, idDigits - 2);
        int max = (int) Math.pow(10, idDigits);
        return new IdRange(min, max);
    }
}
