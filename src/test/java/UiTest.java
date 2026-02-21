import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import kirkstein.ui.Ui;

public class UiTest {

    @Test
    public void showHelp_containsAllCommands_success() {
        Ui ui = new Ui();
        String help = ui.showHelp();

        assertTrue(help.contains("todo"));
        assertTrue(help.contains("deadline"));
        assertTrue(help.contains("event"));
        assertTrue(help.contains("list"));
        assertTrue(help.contains("mark"));
        assertTrue(help.contains("unmark"));
        assertTrue(help.contains("find"));
        assertTrue(help.contains("delete"));
        assertTrue(help.contains("bye"));
    }

    @Test
    public void showHelp_isNotEmpty_success() {
        Ui ui = new Ui();
        String help = ui.showHelp();

        assertTrue(!help.isEmpty());
    }
}
