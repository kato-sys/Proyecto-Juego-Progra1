
import java.util.Scanner;

public class Movimiento {

    Scanner myScanner = new Scanner(System.in);
    Combate callCombate = new Combate();

    public void RecorridoHabitacion(Habitacion habitacionActual, Jugador jugador, Enemigo enemigo) {
    Mazmorra mazmorra = new Mazmorra();
    boolean gameOver = false;

    while (!gameOver) {
        int[][] habitacion = habitacionActual.getHabitacion();
        habitacionActual.ImprimirHabitacion();
        int pPosF = 0; // Posicion fila
        int pPosC = 0; // Posicion columna

        // Daño del debuff
        if (jugador.tieneDebuff()) {
            jugador.recibirDaño(0);
        }
        System.out.println("Vida de jugador: " + jugador.getVida());

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
                if (habitacionActual.arriba == null) {
                    habitacionActual.arriba = mazmorra.generarNuevaHabitacion();
                }
                System.out.println("Entraste por la Puerta Norte a la Siguiente Habitación.");
                habitacionActual = habitacionActual.arriba;
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
                if (habitacionActual.abajo == null) {
                    habitacionActual.abajo = mazmorra.generarNuevaHabitacion();
                }
                System.out.println("Entraste por la Puerta Sur a la Siguiente Habitación.");
                habitacionActual = habitacionActual.abajo;
                break;
            case 9: // Puerta Este
                if (habitacionActual.derecha == null) {
                    habitacionActual.derecha = mazmorra.generarNuevaHabitacion();
                }
                System.out.println("Entraste por la Puerta Este a la Siguiente Habitación.");
                habitacionActual = habitacionActual.derecha;
                break;
            case 10: // Puerta Oeste
                if (habitacionActual.izquierda == null) {
                    habitacionActual.izquierda = mazmorra.generarNuevaHabitacion();
                }
                System.out.println("Entraste por la Puerta Oeste a la Siguiente Habitación.");
                habitacionActual = habitacionActual.izquierda;
                break;
            case 6: // Enemigo
                checksurrounding(destinationF, destinationC, habitacion, jugador, enemigo);
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
        moverEnemigoHaciaJugador(habitacion, pPosF, pPosC, enemigo, jugador);
          
      }
    }


    private void moverEnemigoHaciaJugador(int[][] habitacion, int pPosF, int pPosC, Enemigo enemigo, Jugador jugador){
      //Primero declaramos la posición del enemigo. 
      int ePosF = 0;
      int ePosC = 0;
      
      //Encontrar la posición del enemigo.
      for(int f = 0; f < habitacion.length; f++){
        for (int c = 0; c < habitacion[0].length; c++){
          if(habitacion[f][c] == 6){ 
            //Recordar que el 6 en la matriz representa al enemigo.
            ePosF = f;
            ePosC = c;
          }
        }
      }
      //Luego necesitamos calcular el movimiento que vaya hacia el jugador. 
      int newEPosF = ePosF;
      int newEPosC = ePosC;

      if (ePosF < pPosF) newEPosF++;
      else if (ePosF > pPosF) newEPosF--;

      if(ePosC < pPosC) newEPosC++;
      else if(ePosC > pPosC) newEPosC--;

      //Ahora necesitamos comprobar si la posición es válida, osea, no es pared ni se sale de los límites (Rezo porque funcione a la primera. Oh gran Omnissiah, deidad de los Adeptus Mechanicus, has que este código funcione a la primera.)
      if(habitacion[newEPosF][newEPosC] == 0){
        habitacion[ePosF][ePosC] = 0; //Quita el enemigo de la posición anterior.
        habitacion[newEPosF][newEPosC] = 6; //Mover al enemigo a la nueva posición.
        //Aquí ahora detecta si está cerca que inicie el combate. 
        if(Math.abs(newEPosF - pPosF) + Math.abs(newEPosC - pPosC) == 1){
          callCombate.combate(enemigo,jugador,habitacion);
        }
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
