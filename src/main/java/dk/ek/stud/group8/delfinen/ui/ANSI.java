package dk.ek.stud.group8.delfinen.ui;

public final class ANSI {
    public static final String ESC = "\u001b";
    /**
     * Control Sequence Introducer
     */
    public static final String CSI = ESC + "[";
    public static final String RESET = CSI + "0m";
    public static final String BOLD = CSI + "1m";
    public static final String ENABLE_ALT_SCREEN_BUF = CSI + "?1049h";
    public static final String DISABLE_ALT_SCREEN_BUF = CSI + "?1049l";

    /**
     * 8-bit foreground color
     */
    public static String F8(int color) {
        return CSI + "38;5;" + color + "m";
    }

    public static String GRAY = F8(244);
    public static String RED = F8(9);

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
}
