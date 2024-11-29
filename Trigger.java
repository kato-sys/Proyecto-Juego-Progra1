
public class Trigger {

    public static void main(String[] args) {
        Jugador jugador = new Jugador("Player", 100, 8, 0);
        Movimiento movimiento = new Movimiento();
        System.out.println("Bienvenido al juego! Intenta encontrar la salida (/) y esquiva o enfrenta los enemigos (E).");

        movimiento.RecorridoHabitacion(jugador);

        if (jugador.getVida() > 0) {
            System.out.println("¡Has salido de la habitación y ganado el juego!");
        } else {
            System.out.println("Game Over. Intenta de nuevo.");
        } 
    }
}
 
