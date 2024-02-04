package ldts.g0907.diplomacyfailed.MenuTests.model;

import ldts.g0907.diplomacyfailed.menu.model.Menu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MenuTest {

    private Menu menu;

    @BeforeEach
    public void setup() {
        menu = new Menu(new String[]{"MENU"}, "BUTTON1", "BUTTON2", "BUTTON3");
    }

    @Test
    public void testGetText() {
        Assertions.assertArrayEquals(new String[]{"MENU"}, menu.getText());
    }

    @Test
    public void testGetTextColor() {
        Assertions.assertEquals("#000000", menu.getTextColor());
    }

    @Test
    public void testGetButton1() {
        Assertions.assertEquals(">BUTTON1", menu.getButton1());
    }

    @Test
    public void testGetButton1Color() {
        Assertions.assertEquals("#FF54F6", menu.getButton1Color());
    }

    @Test
    public void testGetButton2() {
        Assertions.assertEquals("BUTTON2", menu.getButton2());
    }

    @Test
    public void testGetButton2Color() {
        Assertions.assertEquals("#A95AEC", menu.getButton2Color());
    }

    @Test
    public void testGetButton3() {
        Assertions.assertEquals("BUTTON3", menu.getButton3());
    }

    @Test
    public void testGetButton3Color() {
        Assertions.assertEquals("#A95AEC", menu.getButton3Color());
    }

    @Test
    public void testChangeOptionUp() {
        menu.changeOptionUp();
        Assertions.assertEquals("BUTTON1", menu.getButton1());
        Assertions.assertEquals("BUTTON2", menu.getButton2());
        Assertions.assertEquals(">BUTTON3", menu.getButton3());
        menu.changeOptionUp();
        Assertions.assertEquals("BUTTON1", menu.getButton1());
        Assertions.assertEquals(">BUTTON2", menu.getButton2());
        Assertions.assertEquals("BUTTON3", menu.getButton3());
        menu.changeOptionUp();
        Assertions.assertEquals(">BUTTON1", menu.getButton1());
        Assertions.assertEquals("BUTTON2", menu.getButton2());
        Assertions.assertEquals("BUTTON3", menu.getButton3());
    }

    @Test
    public void testChangeOptionDown() {
        menu.changeOptionDown();
        Assertions.assertEquals("BUTTON1", menu.getButton1());
        Assertions.assertEquals(">BUTTON2", menu.getButton2());
        Assertions.assertEquals("BUTTON3", menu.getButton3());
        menu.changeOptionDown();
        Assertions.assertEquals("BUTTON1", menu.getButton1());
        Assertions.assertEquals("BUTTON2", menu.getButton2());
        Assertions.assertEquals(">BUTTON3", menu.getButton3());
        menu.changeOptionDown();
        Assertions.assertEquals(">BUTTON1", menu.getButton1());
        Assertions.assertEquals("BUTTON2", menu.getButton2());
        Assertions.assertEquals("BUTTON3", menu.getButton3());
    }

    @Test
    public void testSelectOption() {
        Assertions.assertEquals(">BUTTON1", menu.selectOption());
    }
}
