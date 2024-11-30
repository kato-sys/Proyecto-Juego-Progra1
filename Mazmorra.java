public class Mazmorra {
    Habitacion habitacionOriginal; 
    Habitacion habitacionActual;
    private double probabilidadJefe = 0.10; // Inicial 10%
    private double probabilidadSalida = 0.05; // Inicial 5%


    public Mazmorra() {
        habitacionOriginal = new Habitacion();
        habitacionActual = habitacionOriginal;
        habitacionOriginal.setHabitacionInicial(true);
        habitacionOriginal.colocarJugadorHabitacionBase();
    }

    public void ImprimirMapa() {
        String norte = "N";
        String sur = "N";
        String este = "N";
        String oeste = "N";

        // Verificar vecinos
        if (habitacionActual.vecinos[0] != null) {
            norte = habitacionActual.vecinos[0].getHabitacionSalida() ? "S" :
            habitacionActual.vecinos[0].getHabitacionJefe() ? "B" :
            String.valueOf(habitacionActual.vecinos[0].getNumeroHabitacion());
        }
        if (habitacionActual.vecinos[1] != null) {
            sur = habitacionActual.vecinos[1].getHabitacionSalida() ? "S" :
            habitacionActual.vecinos[1].getHabitacionJefe() ? "B" :
            String.valueOf(habitacionActual.vecinos[1].getNumeroHabitacion());
        }
        if (habitacionActual.vecinos[2] != null) {
            este = habitacionActual.vecinos[2].getHabitacionSalida() ? "S" :
            habitacionActual.vecinos[2].getHabitacionJefe() ? "B" :
            String.valueOf(habitacionActual.vecinos[2].getNumeroHabitacion());
        }
        if (habitacionActual.vecinos[3] != null) {
            oeste = habitacionActual.vecinos[3].getHabitacionSalida() ? "S" :
            habitacionActual.vecinos[3].getHabitacionJefe() ? "B" :
            String.valueOf(habitacionActual.vecinos[3].getNumeroHabitacion());
        }

        // Imprimir el mapa
        System.out.println("     " + norte);
        System.out.println("     |");
        System.out.println("[" + oeste + "]-[" + habitacionActual.getNumeroHabitacion() + "]-[" + este + "]");
        System.out.println("     |");
        System.out.println("     " + sur);
    }




    // Método de creación de una posición. 
    private Habitacion crearHabitacion(int direction) {
    Habitacion habitacionNueva = new Habitacion();
    
    // Verificar si esta será la habitación del jefe o salida
    if (!habitacionNueva.getHabitacionJefe() && probabilidadJefe >= Math.random()) {
        habitacionNueva.setHabitacionJefe(true);
        System.out.println("Habitación " + habitacionNueva.getNumeroHabitacion() + " es la habitación del jefe.");
        probabilidadJefe = 0; // Resetea para que no haya más jefes
    } else if (!habitacionNueva.getHabitacionSalida() && probabilidadSalida >= Math.random()) {
        habitacionNueva.setHabitacionSalida(true);
        System.out.println("Habitación " + habitacionNueva.getNumeroHabitacion() + " es la habitación de salida.");
        probabilidadSalida = 0; // Resetea para que no haya más salidas
    }

    // Incrementar las probabilidades para las siguientes habitaciones
    probabilidadJefe += 0.07; // Incremento del 7%
    probabilidadSalida += 0.05; // Incremento del 5%

    // Conectar la habitación
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

        // Mover a la siguiente habitación
        if (habitacionActual.vecinos[direction] == null) {
            Habitacion nuevaHabitacion = crearHabitacion(direction);
            habitacionActual.connectar(nuevaHabitacion, direction);
        }

        int opposite = oppositeDirection(direction);
        habitacionActual = habitacionActual.vecinos[direction];
        habitacionActual.colocarJugador(opposite); // Colocar al jugador, dependiendo de la puerta donde salió.
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
