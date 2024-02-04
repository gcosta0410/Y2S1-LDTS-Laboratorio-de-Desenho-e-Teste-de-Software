package ldts.g0907.diplomacyfailed;

import ldts.g0907.diplomacyfailed.game.view.PlayingView;
import ldts.g0907.diplomacyfailed.menu.view.MenuView;

public interface ViewFactoryInterface {
//    Vai ser usado o Lanterna, mas podia não ser o caso
//    Game não tem de saber quem desenha

    PlayingView createPlayingView();
    MenuView createMenuView();
    int getWidth();
    int getHeight();
}
