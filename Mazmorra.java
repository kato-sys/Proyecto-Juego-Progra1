public class Mazmorra {
    Habitacion habitacionOriginal; // Starting room
    Habitacion habitacionActual;

    public Mazmorra() {
        habitacionOriginal = new Habitacion();
        habitacionActual = habitacionOriginal;
    }

    // Generate a new room when the player goes through a door
    private Habitacion crearHabitacion(int direction) {
        Habitacion habitacionNueva = new Habitacion();
        habitacionActual.connectar(habitacionNueva, direction);
        return habitacionNueva;
    }

    // Move to a connected room or create a new one if it doesn't exist
    public Habitacion irSiguiente(int direction) {
        if (habitacionActual.vecinos[direction] == null) {
            // Create a new room if no room exists in the given direction
            habitacionActual.vecinos[direction] = crearHabitacion(direction);
        }
        habitacionActual = habitacionActual.vecinos[direction];
        // Place player at the opposite door in the new room
        System.out.println("Has pasado a otra habitacion");
        return habitacionActual;
    }

    public void ImprimirHabitacion() {
        for (int[] fila : habitacionActual.habitacion) {
            for (int celda : fila) {
                switch (celda) {
                    case 1 ->
                        System.out.print("#"); // Pared
                    case 2 ->
                        System.out.print("@"); // Player
                    case 3 ->
                        System.out.print("/"); // Puerta Norte
                    case 4 ->
                        System.out.print("+"); // Item
                    case 5 ->
                        System.out.print("}"); // arma
                    case 6 ->
                        System.out.print("E"); // Enemigo
                    case 7 ->
                        System.out.print("D"); // Debuff
                    case 8 ->
                        System.out.print("/"); // Puerta Sur
                    case 9 ->
                        System.out.print("/"); // Puerta Este
                    case 10 ->
                        System.out.print("/"); //Puerta Oeste
                    default ->
                        System.out.print("."); // piso
                }
            }
            System.out.println();
        }
    }
}
