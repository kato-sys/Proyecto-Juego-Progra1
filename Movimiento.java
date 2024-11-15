
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
                if (item.getTipo().equals("armadura_base") || item.getTipo().equals("armadura_legendaria")) {
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
                //checksurrounding(destinationF, destinationC, habitacion, jugador, enemigo);
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
        }
        //El enemigo se mueve hacia el jugador después de que el jugador se mueva. LLamado método.
        for (int f=0; f<habitacion.length; f++){
          for(int c = 0; c < habitacion[0].length; c++){
            if(habitacion[f][c] == 6){
              moverEnemigoHaciaJugador(habitacion,pPosF,pPosC,enemigo,jugador);
            }
          }
        }
          
      }
    }

private void moverEnemigoHaciaJugador(int[][] habitacion, int pPosF, int pPosC, Enemigo enemigo, Jugador jugador) {
    int ePosF = 0, ePosC = 0;

    // Encontrar la posición del enemigo.
    for (int f = 0; f < habitacion.length; f++) {
        for (int c = 0; c < habitacion[0].length; c++) {
            if (habitacion[f][c] == 6) { // 6 representa al enemigo.
                ePosF = f;
                ePosC = c;
            }
        }
    }

    // Determinar probabilidad de moverse hacia el jugador.
    double probabilidadMover = Math.random();
    if (probabilidadMover > 0.75) return; // 25% de quedarse quieto.

    // Calcular nueva posición hacia el jugador.
    int newEPosF = ePosF;
    int newEPosC = ePosC;

    if (ePosF < pPosF) newEPosF++;
    else if (ePosF > pPosF) newEPosF--;

    if (ePosC < pPosC) newEPosC++;
    else if (ePosC > pPosC) newEPosC--;

    // Verificar si la nueva posición es válida.
    if (newEPosF < 0 || newEPosF >= habitacion.length || newEPosC < 0 || newEPosC >= habitacion[0].length) {
        return; // Fuera de los límites.
    }

    switch (habitacion[newEPosF][newEPosC]) {
        case 0: // Espacio vacío.
            habitacion[ePosF][ePosC] = 0;
            habitacion[newEPosF][newEPosC] = 6;
            break;

        case 4: // Ítem.
            //Anadir metodo para que el Enemigo tenga un inventario ;;
            break;

        case 7: // Debuff.
            enemigo.activarDebuff();
            System.out.println("El enemigo ha sido afectado por un debuff.");
            habitacion[newEPosF][newEPosC] = 6; // Mover enemigo.
            habitacion[ePosF][ePosC] = 0;
            break;

        case 2: // Jugador (inicia combate).
            callCombate.combate(enemigo, jugador, habitacion);
            break;

        default:
            // Si es otro caso, no se mueve.
            return;
    }
}


    private void checksurrounding(int x, int y, int[][] habitacion, Jugador jugador, Enemigo objetivo) {
        int filas = habitacion.length;
        int columnas = habitacion[0].length;
    
        if ((x + 1 < filas && habitacion[x + 1][y] == 6) || 
            (x - 1 >= 0 && habitacion[x - 1][y] == 6) || 
            (y + 1 < columnas && habitacion[x][y + 1] == 6) || 
            (y - 1 >= 0 && habitacion[x][y - 1] == 6)) {
            callCombate.combate(objetivo, jugador, habitacion);
        }
    }

}
