package tools;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for date parsing and formatting (dd/MM/yyyy)
 */
public class DateUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static LocalDate parse(String dateStr) {
        return LocalDate.parse(dateStr.trim(), FORMATTER);
    }

    public static String format(LocalDate date) {
        return date.format(FORMATTER);
    }

    /**
     * Try to parse; return null on failure (for validation use)
     */
    public static LocalDate tryParse(String dateStr) {
        try {
            return LocalDate.parse(dateStr.trim(), FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

        
}
