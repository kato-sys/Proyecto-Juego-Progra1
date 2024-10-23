import java.util.Scanner;
/**
 * Movimiento/Acción del jugador de la habitación
 */
class Main{
    //variables que podríar ser útiles para varios métodos.
    Scanner myScanner = new Scanner(System.in);
    //int[][] matriz = new int[9][9]; //asumamos esta matriz como habitación por ahora
    //Habitación.
    public void GeneracionHabitacion(int[][]habitacion){
        int filas = habitacion.length;
        int columnas = habitacion[0].length;
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
    }

    public void ImprimirHabitacion(int[][] tablero){

        /*System.out.print("  ");
        for (int j = 0; j < tablero[0].length; j++){
        System.out.print(" " + j + " ");
        }*/

        for(int i = 0; i < tablero.length; i++){
            for (int j = 0; j < tablero[0].length; j++){
                switch(tablero[i][j]){
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
                    default:
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
        while(!gameOver)
        {
            ImprimirHabitacion(habitacion);
            //posicion del jugador
            int pPosF = 0;
            int pPosC = 0;
            
            //para encontrar donde esta el jugador en la habitacion y guardar su posicion
            for(int f = 0; f < habitacion.length; f++)
            {
                for (int c = 0; c < habitacion[0].length; c++)
                {
                    if(habitacion[f][c] == 2)
                    {
                        pPosF = f;
                        pPosC = c;
                    }
                }
            }

            /**
             * estos son para guardar la posición en la matriz a donde 
             * se quiere mover un jugador en cada turno
             */
            int destinationF = 0;
            int destinationC = 0;

            /**
             * esta variable se usará para guardar si la posición a donde quiere ir el jugador
             * está libre o si choca con una pared
             */
            boolean canGo = true;

            //para revisar el input de acción del usuario
            boolean valido = true;
            do
            {
                System.out.println("Ingrese \"w\" (arriba), \"a\" (izquierda), \"s\" (abajo) o \"d\" (derecha) para traversar la habitación.");            
                String movimiento = myScanner.nextLine();
                switch(movimiento)
                {
                    case "w":
                        destinationF = pPosF-1;
                        destinationC = pPosC;
                        break;
                    case "a":
                        destinationF = pPosF;
                        destinationC = pPosC-1;
                        break;
                    case "s":
                        destinationF = pPosF+1;
                        destinationC = pPosC;
                        break;
                    case "d":
                        destinationF = pPosF;
                        destinationC = pPosC+1;
                        break;
                    default:
                        System.out.println("Opción inválida, por favor intente otra vez.");
                        valido = false;
                }
            }
            while(!valido);
            
            //revisando qué se encuentra en la posición a la cual se quiere avanzar
            //para preguntar por el atributo que muestre que hay pared
            switch(habitacion[destinationF][destinationC])
            {
                case 1: //pared
                    System.out.println("No puede avanzar más porque hay una pared.");
                    canGo = false;
                    break;
                case 3: //puerta
                    System.out.println("Felicidades! LLegó al final de la habitación, hasta la próxima entrega!");
                    //llamar a addInventory(true)
                    gameOver = true;
                    canGo = false;
                    break;
                case 4: //item
                    System.out.println("Obtuviste el item...hace esto....");
                    //habitacion[destinationF][destinationC] = ;
                    // ^ no sé cual es el valor para el piso ^^'
                    break;
                case 5: //arma
                    System.out.println("Obtuviste el arma... hace esto....");
                    //habitacion[destinationF][destinationC] = ;
                    break;
                case 6: //enemigo
                    Combate(enemigo, jugador);
                    //habitacion[destinationF][destinationC] = ;
                    break;
            }
            if(canGo)
            {
                habitacion[destinationF][destinationC] = 2;
                //habitacion[pPosF][pPosC] = ;
            }
        }
    }
    
    //Método de combate:
    private boolean Combate(Enemigo objetivo, Jugador jugador){
        boolean combateTerminado = false;
        System.out.println("Atacas a: "+objetivo.getNombre());
        while (combateTerminado == false){
            //Función combate.
            System.out.println("¿Qué quieres hacer?");
            System.out.println("ENEMIGO VIDA: "+objetivo.getVida());
            System.out.println("VIDA: "+jugador.getVida());
            System.out.println("[0] ATACAR");
            System.out.println("[1] ITEM");
            int uMenuCombatChoice = myScanner.nextInt();
            myScanner.nextLine();
            switch(uMenuCombatChoice){
                case 0:
                    if (objetivo.getVida() > 0){
                        jugador.atacar(objetivo);
                    if (objetivo.getVida() <= 0){
                        System.out.println("Has derrotado al "+ objetivo.getNombre());
                        combateTerminado = true;
                    }else{
                        objetivo.atacar(jugador);
                        if (jugador.getVida() <= 0){
                            System.out.println("Has Muerto.");
                            combateTerminado = true;
                        }
                    }
                    }
                case 1:
                    //Introducir Menú items. Uso de Items.
            }
           
        }
        return combateTerminado;
    }


    public static void main(String[]args){
        Main trigger = new Main();
        Jugador jugador = new Jugador("Player1",100,15); //Definición placeholder de jugador
        //Generación de la habitación.
        Enemigo enemigo = new Enemigo("Cyborg Renegado",20,5);
        int[][] habitacion = new int[9][9];
        trigger.GeneracionHabitacion(habitacion);
        //Habitación de Ejemplo:
        habitacion[2][3] = 2;//Jugador
        habitacion[0][1] = 3;//Puerta
        habitacion[3][4] = 4;//Item
        habitacion[5][5] = 5;//Arma
        habitacion[7][7] = 6;//Enemigo
        //trigger.Combate(enemigo,jugador);
        //trigger.ImprimirHabitacion(habitacion);
        trigger.RecorridoHabitacion(habitacion, jugador, enemigo);

    }
}
