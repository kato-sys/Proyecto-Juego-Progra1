public class Mazmorra {
    Habitacion habitacionOriginal; 
    Habitacion habitacionActual;

    public Mazmorra() {
        habitacionOriginal = new Habitacion();
        habitacionActual = habitacionOriginal;
        habitacionOriginal.setHabitacionInicial(true);
        habitacionOriginal.colocarJugadorHabitacionBase();
    }

    // Método de creación de una posición. 
    private Habitacion crearHabitacion(int direction) {
        Habitacion habitacionNueva = new Habitacion();
        habitacionActual.connectar(habitacionNueva, direction);
        return habitacionNueva;
    }

    public Habitacion irSiguiente(int direction) {
        // Limpiar la posición del jugador en la habitación actual
        for (int i = 0; i < habitacionActual.habitacion.length; i++) {
            for (int j = 0; j < habitacionActual.habitacion[0].length; j++) {
                if (habitacionActual.habitacion[i][j] == 2) { // '2' representa al jugador
                    habitacionActual.habitacion[i][j] = 0; // Limpia la posición
                    break;
                }
            }
        }

        // Encontrar la posición actual del jugador
        int playerX = -1, playerY = -1;
        for (int i = 0; i < habitacionActual.habitacion.length; i++) {
            for (int j = 0; j < habitacionActual.habitacion[0].length; j++) {
                if (habitacionActual.habitacion[i][j] == 2) {
                    playerX = i;
                    playerY = j;
                    break;
                }
            }
            if (playerX != -1 && playerY != -1) break;
        }

        // Replace the player with the door
        if (playerX != -1 && playerY != -1) {
            switch (direction) {
                case 0: // Norte
                    habitacionActual.habitacion[playerX][playerY] = 3;
                    break;
                case 1: // Sur
                    habitacionActual.habitacion[playerX][playerY] = 8;
                    break;
                case 2: // Este
                    habitacionActual.habitacion[playerX][playerY] = 9;
                    break;
                case 3: // Oeste
                    habitacionActual.habitacion[playerX][playerY] = 10;
                    break;
            }
        }

        // Move to the next room
        if (habitacionActual.vecinos[direction] == null) {
            Habitacion nuevaHabitacion = crearHabitacion(direction);
            habitacionActual.connectar(nuevaHabitacion, direction);
        }

        int opposite = oppositeDirection(direction);
        habitacionActual = habitacionActual.vecinos[direction];
        habitacionActual.colocarJugador(opposite); // Place player based on entry

        System.out.println("Has pasado a otra habitacion");
        return habitacionActual;
    }

private int oppositeDirection(int direction) {
    switch (direction) {
        case 0: return 1; // Norte
        case 1: return 0; // Sur
        case 2: return 3; // Este
        case 3: return 2; // Oeste
        default: return -1; 
    }
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
