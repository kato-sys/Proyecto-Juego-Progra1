
import java.util.Random;
import java.util.Scanner;

/**
 * Movimiento/Acción del jugador de la habitaciﾃｳn
 */
class Main {

    //variables que podrﾃｭar ser útiles para varios métodos.
    Scanner myScanner = new Scanner(System.in);
    public Random rand = new Random();

    //int[][] matriz = new int[9][9]; //asumamos esta matriz como habitación por ahora
    //Habitación.
    public void GeneracionHabitacion(int[][] habitacion) {
        int filas = habitacion.length;
        int columnas = habitacion[0].length;

        int index = 0;
        int[] entities = {2, 3, 4, 4, 6}; // 2 = jugador, 3 = puerta, 4 = item, 5 = arma, 6 = enemigo
        // Generar la fila superior e inferior.
        for (int i = 0; i < columnas; i++) {
            habitacion[0][i] = 1;             // Fila superior
            habitacion[filas - 1][i] = 1;      // Fila inferior
        }
        // Lo mismo con las columnas izquierdas y derechas.
        for (int i = 0; i < filas; i++) {
            habitacion[i][0] = 1;             // Columna izquierda
            habitacion[i][columnas - 1] = 1;      // Columna derecha.
        }
        // El resto de las casillas son piso.
        for (int i = 0; i < habitacion.length; i++) {
            for (int j = 0; j < habitacion[0].length; j++) {
                if (habitacion[i][j] != 1) {
                    habitacion[i][j] = 0;
                }
            }
        }
        // Randomizar habitación
        while (index < entities.length) {
            int i = rand.nextInt(9);
            int j = rand.nextInt(9);
            if (habitacion[i][j] != 1) {
                habitacion[i][j] = entities[index];
                index += 1;
            }
        }
    }

    public void ImprimirHabitacion(int[][] tablero) {

        /*System.out.print("  ");
        for (int j = 0; j < tablero[0].length; j++){
        System.out.print(" " + j + " ");
        }*/
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                switch (tablero[i][j]) {
                    case 1:
                        System.out.print("#"); //Imprimir muros
                        break;
                    case 2:
                        System.out.print("@"); //Jugador
                        break;
                    case 3:
                        System.out.print("/"); //Puerta
                        break;
                    case 4:
                        System.out.print("+"); //Item
                        break;
                    case 5:
                        System.out.print("}"); //Arma
                        break;
                    case 6:
                        System.out.print("E"); //Enemigo
                        break;
                    case 7:
                        System.out.println("D");
                        break;
                    case 0:
                        System.out.print("."); //Piso
                }
            }
            System.out.println();
        }
    }

    public void RecorridoHabitacion(int[][] habitacion, Jugador jugador, Enemigo enemigo) //como este metodo llama al metodo combate se le debe dar de una vez el jugador y enemigo
    {
        System.out.println("Habitación "); // + num de habitacion

        boolean gameOver = false;
        while (!gameOver) {
            ImprimirHabitacion(habitacion);
            //posicion del jugador
            int pPosF = 0;
            int pPosC = 0;

            //para activar debuff
            if(jugador.tieneDebuff())
            {
                jugador.recibirDaño(0);
            }
            System.out.println("Vida de jugador: " + jugador.getVida());

            //para encontrar donde esta el jugador en la habitacion y guardar su posicion
            for (int f = 0; f < habitacion.length; f++) {
                for (int c = 0; c < habitacion[0].length; c++) {
                    if (habitacion[f][c] == 2) {
                        pPosF = f;
                        pPosC = c;
                    }
                }
            }

            /**
             * estos son para guardar la posicion en la matriz a donde se
             * quiere mover un jugador en cada turno
             */
            int destinationF = 0;
            int destinationC = 0;

            /**
             * esta variable se usará para guardar si la posición a donde quiere
             * ir el jugador está libre o si choca con una pared
             */
            boolean canGo = true;

            //para revisar el input de acción del usuario
            boolean valido = true;
            do {
                System.out.println("Ingrese \"w\" (arriba), \"a\" (izquierda), \"s\" (abajo) o \"d\" (derecha) para traversar la habitación.");
                String movimiento = myScanner.nextLine();
                switch (movimiento) {
                    case "w":
                        destinationF = pPosF - 1;
                        destinationC = pPosC;
                        // checksurrounding(destinationF, destinationC, habitacion, jugador, enemigo);
                        valido = true;
                        break;
                    case "a":
                        destinationF = pPosF;
                        destinationC = pPosC - 1;
                        // checksurrounding(destinationF, destinationC, habitacion, jugador, enemigo);
                        valido = true;
                        break;
                    case "s":
                        destinationF = pPosF + 1;
                        destinationC = pPosC;
                        valido = true;
                        // checksurrouding(destinationF, destinationC, habitacion, jugador, enemigo);
                        break;
                    case "d":
                        destinationF = pPosF;
                        destinationC = pPosC + 1;
                        valido = true;
                        // checksurrouding(destinationF, destinationC, habitacion, jugador, enemigo);
                        break;
                    default:
                        System.out.println("Opción inválida, por favor intente otra vez.");
                        valido = false;
                }
            } while (!valido);

            //revisando que se encuentra en la posición a la cual se quiere avanzar
            //para preguntar por el atributo que muestre que hay pared
            switch (habitacion[destinationF][destinationC]) {
                case 1: //pared
                    System.out.println("No puede avanzar más porque hay una pared.");
                    canGo = false;
                    break;
                case 3: //puerta
                    System.out.println("Felicidades! LLegó al final de la habitación, hasta la próxima entrega!");
                    gameOver = true;
                    canGo = false;
                    break;
                case 4: //item
                    Item item = generarItem(); // Genera el item
                    switch (item.getTipo()) {
                        case "armadura_base":
                            jugador.getInventario().addInventario(item, "armadura"); // Agrega al inventario
                            break;
                        case "armadura_legendaria":
                            jugador.getInventario().addInventario(item, "armadura");
                            break;
                        case "buff_ataque":
                        jugador.getInventario().addInventario(item, "item");
                            break;
                        case "debuff_defensa":
                            jugador.activarDebuff();
                            break;
                    }
                    System.out.println("Obtuviste el item: " + item.getNombre());
                    habitacion[destinationF][destinationC] = 0;
                    break;
                case 5:
                    Item arma = generarArma();
                    jugador.getInventario().addInventario(arma, "arma");
                    System.out.println("Obtuviste el arma: " + arma.getNombre());

                    break;
            }
            if (canGo) {
                checksurrouding(destinationF, destinationC, habitacion, jugador, enemigo);
                habitacion[destinationF][destinationC] = 2;
                habitacion[pPosF][pPosC] = 0;
            }
        }
    }

    private Item generarArma() {
        int seleccionTipoArma = rand.nextInt(3);
        switch (seleccionTipoArma) {
            case 0:
                return new Item("Arma Secreta", "arma_secreta", "Reduce 50% de la vida del enemigo", 0, 1);
            case 1:
                return new Item("Arma Básica", "arma_basica", "Ataca con el poder base del jugador", 0, 1);
            case 2:
                return new Item("Arma Legendaria", "arma_legendaria", "Duplica el ataque base del jugador", 0, 1);
        }
        return null; // No sé, por si acaso ocurre un error (?)

    }

    private Item generarItem() {
        int seleccionTipoItem = rand.nextInt(4);
        switch (seleccionTipoItem) {
            case 0:
                return new Item("Armadura Base", "armadura_base", "Reduce ataque recibido hasta agotarse", 5, 3);
            case 1:
                return new Item("Armadura Legendaria", "armadura_legendaria", "Reduce el daño recibido a la mitad", 0, 1);
            case 2:
                return new Item("Buff de Ataque", "buff_ataque", "Incrementa el ataque en 5-10%", 0, 1);
            case 3:
                return generarArma();
        }
        return null; // No sé, por si acaso ocurre un error (?)
    }

    //Método de combate:
    private boolean Combate(Enemigo objetivo, Jugador jugador, int[][] habitacion) {
        boolean combateTerminado = false;
        System.out.println("Atacas a: " + objetivo.getNombre());
        while (combateTerminado == false) {
            //Función combate.
            System.out.println("¿Qué quieres hacer?");
            System.out.println("ENEMIGO VIDA: " + objetivo.getVida());
            System.out.println("VIDA: " + jugador.getVida());
            System.out.println("[0] ATACAR");
            System.out.println("[1] ITEM");
            int uMenuCombatChoice = myScanner.nextInt();
            myScanner.nextLine();
            switch (uMenuCombatChoice) {
                case 0:
                    if (objetivo.getVida() > 0) {
                        jugador.atacar(objetivo);
                        if (objetivo.getVida() <= 0) {
                            System.out.println("Has derrotado al " + objetivo.getNombre());
                            for (int i = 0; i < habitacion.length; i++) {
                                for (int j = 0; j < habitacion[0].length; j++) {
                                    if (habitacion[i][j] == 6) {
                                        habitacion[i][j] = 0;
                                    }
                                }
                            }
                            combateTerminado = true;
                        } else {
                            objetivo.atacar(jugador);
                            if (jugador.getVida() <= 0) {
                                System.out.println("Has Muerto.");
                                combateTerminado = true;
                            }
                        }
                    }
                    break;
                case 1:
                    usarItem(jugador, objetivo); //Llamar método usar Item
                    break;
            }

        }
        return combateTerminado;
    }

    private void usarItem(Jugador jugador, Enemigo enemigo) {
        jugador.getInventario().printInventario(); // Mostrar los ítems
        System.out.println("Elige un ítem para usar (índice 0, 1, 2): ");
        int itemIndex = myScanner.nextInt();
        myScanner.nextLine();

        try {
            if (itemIndex >= 0 && itemIndex <= jugador.getInventario().getCuantosItems()) {
                // Accede al ítem usando el índice
                Item item = jugador.getInventario().getInventarioItems()[itemIndex];
                // Verifica que el ítem no sea null antes de usarlo
                if (item != null) {
                    item.aplicarEfecto(jugador, enemigo); // Aplica el efecto del ítem
                    System.out.println("Has usado: " + item.getNombre());
                    // Elimina el ítem del inventario si es de usarlo una vez
                    jugador.getInventario().removeItem(itemIndex);
                } else {
                    System.out.println("El ítem no es válido. Intenta de nuevo.");
                }
            } else {
                System.out.println("Ítem inválido. Intenta de nuevo.");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Índice fuera de rango. Asegúrate de elegir un índice válido.");
        } catch (NullPointerException e) {
            System.out.println("No hay ítems en el inventario o el ítem seleccionado es nulo.");
        }
    }

    private void checksurrouding(int x, int y, int[][] habitacion, Jugador jugador, Enemigo objetivo) {
        if (habitacion[x + 1][y] == 6 || habitacion[x - 1][y] == 6 || habitacion[x][y + 1] == 6 || habitacion[x][y - 1] == 6 || habitacion[x + 1][y + 1] == 6 || habitacion[x + 1][y - 1] == 6 || habitacion[x - 1][y - 1] == 6 || habitacion[x - 1][y + 1] == 6) {
            Combate(objetivo, jugador, habitacion);
        }
    }

    public static void main(String[] args) {
        Main trigger = new Main();
        Jugador jugador = new Jugador("Player1", 100, 15, 10); //Definición placeholder de jugador
        //Generación de la habitación.
        Enemigo enemigo = new Enemigo("Cyborg Renegado", 20, 5, 10);
        int[][] habitacion = new int[9][9];
        trigger.GeneracionHabitacion(habitacion);
        //Habitación de Ejemplo:
        //habitacion[2][3] = 2;//Jugador
        //habitacion[0][1] = 3;//Puerta
        //habitacion[3][4] = 4;//Item
        //habitacion[5][5] = 5;//Arma
        //habitacion[7][7] = 6;//Enemigo
        //trigger.Combate(enemigo,jugador)
        //trigger.ImprimirHabitacion(habitacion);
        trigger.RecorridoHabitacion(habitacion, jugador, enemigo);
        //Hullo!
    }
}
