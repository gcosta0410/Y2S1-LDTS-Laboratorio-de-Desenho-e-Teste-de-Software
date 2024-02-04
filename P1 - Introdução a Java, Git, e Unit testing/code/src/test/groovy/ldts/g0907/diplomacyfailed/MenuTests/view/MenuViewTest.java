package ldts.g0907.diplomacyfailed.MenuTests.view;

import com.googlecode.lanterna.screen.Screen;
import ldts.g0907.diplomacyfailed.menu.controller.VoidController;
import ldts.g0907.diplomacyfailed.menu.view.LanternaMenuView;
import ldts.g0907.diplomacyfailed.menu.view.MenuView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class MenuViewTest {

    private MenuView menuView;

    @BeforeEach
    public void setup() {
        Screen screen = Mockito.mock(Screen.class);
        menuView = new LanternaMenuView(screen, 50,100);
    }

    @Test
    public void testGetMenuController() {
        Assertions.assertTrue(menuView.getMenuController() instanceof VoidController);
    }



}
