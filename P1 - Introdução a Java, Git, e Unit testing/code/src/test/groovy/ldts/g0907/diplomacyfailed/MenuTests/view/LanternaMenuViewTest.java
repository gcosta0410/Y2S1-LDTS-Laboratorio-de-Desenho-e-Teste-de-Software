package ldts.g0907.diplomacyfailed.MenuTests.view;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import ldts.g0907.diplomacyfailed.menu.controller.ChangeOptionDownController;
import ldts.g0907.diplomacyfailed.menu.controller.ChangeOptionUpController;
import ldts.g0907.diplomacyfailed.menu.controller.SelectOptionController;
import ldts.g0907.diplomacyfailed.menu.controller.VoidController;
import ldts.g0907.diplomacyfailed.menu.view.LanternaMenuView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class LanternaMenuViewTest {

    private Screen screen;
    private LanternaMenuView menuView;

    //No point in testing menuView.draw() method because methods called inside it are private anyway

    @BeforeEach
    public void setup(){
        screen = Mockito.mock(Screen.class);
        menuView = new LanternaMenuView(screen, 50, 100);
    }

    @Test
    public void testScreenReadInput() throws IOException {
        KeyStroke key = Mockito.mock(KeyStroke.class);
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Enter);
        Mockito.when(screen.readInput()).thenReturn(key);
        menuView.processInput();
        Mockito.verify(screen, Mockito.times(1)).readInput();
    }

    @Test
    public void testProcessInputArrowUp() throws IOException {
        KeyStroke key = Mockito.mock(KeyStroke.class);
        Mockito.when(key.getKeyType()).thenReturn(KeyType.ArrowUp);
        Mockito.when(screen.readInput()).thenReturn(key);
        menuView.processInput();
        Assertions.assertTrue(menuView.getMenuController() instanceof ChangeOptionUpController);
    }

    @Test
    public void testProcessInputArrowDown() throws IOException {
        KeyStroke key = Mockito.mock(KeyStroke.class);
        Mockito.when(key.getKeyType()).thenReturn(KeyType.ArrowDown);
        Mockito.when(screen.readInput()).thenReturn(key);
        menuView.processInput();
        Assertions.assertTrue(menuView.getMenuController() instanceof ChangeOptionDownController);
    }

    @Test
    public void testProcessInputEnter() throws IOException {
        KeyStroke key = Mockito.mock(KeyStroke.class);
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Enter);
        Mockito.when(screen.readInput()).thenReturn(key);
        menuView.processInput();
        Assertions.assertTrue(menuView.getMenuController() instanceof SelectOptionController);
    }

    @Test
    public void testProcessInputInvalid() throws IOException {
        KeyStroke key = Mockito.mock(KeyStroke.class);
        Mockito.when(key.getKeyType()).thenReturn(KeyType.Escape);
        Mockito.when(screen.readInput()).thenReturn(key);
        menuView.processInput();
        Assertions.assertTrue(menuView.getMenuController() instanceof VoidController);
    }

}
