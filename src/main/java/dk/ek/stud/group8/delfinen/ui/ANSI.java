package dk.ek.stud.group8.delfinen.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class ANSI {
    public static final int WIDTH = 80;
    private static final boolean USE_COLOR = true;

    public static final String ESC = "\u001b";
    /**
     * Control Sequence Introducer
     */
    public static final String CSI = ESC + "[";
    public static final String RESET = CSI + "0m";
    public static final String BOLD = CSI + "1m";
    private static final String WHITE = CSI + "97m";
    private static final String DIM = CSI + "90m";
    private static final String RED = CSI + "31m";
    private static final String GREEN = CSI + "32m";
    private static final String YELLOW = CSI + "33m";

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("EEEE d. MMMM yyyy", Locale.forLanguageTag("da-DK"));

    public static final String ENABLE_ALT_SCREEN_BUF = CSI + "?1049h";
    public static final String DISABLE_ALT_SCREEN_BUF = CSI + "?1049l";

    private static String paint(String code, String text) {
        return USE_COLOR ? code + text + RESET : text;
    }

    public static String white(String text) { return paint(WHITE, text); }
    public static String dim(String text) { return paint(DIM, text); }
    public static String success(String msg) { return paint(GREEN, msg); }
    public static String error(String msg) { return paint(RED, msg); }
    public static String warning(String msg) { return paint(YELLOW, msg); }

    /**
     * 8-bit foreground color
     */
    public static String F8(int color) {
        return CSI + "38;5;" + color + "m";
    }

    public static String GRAY = F8(244);
    // public static String RED = F8(9);

    /**
     * Cursor Previous Line
     */
    public static final String CPL = CPL(1);

    /**
     * Cursor Previous Line
     */
    public static String CPL(int lines) {
        return CSI + lines + "F";
    }

    /**
     * Cursor Next Line
     */
    public static final String CNL = CNL(1);

    /**
     * Cursor Next Line
     */
    public static String CNL(int lines) {
        return CSI + lines + "E";
    }

    /**
     * Save Current Cursor Position
     */
    public static final String SCP = CSI + "s";

    /**
     * Restore Saved Cursor Position
     */
    public static final String RCP = CSI + "u";

    /**
     * Cursor Position
     */
    public static final String CUP = CUP(1, 1);

    /**
     * Cursor Position
     */
    public static String CUP(int row, int col) {
        return CSI + row + ";" + col + "H";
    }

    /**
     * Velkomst boks layout
     */
    public static String boxTop() { return "╔" + "═".repeat(WIDTH - 2) + "╗"; }
    public static String boxBottom() { return "╚" + "═".repeat(WIDTH - 2) + "╝"; }

    public static String boxLine(String text) {
        int inner = WIDTH - 2;
        int left = (inner - text.length()) / 2;
        int right = inner - text.length() - left;
        return "║" + " ".repeat(left) + text + " ".repeat(right) + "║";
    }

    /**
     * Sektioner
     */
    public static String divider() {
        return dim("─".repeat(WIDTH));
    }

    public static String heading(String title) {
        return "\n " + title.toUpperCase() + "\n" + divider();
    }

    public static String menuItem(String number, String label, String description) {
        String numberPart = number + ". ";
        String labelPart = String.format("%-12s", label);
        return "  " + numberPart + labelPart + dim(description);
    }

    public static String statusLine(LocalDate today, String rightText) {
        String date = today.format(DATE_FORMAT);
        int spaces = Math.max(1, WIDTH - date.length() - rightText.length());
        return dim(date) + " ".repeat(spaces) + dim(rightText);
    }

    public static String statusLineTwo(String leftText, String rightText) {
        int spaces = Math.max(1, WIDTH - leftText.length() - rightText.length());
        return dim(leftText) + " ".repeat(spaces) + dim(rightText);
    }

    public static String padding(String text, int width) {
        if (text.length() > width) {
            return text.substring(0, width - 1) + "...";
        }
        return text + " ".repeat(width - text.length());
    }

    public static String paddingLeft(String text, int width) {
        if (text.length() > width) {
            return text.substring(0, width - 1) + "...";
        }
        return " ".repeat(width - text.length()) + text;
    }
}
