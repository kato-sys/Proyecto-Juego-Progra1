
import java.util.Scanner;

public class Movimiento {

    Scanner myScanner = new Scanner(System.in);
    Combate callCombate = new Combate();
    Mazmorra mazmorra = new Mazmorra();
    boolean gameOver = false;
    GeneracionHabitacion HabitacionGenerada = new GeneracionHabitacion();
    Enemigo[] enemigosEnCuarto = HabitacionGenerada.getEnemigos();

    
    Habitacion habitacionActual = HabitacionGenerada.getHabitacion();

    public void RecorridoHabitacion(Jugador jugador) {
    HabitacionGenerada.LlenarHabitacion();
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
            moverEnemigos(habitacion, destinationF, destinationC, HabitacionGenerada);
            checksurrounding(destinationF, destinationC, habitacion, jugador, HabitacionGenerada);
            
        }
      }
    }

    private void checksurrounding(int x, int y, int[][] habitacion, Jugador jugador, GeneracionHabitacion HabitacionGenerada) {
<<<<<<< HEAD
        for(int f = x-1; f <= x; f++)
        {
            for(int c = y-1; c <= y; c++)
            {
                if(habitacion[f][c] == 6)
                {
                    callCombate.combate(enemigosEnCuarto, jugador, habitacion, HabitacionGenerada.getEnemigoPorPosicion(f, c), HabitacionGenerada);
                    }
            }
        }
    
        // Solo verifica casillas adyacentes
    //int[] dx = {-1, 1, 0, 0};
    //int[] dy = {0, 0, -1, 1};
    //int enemigoIndex = 0;
    //for (int i = 0; i < dx.length; i++) {
    //    int nx = x + dx[i];
    //    int ny = y + dy[i];

        // Verificar límites y buscar enemigos adyacentes
    //    if (nx >= 0 && nx < habitacion.length &&
    //        ny >= 0 && ny < habitacion[0].length &&
    //        habitacion[nx][ny] == 6) { // 6 representa al enemigo
    //        int enemigoActual = HabitacionGenerada.getEnemigoPorPosicion(nx, ny);
    //        callCombate.combate(HabitacionGenerada.getEnemigos(), jugador, habitacion, enemigoActual, HabitacionGenerada);
        }
    //}
=======
        // Solo verifica casillas adyacentes
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        int enemigoIndex = 0;
        for (int i = 0; i < dx.length; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            // Verificar límites y buscar enemigos adyacentes
            if (nx >= 0 && nx < habitacion.length &&
                ny >= 0 && ny < habitacion[0].length &&
                habitacion[nx][ny] == 6) { // 6 representa al enemigo
                int enemigoActual = HabitacionGenerada.getEnemigoPorPosicion(nx, ny);
                callCombate.combate(HabitacionGenerada.getEnemigos(), jugador, habitacion, enemigoActual, HabitacionGenerada);
            }
        }
    }

    private void moverEnemigos(int[][] habitacion, int jugadorF, int jugadorC, GeneracionHabitacion HabitacionGenerada) {
    for (Enemigo enemigo : HabitacionGenerada.getEnemigos()) {
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
            }
        }
    }
}





>>>>>>> e60b0edeb9d300296f98351b81c6b3f8872050e0
}
