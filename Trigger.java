
public class Trigger {

    public static void main(String[] args) {
        
        Jugador jugador = new Jugador("Player", 100, 8, 0);
        Movimiento movimiento = new Movimiento();
        System.out.println("Bienvenido a esta aventura del proyecto de programación... Misteriosamente te encuentras en una mazmorra. ¡Necesitas volver a tu hogar! ¡La ECCI te espera!");

        movimiento.RecorridoHabitacion(jugador);

        if (jugador.getVida() > 0) {
            System.out.println("¡Has salido de la habitación y ganado el juego!");
        } else {
            System.out.println("Game Over. Intenta de nuevo.");
        } 
    }
}
 
