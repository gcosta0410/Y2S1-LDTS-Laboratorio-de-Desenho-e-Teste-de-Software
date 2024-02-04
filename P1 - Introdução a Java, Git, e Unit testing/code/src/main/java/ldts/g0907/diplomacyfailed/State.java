package ldts.g0907.diplomacyfailed;


public abstract class State {
    //Playing, Menu,..
    //State pattern -> tem de guardar referência de Game(?)

    protected ViewFactoryInterface gui;

    protected StateObserver observer;

    public void setObserver(StateObserver observer) {
        this.observer = observer;
    }

    public StateObserver getObserver() {
        return observer;
    }

    public State(ViewFactoryInterface gui){
        this.gui = gui;
    }

    public abstract void processInput();
    public abstract void draw();
    public abstract void update();
}
