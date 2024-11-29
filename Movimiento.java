
import java.util.Scanner;

public class Movimiento {
    Scanner myScanner = new Scanner(System.in);
    Combate callCombate = new Combate();
    Mazmorra mazmorra = new Mazmorra();
    boolean gameOver = false;
    public void RecorridoHabitacion(Jugador jugador) {
        Habitacion habitacion = mazmorra.habitacionActual;
    while (!gameOver) {
        int[][] habitacionMatriz = habitacion.habitacion;
        mazmorra.ImprimirHabitacion();
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
        for (int f = 0; f < habitacionMatriz.length; f++) {
            for (int c = 0; c < habitacionMatriz[0].length; c++) {
                if (habitacionMatriz[f][c] == 2) {
                    pPosF = f;
                    pPosC = c;
                }
            }
        }
        jugador.ultimaPosicionJugadorF = pPosF;
        jugador.ultimaPosicionJugadorC = pPosC;
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

        if (destinationF < 0 || destinationF >= habitacionMatriz.length || destinationC < 0 || destinationC >= habitacionMatriz[0].length) {
            System.out.println("Movimiento fuera de los límites. Intente de nuevo.");
            continue;
        }
        
        switch (habitacionMatriz[destinationF][destinationC]) {
            case 1: // Pared
                canGo = false;
                System.out.println("No puede avanzar más porque hay una pared.");
                break;
            case 3: // Puerta Norte
                canGo = false;
                habitacion = mazmorra.irSiguiente(0);
                canGo = false;
                break;
            case 4:  // Item
                Item item = jugador.getInventario().recogerGenerarItem();
                System.out.println("Obtuviste el item: " + item.getNombre());
                habitacionMatriz[destinationF][destinationC] = 0;
                break;
            case 5: // Arma
                Item arma = jugador.getInventario().recogerGenerarArma(jugador);
                System.out.println("Obtuviste el arma: " + arma.getNombre());
                habitacionMatriz[destinationF][destinationC] = 0;
                break;
            case 7:
                jugador.activarDebuff(); // Debuff
                break;
            case 8: // Puerta Sur
                canGo = false;
                habitacion = mazmorra.irSiguiente(1);
                break;
            case 9: // Puerta Este
                canGo = false;
                habitacion = mazmorra.irSiguiente(2);
                break;
            case 10: // Puerta Oeste
                canGo = false;
                habitacion = mazmorra.irSiguiente(3);
                break;
            default:
                // Si es otro número, permitimos el movimiento
                break;
        }

        if (canGo) {
            // Se mueve el jugador a la posicion
            habitacionMatriz[destinationF][destinationC] = 2;
            habitacionMatriz[pPosF][pPosC] = 0;
            checksurrounding(destinationF, destinationC, habitacionMatriz, jugador, habitacion);
            // Mover enemigos después de verificar combates
            moverEnemigos(habitacionMatriz, destinationF, destinationC, habitacion);
            checksurrounding(destinationF, destinationC, habitacionMatriz, jugador, habitacion);
            
        }
      }
    }

    private void checksurrounding(int x, int y, int[][] habitacion, Jugador jugador, Habitacion HabitacionGenerada) {
        // Solo verifica casillas adyacentes
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
        //int enemigoIndex = 0;
        for (int i = 0; i < dx.length; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            // Verificar límites y buscar enemigos adyacentes
            if (nx >= 0 && nx < habitacion.length && ny >= 0 && ny < habitacion[0].length && habitacion[nx][ny] == 6) { // 6 representa al enemigo
                int enemigoActual = HabitacionGenerada.getEnemigoPorPosicion(nx, ny);
                callCombate.combate(HabitacionGenerada.enemigos, jugador, habitacion, enemigoActual, HabitacionGenerada);
            }
        }
    }

private void moverEnemigos(int[][] habitacion, int jugadorF, int jugadorC, Habitacion HabitacionGenerada) {
    for (Enemigo enemigo : HabitacionGenerada.enemigos) {
        if (enemigo != null && enemigo.getVida() > 0) {
            int enemigoF = enemigo.getPosFila();
            int enemigoC = enemigo.getPosColumna();

            // Calculamos las diferencias
            int deltaF = jugadorF - enemigoF;
            int deltaC = jugadorC - enemigoC;

            // Decidir el movimiento basado en la distancia
            int nuevaF = enemigoF;
            int nuevaC = enemigoC;

            if (Math.abs(deltaF) > Math.abs(deltaC)) {
                // Priorizar movimiento vertical
                nuevaF += (deltaF > 0) ? 1 : -1;
            } else {
                // Priorizar movimiento horizontal
                nuevaC += (deltaC > 0) ? 1 : -1;
            }

            // Validar si la nueva posición es válida
            if (nuevaF >= 0 && nuevaF < habitacion.length &&
                nuevaC >= 0 && nuevaC < habitacion[0].length &&
                habitacion[nuevaF][nuevaC] == 0) { // Espacio vacío
                // Actualizar posición del enemigo en la matriz
                habitacion[enemigoF][enemigoC] = 0;
                habitacion[nuevaF][nuevaC] = 6;

                // Actualizar posición del enemigo
                enemigo.setPosFila(nuevaF);
                enemigo.setPosColumna(nuevaC);

                // Verificar la nueva casilla
                verificarCasillaEnemigo(habitacion, nuevaF, nuevaC, enemigo);
            }
        }
    }
}
private void verificarCasillaEnemigo(int[][] habitacion, int fila, int columna, Enemigo enemigo) {
    switch (habitacion[fila][columna]) {
        case 4: // Ítem
            Item item = enemigo.getInventario().recogerGenerarItem();
            System.out.println(enemigo.getNombre() + " recogió el ítem: " + item.getNombre());
            habitacion[fila][columna] = 0; // Limpiar la casilla
            break;
        case 7: // Debuff
            enemigo.activarDebuff();
            System.out.println(enemigo.getNombre() + " ha activado un debuff.");
            break;
        default:
            // No pasa nada si la casilla está vacía o no tiene interacción
            break;
    }
}






}
