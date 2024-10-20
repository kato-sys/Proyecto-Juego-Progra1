import java.util.Scanner;
/**
 * Movimiento/Acción del jugador de la habitación
 */
class Main{
    //variables que podríar ser útiles para varios métodos.
    Scanner scanner = new Scanner(System.in);
    int[][] matriz = new int[9][9]; //asumamos esta matriz como habitación por ahora
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
                if (tablero[i][j] == 1){
                    System.out.print("#");
                }else if(tablero[i][j] == 0){
                    System.out.print(".");
                }
                //Continuar con else ifs... Cambia el caracter que imprime. 
            }
            System.out.println();
        }
    }
    //Sería importante que este método reciba la/las matrices de la habitación.
    public void RecorridoHabitacion()//aquí podría ir el número de habitación para que se pueda accesar sus elementos aleatorios.
    {
        System.out.println("Habitación "); // + num de habitacion

        boolean gameOver = false;
        while(!gameOver)
        {
            /**
             * Aquí se imprimiría la matriz, ya sea si ya se le asignaron signos a las partes 
             * de la matriz o con variables las creo yo
             */

            /**
             * aquí se buscariá la posición del jugador, si ya viene preestablecida, 
             * sino lo pongo al frente de la puerta. mientras tanto la asumo con estas
             * variables (p para player, Pos de posición, F para fila y C para columna)
             */
            int pPosF = 8;
            int pPosC = 5;

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
                String movimiento = scanner.nextLine();
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
            /*
            //revisando qué se encuentra en la posición a la cual se quiere avanzar
            //para preguntar por el atributo que muestre que hay pared
            if(matriz[destinationF][destinationC].getType() == Pared)
            {
            System.out.println("No puede avanzar más porque hay una pared.");
            }
            //preguntando si hay puerta
            else if(matriz[destinationF][destinationC].getType() == Puerta)
            {
            System.out.println("Felicidades! LLegó al final de la habitación, hasta la próxima entrega!");
            gameOver = true;
            }
            else
            {
            //como nada le obstruye, el jugador se mueve
            pPosF = destinationF;
            pPosC = destinationC;
            //preguntando si hay un item
            if(matriz[pPosF][pPosC].getType() == Item)
            {
            System.out.println("Obtuviste el item...hace esto....");
            }
            //preguntando si hay un arma
            else if(matriz[pPosF][pPosC].getType() == Arma)
            {
            System.out.println("Obtuviste el arma... hace esto....");
            }
            //preguntando si hay un enemigo
            else if(matriz[pPosF][pPosC].getType() == Enemigo)
            {
            // el método de Batalla iría aquí

            }
            }*/
        }
    }
    //Método de combate:
    private boolean Combate(Enemigo objetivo, Jugador jugador){
        // Atacar a un monstruo
        System.out.print("Atacas a: "+ objetivo.getNombre());
        boolean combateTerminado = false; // variable para saber si el juego terminó
        jugador.atacar(objetivo);
            if (objetivo.getVida() <= 0) {
                System.out.println("¡Has derrotado al " + objetivo.getNombre() + "!");
            } else {
                objetivo.atacar(jugador);
                if (jugador.getVida() <= 0) {
                    System.out.println("¡Has sido derrotado!");
                    combateTerminado = true;
                }
            }
        return combateTerminado;
    }


    public static void main(String[]args){
        Main trigger = new Main();
        Jugador jugador = new Jugador("Player1",100,15); //Definición placeholder de jugador
        Enemigo[] enemigos = new Enemigo[3];
        enemigos[0] = new Enemigo("Cyborg Renegado",50,5);
        enemigos[1] = new Enemigo("Hacker Rebelde",20,10);
        //Generación de la habitación.
        int[][] habitacion = new int[9][9];
        trigger.GeneracionHabitacion(habitacion);
        trigger.ImprimirHabitacion(habitacion);
        //trigger.RecorridoHabitacion();

    }
}
