
import java.util.Scanner;

public class Movimiento {

    Scanner myScanner = new Scanner(System.in);
    Combate callCombate = new Combate();

    public void RecorridoHabitacion(Jugador jugador, Enemigo enemigo) {
    Mazmorra mazmorra = new Mazmorra();
    boolean gameOver = false;
    GeneracionHabitacion HabitacionGenerada = new GeneracionHabitacion();
    HabitacionGenerada.LlenarHabitacion();
    Habitacion habitacionActual = HabitacionGenerada.getHabitacion();

    while (!gameOver) {
        int[][] habitacion = habitacionActual.tamano();
        HabitacionGenerada.ImprimirHabitacion();
        int pPosF = 0; // Posicion fila
        int pPosC = 0; // Posicion columna

        // Daño del debuff
        if (jugador.tieneDebuff()) {
            jugador.recibirDaño(5);
        }
        System.out.println("Vida de jugador: " + jugador.getVida());
        //Revisar si el jugador está muerto (por el debuff)
        if(jugador.getVida() <= 0){
          System.out.println("¡Has muerto! :D FIN DEL JUEGO.");
          gameOver = true;
          continue;
        }
        // Se busca la posicion del jugador en el cuarto
        for (int f = 0; f < habitacion.length; f++) {
            for (int c = 0; c < habitacion[0].length; c++) {
                if (habitacion[f][c] == 2) {
                    pPosF = f;
                    pPosC = c;
                }
            }
        }

        int destinationF = pPosF;
        int destinationC = pPosC;
        boolean canGo = true;

        System.out.println("Ingrese 'w' (arriba), 'a' (izquierda), 's' (abajo) o 'd' (derecha): ");
        char movimiento = myScanner.next().charAt(0);
        switch (movimiento) {
            case 'w':
                destinationF = pPosF - 1;
                break;
            case 'a':
                destinationC = pPosC - 1;
                break;
            case 's':
                destinationF = pPosF + 1;
                break;
            case 'd':
                destinationC = pPosC + 1;
                break;
            case 'e':
                System.out.println("Salida de emergencias activada. Has salido del juego.");
                gameOver = true;
                break;

            default:
                System.out.println("Opción inválida. Intente de nuevo.");
                continue; // Salta al próximo ciclo del bucle
        }

        if (destinationF < 0 || destinationF >= habitacion.length || destinationC < 0 || destinationC >= habitacion[0].length) {
            System.out.println("Movimiento fuera de los límites. Intente de nuevo.");
            continue;
        }

        switch (habitacion[destinationF][destinationC]) {
            case 1: // Pared
                System.out.println("No puede avanzar más porque hay una pared.");
                canGo = false;
                break;
            case 3: // Puerta Norte
                habitacionActual = mazmorra.irSiguiente("norte", habitacionActual.arriba);
                break;
            case 4:  // Item
                Item item = jugador.getInventario().recogerGenerarItem();
                System.out.println("Obtuviste el item: " + item.getNombre());
                habitacion[destinationF][destinationC] = 0;
                break;
            case 5: // Arma
                Item arma = jugador.getInventario().recogerGenerarArma(jugador);
                System.out.println("Obtuviste el arma: " + arma.getNombre());
                habitacion[destinationF][destinationC] = 0;
                break;
            case 7:
                jugador.activarDebuff(); // Debuff
                break;
            case 8: // Puerta Sur
            habitacionActual = mazmorra.irSiguiente("sur", habitacionActual.abajo);
                break;
            case 9: // Puerta Este
            habitacionActual = mazmorra.irSiguiente("este", habitacionActual.derecha);
                break;
            case 10: // Puerta Oeste
            habitacionActual = mazmorra.irSiguiente("oeste", habitacionActual.izquierda);
                break;
            default:
                // Si es otro número, permitimos el movimiento
                break;
        }

        if (canGo) {
            // Se mueve el jugador a la posicion
            habitacion[destinationF][destinationC] = 2;
            habitacion[pPosF][pPosC] = 0;
            checksurrounding(destinationF, destinationC, habitacion, jugador, HabitacionGenerada);
            // Mover enemigos después de verificar combates
            if (HabitacionGenerada.quedanEnemigos()) {
                moverEnemigos(habitacion, pPosF, pPosC, HabitacionGenerada);
            }
            checksurrounding(destinationF, destinationC, habitacion, jugador, HabitacionGenerada);
        }
      }
    }

private void checksurrounding(int x, int y, int[][] habitacion, Jugador jugador, GeneracionHabitacion HabitacionGenerada) {
    // Solo verifica casillas adyacentes
    int[] dx = {-1, 1, 0, 0};
    int[] dy = {0, 0, -1, 1};

    for (int i = 0; i < dx.length; i++) {
        int nx = x + dx[i];
        int ny = y + dy[i];

        // Verificar límites y buscar enemigos adyacentes
        if (nx >= 0 && nx < habitacion.length &&
            ny >= 0 && ny < habitacion[0].length &&
            habitacion[nx][ny] == 6) { // 6 representa al enemigo
            Enemigo enemigoActual = HabitacionGenerada.getEnemigoPorPosicion(nx, ny);

            if (enemigoActual != null) {
                System.out.println("¡Has encontrado un enemigo!");
                callCombate.combate(enemigoActual, jugador, habitacion);

                // Si el enemigo muere, actualizar la habitación
                if (enemigoActual.getVida() <= 0) {
                    habitacion[nx][ny] = 0;
                    HabitacionGenerada.eliminarEnemigo(enemigoActual);
                }
            }
        }
    }
}


private void moverEnemigos(int[][] habitacion, int jugadorF, int jugadorC, GeneracionHabitacion HabitacionGenerada) {
    for (int f = 0; f < habitacion.length; f++) {
        for (int c = 0; c < habitacion[0].length; c++) {
            if (habitacion[f][c] == 6) {
                Enemigo enemigoActual = HabitacionGenerada.getEnemigoPorPosicion(f, c);
                if (enemigoActual != null) {
                    moverEnemigoIndividual(habitacion, f, c, jugadorF, jugadorC, enemigoActual);
                }
            }
        }
    }
}

private void moverEnemigoIndividual(int[][] habitacion, int ePosF, int ePosC, int jugadorF, int jugadorC, Enemigo enemigo) {
    int newEPosF = ePosF;
    int newEPosC = ePosC;

    // Decide si moverse verticalmente u horizontalmente
    if (Math.abs(ePosF - jugadorF) > Math.abs(ePosC - jugadorC)) {
        newEPosF += (ePosF < jugadorF) ? 1 : -1; // Moverse verticalmente
    } else {
        newEPosC += (ePosC < jugadorC) ? 1 : -1; // Moverse horizontalmente
    }

    // Verificar si la nueva posición es válida y está vacía
    if (newEPosF >= 0 && newEPosF < habitacion.length &&
        newEPosC >= 0 && newEPosC < habitacion[0].length &&
        habitacion[newEPosF][newEPosC] == 0) {
        habitacion[ePosF][ePosC] = 0; // Liberar posición anterior
        habitacion[newEPosF][newEPosC] = 6; // Ocupa nueva posición
    }
}




}
