package proyecto2;

public class MainSopa {
    public static void main(String args[]){
        int randSize = 1 + (int)(Math.random() * ((3 - 1) + 1));
        BoardDisplay game;
        switch (randSize) {
            case 1:
                game = new BoardDisplay(10, 4);
                break;
            case 2:
                game = new BoardDisplay(15, 4);
                break;
            case 3:
                game = new BoardDisplay(20, 4);
                break;
            default:
                game = new BoardDisplay(10, 4);
                break;
        }
        game.buildGrid();
    }
}
