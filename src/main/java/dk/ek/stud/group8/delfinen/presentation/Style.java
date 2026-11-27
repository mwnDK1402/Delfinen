package dk.ek.stud.group8.delfinen.presentation;

import static dk.ek.stud.group8.delfinen.ui.ANSI.*;

public final class Style {
    public static String title(String str) {
        return BOLD + "[ " + str + " ]" + RESET;
    }

    public static String error(String str) {
        return RED + str + RESET;
    }
}
