package ldts.g0907.diplomacyfailed.MenuTests.controller;

import ldts.g0907.diplomacyfailed.menu.controller.*;
import ldts.g0907.diplomacyfailed.menu.model.Menu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MenuControllerTest {

    private Menu menu;

    @BeforeEach
    public void setup(){
        menu = Mockito.mock(Menu.class);
    }

    @Test
    public void testChangeOptionUpController() {
        MenuController controller = new ChangeOptionUpController();
        Assertions.assertEquals("", controller.executeAction(menu));
        Mockito.verify(menu, Mockito.times(1)).changeOptionUp();
    }

    @Test
    public void testChangeOptionDownController() {
        MenuController controller = new ChangeOptionDownController();
        Assertions.assertEquals("", controller.executeAction(menu));
        Mockito.verify(menu, Mockito.times(1)).changeOptionDown();
    }

    @Test
    public void testSelectOptionController() {
        MenuController controller = new SelectOptionController();
        Mockito.when(controller.executeAction(menu)).thenReturn("");
        Assertions.assertEquals("", controller.executeAction(menu));
        Mockito.verify(menu, Mockito.times(1)).selectOption();
    }

    @Test
    public void testVoidController() {
        MenuController controller = new VoidController();
        Assertions.assertEquals("", controller.executeAction(menu));
    }
}
