package dk.ek.stud.group8.delfinen.ui;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class Style {
    public static final int WIDTH = 80;
    public static final int FORM_INNER_WIDTH = 56;
    private static final boolean USE_COLOR = true;
    private static final String BREADCRUMB_CHEVRON = "  ›  ";
    private static final NumberFormat MONEY =
            NumberFormat.getIntegerInstance(Locale.forLanguageTag("da-DK"));

    public static final String ESC = "\u001b";
    /**
     * ANSI text styling
     */
    public static final String CSI = ESC + "[";
    public static final String RESET = CSI + "0m";
    public static final String BOLD = CSI + "1m";
    private static final String WHITE = CSI + "97m";
    private static final String DIM = CSI + "38;5;248m";
    private static final String RED = CSI + "31m";
    private static final String GREEN = CSI + "92m";
    private static final String YELLOW = CSI + "33m";

    private static final String BG_ACCENT = CSI + "48;5;24m";   // dyb blå

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
    public static String bold(String text) { return paint(BOLD, text); }

    public static String getBreadcrumbChevron() { return BREADCRUMB_CHEVRON; }

    public static String money(int amount) {
        return MONEY.format(amount) + " kr.";
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

    public static String statLabels(int colWidth, String... labels) {
        StringBuilder line = new StringBuilder("  ");
        for (String label : labels) {
            line.append(dim(padding(label, colWidth)));
        }
        return line.toString();
    }

    public static String statValues(int colWidth, String... values) {
        StringBuilder line = new StringBuilder("  ");
        for (String value : values) {
            line.append(paint(BOLD + WHITE, padding(value, colWidth)));   // pad først, farv bagefter
        }
        return line.toString();
    }

    public static String progressBar(int percent, int width, String suffix) {
        int filled = Math.min(width, Math.max(0, percent * width / 100));
        String done = "█".repeat(filled);
        String rest = "░".repeat(width - filled);
        return "  " + success(done) + paint(DIM, rest) + "  " + white(percent + " % " + suffix);
    }

    public static String[] headerBox(String title, String subTitle, String rightText, boolean spacious) {
        int inner = WIDTH - 2;

        String left = title.toUpperCase();
        if (!subTitle.isEmpty()) {
            left += getBreadcrumbChevron() + subTitle.toUpperCase();
        }
        left = " " + left;
        String right = rightText.isEmpty() ? "" : rightText + " ";

        // Afkort venstre side, hvis der ikke er plads til begge dele
        int maxLeft = inner - right.length() - 1;
        if (left.length() > maxLeft) {
            left = left.substring(0, Math.max(0, maxLeft - 3)) + "...";
        }

        int spaces = inner - left.length() - right.length();

        // Padding er regnet på ren tekst. Farve påføres bagefter, så rammen flugter.
        String content = "║" + paint(BOLD + WHITE, left)
                + " ".repeat(spaces) + dim(right) + white("║");

        String empty = "║" + " ".repeat(inner) + "║";

        if (spacious) {
            return new String[] { boxTop(), empty, content, empty, boxBottom() };
        }
        return new String[] { boxTop(), content, boxBottom() };
    }

    // Genvej: kompakt som standard
    public static String[] headerBox(String title, String subTitle, String rightText) {
        return headerBox(title, subTitle, rightText, false);
    }



    /**
     * Sektioner
     */
    public static String divider() {
        return dim("─".repeat(WIDTH));
    }

    public static String heading(String title) {
        return "\n" + white(title.toUpperCase()) + "\n" + divider();
    }

    public static String heading(String title, String subtitle) {
        return "\n" + white(title.toUpperCase()) + dim(getBreadcrumbChevron()) + white(subtitle.toUpperCase()) + "\n" + divider();
    }

    public static String menuItem(String number, String label, String description) {
        String numberPart = number + ". ";
        String labelPart = String.format("%-12s", label);
        return "  " + numberPart + labelPart + dim(description);
    }

    public static String helperTextForm(String text) { return Style.dim(text); }

    public static String statusLine(LocalDate today, String rightText) {
        String date = today.format(DATE_FORMAT);
        int spaces = Math.max(1, WIDTH - date.length() - rightText.length());
        return dim(date) + " ".repeat(spaces) + dim(rightText);
    }

    public static String statusLineTwo(String leftText, String rightText) {
        int spaces = Math.max(1, WIDTH - leftText.length() - rightText.length());
        return leftText + " ".repeat(spaces) + dim(rightText);
    }

    public static String padding(String text, int width) {
        if (text.length() > width) {
            return text.substring(0, width - 3) + "...";
        }
        return text + " ".repeat(width - text.length());
    }

    public static String paddingLeft(String text, int width) {
        if (text.length() > width) {
            return text.substring(0, width - 3) + "...";
        }
        return " ".repeat(width - text.length()) + text;
    }
}