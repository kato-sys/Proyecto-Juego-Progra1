import java.util.Scanner;

public class Movimiento {
    Scanner myScanner = new Scanner(System.in);
    Combate callCombate = new Combate();
    Habitacion callHabitacion = new Habitacion(); 

    public void RecorridoHabitacion(Habitacion habitacionObj, Jugador jugador, Enemigo enemigo) {
        int[][] habitacion = habitacionObj.getHabitacion();
        System.out.println("Habitación ");
        boolean gameOver = false;

        while (!gameOver) {
            habitacionObj.ImprimirHabitacion();
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
            String movimiento = myScanner.nextLine();
            switch (movimiento) {
                case "w":
                    destinationF = pPosF - 1;
                    break;
                case "a": 
                    destinationC = pPosC - 1;
                    break;
                case "s":
                    destinationF = pPosF + 1;
                    break;
                case "d":
                    destinationC = pPosC + 1;
                    break;
                default:
                    System.out.println("Opción inválida. Intente de nuevo.");
                    break;
            }


            switch (habitacion[destinationF][destinationC]) {
                case 1: // Pared
                    System.out.println("No puede avanzar más porque hay una pared.");
                    canGo = false;
                    break;
                case 3: // Puerta
                    System.out.println("Felicidades! Llegó al final de la habitación.");
                    gameOver = true;
                    canGo = false;
                    break;
                case 4:  // Item
                    Item item = jugador.getInventario().generarItem();
                    System.out.println("Obtuviste el item: " + item.getNombre());
                    if(item.getTipo() == "armadura_base" || item.getTipo() == "armadura_legendaria"){
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
            }

            if (canGo) {
                // Revisar sus alrededores
                checksurrounding(destinationF, destinationC, habitacion, jugador, enemigo);
                // Se mueve el jugador a la posicion
                habitacion[destinationF][destinationC] = 2;
                habitacion[pPosF][pPosC] = 0;
            }
        }
    }

    private void checksurrounding(int x, int y, int[][] habitacion, Jugador jugador, Enemigo objetivo) {
        if (habitacion[x + 1][y] == 6 || habitacion[x - 1][y] == 6 || habitacion[x][y + 1] == 6 || habitacion[x][y - 1] == 6) {
            callCombate.combate(objetivo, jugador, habitacion);
        }
    }
}
