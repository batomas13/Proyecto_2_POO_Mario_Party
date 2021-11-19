package proyecto2;

public class Juego_Sopa extends JuegoGenerico {
    
    public Juego_Sopa() {
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
    
    public static void main(String[] args){
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

    @Override
    public void setEnemigo(String nombreEnemigo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
