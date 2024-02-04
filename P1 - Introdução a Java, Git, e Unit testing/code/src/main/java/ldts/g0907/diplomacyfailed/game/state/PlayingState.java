package ldts.g0907.diplomacyfailed.game.state;

import ldts.g0907.diplomacyfailed.State;
import ldts.g0907.diplomacyfailed.ViewFactoryInterface;
import ldts.g0907.diplomacyfailed.game.controller.PlayingController;
import ldts.g0907.diplomacyfailed.game.model.Battlefield;
import ldts.g0907.diplomacyfailed.game.view.PlayingView;
import ldts.g0907.diplomacyfailed.menu.state.EndState;
import ldts.g0907.diplomacyfailed.menu.state.PauseState;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayingState extends State {

    private final PlayingView playing_view;
    private final Battlefield battlefield;
    private final PlayingController controller;
    private int frame_counter;

    public PlayingState(ViewFactoryInterface gui, String map) {
        super(gui);
        playing_view = gui.createPlayingView();

        if(map.equals("randomMap")) createRandomMap();

        String filename = "code/src/main/resources/" + map + ".txt";
        battlefield = new Battlefield(gui.getWidth(), gui.getHeight(), filename);
        controller = new PlayingController(battlefield);
        frame_counter = 0;
    }

    @Override
    public void processInput() {
        playing_view.processInput(controller);
    }

    @Override
    public void draw()  {
        playing_view.draw(battlefield);
    }

    @Override
    public void update() {
        boolean pause = controller.getPause();
        if(pause){
            controller.setPause(false);
            observer.changeState(new PauseState(gui, this));
        }
        if(controller.update(battlefield)){ //checks if the game ended
            frame_counter++;
        }

        if(frame_counter == 50){
            String winner = controller.getWinner(battlefield);
            observer.changeState(new EndState(gui, winner));

        }
    }

    public boolean activeShell(){
        return battlefield.activeShell();
    }

    private List<Integer> generateMapValues(){
        List<Integer> mapValues = new ArrayList<>();
        Random r = new Random();
        int min = 1;
        int max = 30;
        int start = r.nextInt(max-min) + min;
        mapValues.add(start);

        List<Integer> l = new ArrayList<>(List.of());

        String str0 = "0".repeat(100);
        String str1 = "1".repeat(35);
        String str2 = "2".repeat(15);
        String str = str0 + str1 + str2;
        String strn = str1 + str2;
        for (char c: str.toCharArray()) {
            l.add(Character.getNumericValue(c));
        }

        for (char c: strn.toCharArray()) {
            l.add(-1 * Character.getNumericValue(c));
        }

        for(int i = 1; i < 100; i++){
            int c = l.get(r.nextInt(l.size()));
            int n = mapValues.get(i-1) + c;
            if(n != 0)
                mapValues.add(n);
            else
                mapValues.add(1);
        }
        return mapValues;
    }

    private List<Integer> generateTreeValues(List<Integer> mapValues){
        List<Integer> treeValues = new ArrayList<>();
        Random r = new Random();
        int min = 5;
        int max = 30;
        List<Integer> aux = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            int n = r.nextInt(max-min) + min;
            aux.add(n);
            min += 30;
            max += 30;
        }
        for(int i = 0; i < aux.size(); i++){
            treeValues.add(aux.get(i));
            treeValues.add(mapValues.get(aux.get(i)) + 1);
        }
        return treeValues;
    }

    private List<Integer> generatePlayerValues(List<Integer> mapValues){
        List<Integer> playerValues = new ArrayList<>();
        Random r = new Random();
        int min = 7;
        int max = 50;
        List<Integer> aux = new ArrayList<>();
        for(int i = 0; i < 2; i++){
            int n = r.nextInt(max-min) + min;
            aux.add(n);
            min = max + 1;
            max = 93;
        }
        for(int i = 0; i < aux.size(); i++){
            playerValues.add(aux.get(i));
            int playerHeight = mapValues.get(aux.get(i));
            for(int j = 1; j <= 7; j++){
                if(mapValues.get(aux.get(i) + j) > playerHeight)
                    playerHeight = mapValues.get(aux.get(i) + j);
            }
            playerValues.add(playerHeight + 1);
        }
        return playerValues;
    }

    private void createRandomMap(){
        List<Integer> mapValues = generateMapValues();
        List<Integer> treeValues = generateTreeValues(mapValues);
        List<Integer> playerValues = generatePlayerValues(mapValues);

        try {
            FileWriter myWriter = new FileWriter("code/src/main/resources/randomMap.txt");
            for(int i = 0; i < mapValues.size(); i++){
                if(i != mapValues.size()-1)
                    myWriter.write(mapValues.get(i) +",");
                else
                    myWriter.write(mapValues.get(i) + "\n");
            }

            for(int i = 0; i < treeValues.size(); i++){
                if(i % 2 == 0)
                    myWriter.write("(" + treeValues.get(i) + ",");
                else
                    if(i != treeValues.size()-1)
                        myWriter.write(treeValues.get(i) + ");");
                    else
                        myWriter.write(treeValues.get(i) + ")\n");
            }

            for(int i = 0; i < playerValues.size(); i++){
                if(i % 2 == 0)
                    myWriter.write("(" + playerValues.get(i) + ",");
                else
                if(i != treeValues.size()-1)
                    myWriter.write(playerValues.get(i) + ");");
                else
                    myWriter.write(playerValues.get(i) + ")\n");
            }

            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
