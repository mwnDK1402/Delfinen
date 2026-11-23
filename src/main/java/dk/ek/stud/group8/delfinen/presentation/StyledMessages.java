package dk.ek.stud.group8.delfinen.presentation;

import dk.ek.stud.group8.delfinen.ui.MenuList;

public final class StyledMessages implements MenuList.Messages {
    private final MenuList.Messages menuMessages;

    public StyledMessages(MenuList.Messages menuMessages) {
        this.menuMessages = menuMessages;
    }

    @Override
    public String prompt(int min, int max) {
        return menuMessages.prompt(min, max);
    }

    @Override
    public String notANumber(int min, int max, String actual) {
        return Style.error(menuMessages.notANumber(min, max, actual));
    }

    @Override
    public String outOfRange(int min, int max, int actual) {
        return Style.error(menuMessages.outOfRange(min, max, actual));
    }
}
