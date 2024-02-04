package ldts.g0907.diplomacyfailed;
import ldts.g0907.diplomacyfailed.menu.state.MainMenuState;

public class Game implements StateObserver {

    private final ViewFactoryInterface gui;
    private State state;

    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
    }

    public Game(ViewFactoryInterface graphical_interface){
        gui = graphical_interface;
        changeState(new MainMenuState(gui));
    }

    public void run() {
        while (true) {
            state.update();
            state.draw();
            state.processInput();
       }
    }

//    public void run(){
//        long initialTime = System.nanoTime();
//        final double timeU = 1000000000 / 70 ; //ups
//        final double timeF = 1000000000 / 60; //fps
//        double deltaU = 0, deltaF = 0;
//        long timer = System.currentTimeMillis();
//        while (true) {
//
//            long currentTime = System.nanoTime();
//            deltaU += (currentTime - initialTime) / timeU;
//            deltaF += (currentTime - initialTime) / timeF;
//            initialTime = currentTime;
//
//            if (deltaU >= 1) {
//                state.update();
//                state.draw();
//                state.processInput();;
//                deltaU--;
//            }
//
//            if (deltaF >= 1) {
//
//                deltaF--;
//            }
//
//            if (System.currentTimeMillis() - timer > 1000) {
//                timer += 1000;
//            }
//        }
//    }

    @Override
    public void changeState(State new_state) {
        this.state = new_state;
        this.state.setObserver(this);
    }
}
