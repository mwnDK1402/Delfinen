package dk.ek.stud.group8.delfinen.persistence;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

final class DateCodec {
    private static final DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;

    /**
     * @return the formatted date string, not null
     * @throws DateTimeException if an error occurs during printing
     */
    static String encode(LocalDate date) throws DateTimeException {
        return date.format(fmt);
    }

    /**
     * @return the parsed local date, not null
     * @throws DateTimeParseException if the text cannot be parsed
     */
    static LocalDate decode(String cell) throws DateTimeParseException {
        return LocalDate.parse(cell, fmt);
    }
}
