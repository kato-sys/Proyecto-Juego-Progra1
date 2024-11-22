
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
            jugador.recibirDaño(0);
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
                Item item = jugador.getInventario().generarItem();
                System.out.println("Obtuviste el item: " + item.getNombre());
                if (item.getTipo().equals("armadura_base") || item.getTipo().equals("armadura_legendaria")  || item.getTipo().equals("armadura_secreta")) {
                    jugador.setDefensa(item.getPoder());
                } else {
                    jugador.getInventario().addInventario(item, item.getTipo());
                    habitacion[destinationF][destinationC] = 0;
                }
                break;
            case 5: // Arma
                Item arma = jugador.getInventario().generarArma();
                System.out.println("Obtuviste el arma: " + arma.getNombre());
                jugador.setAtaque(arma.getPoder());
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
            case 6: // Enemigo
                System.out.println("¡Enemigo!");
                callCombate.combate(enemigo, jugador, habitacion);
                canGo = false;
                break;
            default:
                // Si es otro número, permitimos el movimiento
                break;
        }

        if (canGo) {
            // Se mueve el jugador a la posicion
            habitacion[destinationF][destinationC] = 2;
            habitacion[pPosF][pPosC] = 0;
            checksurrounding(destinationF, destinationC, habitacion, jugador, enemigo);
            moverEnemigoHaciaJugador(habitacion, pPosF, pPosC, enemigo, jugador);
            checksurrounding(destinationF, destinationC, habitacion, jugador, enemigo);

        }
        //moverEnemigoHaciaJugador(habitacion, pPosF, pPosC, enemigo, jugador);
        
         //El enemigo se mueve hacia el jugador después de que el jugador se mueva. LLamado método.
        /*
        for (int f = 0; f < habitacion.length; f++) { 
            for (int c = 0; c < habitacion[0].length; c++) { 
                if (habitacion[f][c] == 6) { 
                    moverEnemigoHaciaJugador(habitacion, pPosF, pPosC, enemigo, jugador);
                    
                } 
            } 
        }*/
        //checksurrounding(destinationF, destinationC, habitacion, jugador, enemigo); 
      }
    }

    private void moverEnemigoHaciaJugador(int[][] habitacion, int pPosF, int pPosC, Enemigo enemigo, Jugador jugador) {
        int ePosF = -1, ePosC = -1;

        // Encontrar la posición actual del enemigo.
        for (int f = 0; f < habitacion.length; f++) {
            for (int c = 0; c < habitacion[0].length; c++) {
                if (habitacion[f][c] == 6) { // 6 representa al enemigo.
                    ePosF = f;
                    ePosC = c;
                    break;
                }
            }
        }

        // Si no se encuentra un enemigo en la matriz, no hacemos nada.
        if (ePosF == -1 || ePosC == -1) return;

        // Calcular nueva posición hacia el jugador.
        int newEPosF = ePosF;
        int newEPosC = ePosC;

        if (ePosF < pPosF) newEPosF++;
        else if (ePosF > pPosF) newEPosF--;

        if (ePosC < pPosC) newEPosC++;
        else if (ePosC > pPosC) newEPosC--;

        // Verificar si la nueva posicion es valida.
        if (newEPosF >= 0 && newEPosF < habitacion.length &&
            newEPosC >= 0 && newEPosC < habitacion[0].length &&
            habitacion[newEPosF][newEPosC] == 0) {
            habitacion[ePosF][ePosC] = 0; // Liberar posicion anterior.
            habitacion[newEPosF][newEPosC] = 6; // Mover enemigo.
        }   
    }
    private void checksurrounding(int x, int y, int[][] habitacion, Jugador jugador, Enemigo enemigo) {

        int filas = habitacion.length;
        int columnas = habitacion[0].length;

        // Revisar adyacentes y diagonales
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < dx.length; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx >= 0 && nx < filas && ny >= 0 && ny < columnas && habitacion[nx][ny] == 6) {
                callCombate.combate(enemigo, jugador, habitacion);
                if (enemigo.getVida() <= 0) { // Eliminar enemigo tras combate
                    habitacion[nx][ny] = 0;
                }
                break;
            }
        }    
    }
}
