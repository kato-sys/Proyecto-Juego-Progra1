public class Trigger {
    public static void main(String[] args) {
        int[][] habitacion = new int[9][9];
        
        Habitacion habitacionObj = new Habitacion();
        Jugador jugador = new Jugador("Player", 100, 8, 0);
        Enemigo enemigo = new Enemigo("Goblin", 20, 5, 2);
        Movimiento movimiento = new Movimiento();

        habitacionObj.GeneracionHabitacion(habitacion, null, null, null, null);
        System.out.println("Bienvenido al juego! Intenta encontrar la salida (/) y esquiva o enfrenta los enemigos (E).");

        movimiento.RecorridoHabitacion(habitacion, jugador, enemigo);

        if (jugador.getVida() > 0) {
            System.out.println("¡Has salido de la habitación y ganado el juego!");
        } else {
            System.out.println("Game Over. Intenta de nuevo.");
        }
    }
}
