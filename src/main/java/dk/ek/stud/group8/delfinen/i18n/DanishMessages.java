package dk.ek.stud.group8.delfinen.i18n;

import dk.ek.stud.group8.delfinen.ui.MenuList;

public class DanishMessages implements MenuList.Messages {
    private static final String errorPrefix = "Ugyldigt valg: ";

    @Override
    public String prompt(int min, int max) {
        return "Indtast et tal mellem " + min + " og " + max;
    }

    @Override
    public String notANumber(int min, int max, String actual) {
        return errorPrefix
                + "Brug tal, ikke bogstaver. "
                + "Tallet skal være mellem " + min + " og " + max + ".";
    }

    @Override
    public String outOfRange(int min, int max, int actual) {
        return errorPrefix
                + "Skriv et heltal mellem " + min + " og " + max + ".";
    }
}
