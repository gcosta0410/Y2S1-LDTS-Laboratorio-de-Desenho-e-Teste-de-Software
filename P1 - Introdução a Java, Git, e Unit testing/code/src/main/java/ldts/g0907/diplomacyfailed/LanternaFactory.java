package ldts.g0907.diplomacyfailed;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFrame;
import ldts.g0907.diplomacyfailed.game.view.LanternaPlayingView;
import ldts.g0907.diplomacyfailed.game.view.PlayingView;
import ldts.g0907.diplomacyfailed.menu.view.LanternaMenuView;
import ldts.g0907.diplomacyfailed.menu.view.MenuView;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class LanternaFactory implements ViewFactoryInterface {

    private Screen screen;

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }


    private final int width;
    private final int height;

    public LanternaFactory() {
        this.width = 100;
        this.height = 35;
    }

    public LanternaFactory(int width, int height){
        this.width = width;
        this.height = height;

        try {
//            ------------ O tipo de letra e os retângulos ficam muito maiores, mas é ajustável em....
            URL resource = getClass().getClassLoader().getResource("square.ttf");
            assert resource != null;
            File fontFile = new File(resource.toURI());
            Font font =  Font.createFont(Font.TRUETYPE_FONT, fontFile);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            Font loadedFont = font.deriveFont(Font.PLAIN, 12); //ajustável aqui
            AWTTerminalFontConfiguration fontConfig = AWTTerminalFontConfiguration.newInstance(loadedFont);

            DefaultTerminalFactory factory = new DefaultTerminalFactory();
            factory.setTerminalEmulatorFontConfiguration(fontConfig);
            factory.setForceAWTOverSwing(true);
            factory.setInitialTerminalSize(new TerminalSize(width, height));


            Terminal terminal = factory.createTerminal();
            ((AWTTerminalFrame)terminal).addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    e.getWindow().dispose();
                }
            });

            this.screen = new TerminalScreen(terminal);

            this.screen.setCursorPosition(null);
            this.screen.startScreen();
            this.screen.doResizeIfNecessary();

        } catch (IOException | URISyntaxException | FontFormatException e) {
            e.printStackTrace();
        }

    }

    @Override
    public PlayingView createPlayingView() {
        return new LanternaPlayingView(screen, width, height);
    }

    @Override
    public MenuView createMenuView() {
        return new LanternaMenuView(screen, width, height);
    }


}
