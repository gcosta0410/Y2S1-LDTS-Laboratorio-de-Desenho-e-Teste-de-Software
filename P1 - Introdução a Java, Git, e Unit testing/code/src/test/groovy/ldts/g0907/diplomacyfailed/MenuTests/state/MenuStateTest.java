package ldts.g0907.diplomacyfailed.MenuTests.state;

import ldts.g0907.diplomacyfailed.LanternaFactory;
import ldts.g0907.diplomacyfailed.ViewFactoryInterface;
import ldts.g0907.diplomacyfailed.menu.model.Menu;
import ldts.g0907.diplomacyfailed.menu.state.MainMenuState;
import ldts.g0907.diplomacyfailed.menu.state.MenuState;
import ldts.g0907.diplomacyfailed.menu.view.MenuView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MenuStateTest {

    private MenuState menuState;

    @BeforeEach
    public void setup(){
        ViewFactoryInterface gui = new LanternaFactory();
        menuState = new MainMenuState(gui);
    }

    @Test
    public void testSetAndGetMenu(){
        Menu menu = Mockito.mock(Menu.class);
        menuState.setMenu(menu);
        Assertions.assertEquals(menu, menuState.getMenu());
    }

    @Test
    public void testSetAndGetMenuView(){
        MenuView view = Mockito.mock(MenuView.class);
        menuState.setMenuView(view);
        //Assertions.assertTrue(menuState.getMenuView() instanceof MenuView); -> IntelliJ sugeriu substituir pela linha abaixo, que tambem faz sentido
        Assertions.assertNotNull(menuState.getMenuView());
    }


    @Test
    public void testProcessInput(){
        MenuView view = Mockito.mock(MenuView.class);
        menuState.setMenuView(view);
        menuState.processInput();
        Mockito.verify(menuState.getMenuView(), Mockito.times(1)).processInput();
    }


    @Test
    public void testDraw(){
        Menu menu = Mockito.mock(Menu.class);
        menuState.setMenu(menu);
        MenuView view = Mockito.mock(MenuView.class);
        menuState.setMenuView(view);
        menuState.draw();
        Mockito.verify(view, Mockito.times(1)).draw(menu);
    }

}
