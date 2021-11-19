package proyecto2;

public class Juego_Sopa extends JuegoGenerico {
    
    public Juego_Sopa() {
        int randSize = 1 + (int)(Math.random() * ((3 - 1) + 1));
        BoardDisplay game;
        game = switch (randSize) {
            case 1 -> new BoardDisplay(10, 4);
            case 2 -> new BoardDisplay(15, 4);
            case 3 -> new BoardDisplay(20, 4);
            default -> new BoardDisplay(10, 4);
        };
        game.buildGrid();
    }
    
    public static void main(String[] args){
        int randSize = 1 + (int)(Math.random() * ((3 - 1) + 1));
        BoardDisplay game;
        game = switch (randSize) {
            case 1 -> new BoardDisplay(10, 4);
            case 2 -> new BoardDisplay(15, 4);
            case 3 -> new BoardDisplay(20, 4);
            default -> new BoardDisplay(10, 4);
        };
        game.buildGrid();
    }

    @Override
    public void setEnemigo(String nombreEnemigo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
