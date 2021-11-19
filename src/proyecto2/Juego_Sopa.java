package proyecto2;

public class Juego_Sopa extends JuegoGenerico {
    Jugador Jugador;
    public Juego_Sopa(Jugador Jugador) {
        this.Jugador = Jugador;
        int randSize = 1 + (int)(Math.random() * ((3 - 1) + 1));
        BoardDisplay game;
        game = switch (randSize) {
            case 1 -> new BoardDisplay(10, 4, Jugador);
            case 2 -> new BoardDisplay(15, 4, Jugador);
            case 3 -> new BoardDisplay(20, 4, Jugador);
            default -> new BoardDisplay(10, 4, Jugador);
        };
        game.buildGrid();
    }
    @Override
    public void setEnemigo(String nombreEnemigo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
