package dk.ek.stud.group8.delfinen;

import dk.ek.stud.group8.delfinen.controller.Controller;
import dk.ek.stud.group8.delfinen.domain.MemberRegistry;
import dk.ek.stud.group8.delfinen.persistence.DataStorage;
import dk.ek.stud.group8.delfinen.ui.*;

import java.nio.file.Path;
import java.util.Random;

public class Main {
    static void main() throws InterruptedException {
        MemberRegistry memberRegistry = new MemberRegistry(new Random());
        DataStorage dataStorage = new DataStorage(memberRegistry, Path.of("data"));
        Controller controller = new Controller(memberRegistry, dataStorage);
        Terminal terminal = new SystemTerminal(System.in, System.out);
        UserInterface ui = new UserInterface(terminal, controller);
        ui.start();
    }
}