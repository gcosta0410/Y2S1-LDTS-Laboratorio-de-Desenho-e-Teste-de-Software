package ldts.g0907.diplomacyfailed.menu.view;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import ldts.g0907.diplomacyfailed.menu.controller.ChangeOptionDownController;
import ldts.g0907.diplomacyfailed.menu.controller.ChangeOptionUpController;
import ldts.g0907.diplomacyfailed.menu.controller.SelectOptionController;
import ldts.g0907.diplomacyfailed.menu.controller.VoidController;
import ldts.g0907.diplomacyfailed.menu.model.Menu;

import java.io.IOException;

public class LanternaMenuView extends MenuView{

    private Screen screen;
    private int width;
    private int height;
    private int startingHeight;
    private TextGraphics graphics;
    private Menu menu;
    private final static String BACKGROUND_COLOR = "#C4EBF1";

    public LanternaMenuView(Screen screen, int width, int height) {
        this.screen = screen;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Menu menu) {
        graphics = screen.newTextGraphics();
        this.menu = menu;
        //mind the order
        drawBackground();
        drawText();
        drawButton1();
        drawButton2();
        drawButton3();

        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
        screen.doResizeIfNecessary();
    }

    private void drawBackground() {
        graphics.setBackgroundColor(TextColor.Factory.fromString(BACKGROUND_COLOR));
        graphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
    }

    private void drawText(){
        startingHeight = height/3;
        graphics.setForegroundColor(TextColor.Factory.fromString(menu.getTextColor()));
        graphics.enableModifiers(SGR.BOLD);
        if(menu.getText().length > 1){
            startingHeight = 10;
            for(String str : menu.getText()){
                graphics.putString(new TerminalPosition((width/2) - 47, startingHeight), str);
                startingHeight += 1;
            }
        }
        else
            graphics.putString(new TerminalPosition((width/2) - menu.getText()[0].length()/2, startingHeight), menu.getText()[0]);
    }

    private void drawButton1(){
        graphics.setForegroundColor(TextColor.Factory.fromString(menu.getButton1Color()));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition((width/2) - (5), startingHeight + 5), menu.getButton1());
    }

    private void drawButton2(){
        graphics.setForegroundColor(TextColor.Factory.fromString(menu.getButton2Color()));
        graphics.enableModifiers(SGR.BOLD);
        graphics.putString(new TerminalPosition((width/2) - (5), startingHeight + 10), menu.getButton2());
    }

    private void drawButton3(){
        if(menu.getButton3() != null) {
            graphics.setForegroundColor(TextColor.Factory.fromString(menu.getButton3Color()));
            graphics.enableModifiers(SGR.BOLD);
            graphics.putString(new TerminalPosition((width/2) - (5), startingHeight + 15), menu.getButton3());
        }
    }

    @Override
    public void processInput() {
        try {
            KeyStroke key = screen.readInput();
            newController(key);

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void newController(KeyStroke key){
        switch (key.getKeyType()) {
            case ArrowUp -> menuController = new ChangeOptionUpController();
            case ArrowDown -> menuController = new ChangeOptionDownController();
            case Enter -> menuController = new SelectOptionController();
            default -> menuController = new VoidController();
        }
    }
}
