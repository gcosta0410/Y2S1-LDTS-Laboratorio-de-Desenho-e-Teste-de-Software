package ldts.g0907.diplomacyfailed.menu.model;

public class Menu {

    private final String[] text;
    private final String textColor = "#000000"; //black
    private final String BUTTON_COLOR = "#A95AEC"; //purple
    private final String SELECTED_COLOR = "#FF54F6"; //light pink
    private String button1;
    private String button1Color = SELECTED_COLOR;
    private String button2;
    private String button2Color = BUTTON_COLOR;
    private String button3 = null;
    private String button3Color = BUTTON_COLOR;
    private int selectedButton = 1;

    public Menu(String[] TEXT, String BUTTON1, String BUTTON2){
        this.text = TEXT;
        this.button1 = ">" + BUTTON1;
        this.button2 = BUTTON2;
    }

    public Menu(String[] TEXT, String BUTTON1, String BUTTON2, String BUTTON3){
        this.text = TEXT;
        this.button1 = ">" + BUTTON1;
        this.button2 = BUTTON2;
        this.button3 = BUTTON3;
    }

    public String[] getText() {
        return text;
    }

    public String getTextColor(){
        return textColor;
    }

    public String getButton1() {
        return button1;
    }

    public String getButton1Color(){
        return button1Color;
    }

    public String getButton2() {
        return button2;
    }

    public String getButton2Color(){
        return button2Color;
    }

    public String getButton3(){return button3;}

    public String getButton3Color(){
        return button3Color;
    }

    public void changeOptionUp(){
        switch (selectedButton) {
            case 1 -> {
                if (button3 == null) {
                    selectedButton = 2;
                    button2Color = SELECTED_COLOR;
                    button2 = ">" + button2;
                } else {
                    selectedButton = 3;
                    button3Color = SELECTED_COLOR;
                    button3 = ">" + button3;
                }
                button1Color = BUTTON_COLOR;
                button1 = button1.replace(">", "");
            }
            case 2 -> {
                selectedButton = 1;
                button1Color = SELECTED_COLOR;
                button1 = ">" + button1;
                button2Color = BUTTON_COLOR;
                button2 = button2.replace(">", "");
            }
            case 3 -> {
                selectedButton = 2;
                button2Color = SELECTED_COLOR;
                button2 = ">" + button2;
                button3Color = BUTTON_COLOR;
                button3 = button3.replace(">", "");
            }
        }
    }

    public void changeOptionDown(){
        switch (selectedButton) {
            case 1 -> {
                selectedButton = 2;
                button2Color = SELECTED_COLOR;
                button2 = ">" + button2;
                button1Color = BUTTON_COLOR;
                button1 = button1.replace(">", "");
            }
            case 2 -> {
                if (button3 == null) {
                    selectedButton = 1;
                    button1Color = SELECTED_COLOR;
                    button1 = ">" + button1;
                } else {
                    selectedButton = 3;
                    button3Color = SELECTED_COLOR;
                    button3 = ">" + button3;
                }
                button2Color = BUTTON_COLOR;
                button2 = button2.replace(">", "");
            }
            case 3 -> {
                selectedButton = 1;
                button1Color = SELECTED_COLOR;
                button1 = ">" + button1;
                button3Color = BUTTON_COLOR;
                button3 = button3.replace(">", "");
            }
        }
    }

    public String selectOption(){
        return switch (selectedButton) {
            case 1 -> button1;
            case 2 -> button2;
            case 3 -> button3;
            default -> throw new IllegalStateException("Unexpected value: " + selectedButton); //intellij suggestion, this is never possible anyway
        };
    }
}
