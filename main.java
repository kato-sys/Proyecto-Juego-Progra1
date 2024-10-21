import java.util.Scanner;
/**
 * Movimiento/Acción del jugador de la habitación
 */
class Main{
    //variables que podríar ser útiles para varios métodos.
    Scanner scanner = new Scanner(System.in);
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
    //Sería importante que este método reciba la/las matrices de la habitación.
    public void RecorridoHabitacion(int[][] habitacion)//aquí podría ir el número de habitación para que se pueda accesar sus elementos aleatorios.
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
            
            //revisando qué se encuentra en la posición a la cual se quiere avanzar
            //para preguntar por el atributo que muestre que hay pared
            if(habitacion[destinationF][destinationC] == 1)
            {
            System.out.println("No puede avanzar más porque hay una pared.");
            }
            //preguntando si hay puerta
            else if(habitacion[destinationF][destinationC] == 3)
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
            if(habitacion[pPosF][pPosC] == 4)
            {
            System.out.println("Obtuviste el item...hace esto....");
            }
            //preguntando si hay un arma
            else if(habitacion[pPosF][pPosC] == 5)
            {
            System.out.println("Obtuviste el arma... hace esto....");
            }
            //preguntando si hay un enemigo
            else if(habitacion[pPosF][pPosC] == 6)
            {
            // el método de Batalla iría aquí

            }
            }
        }
    }
    /*
    //Método de combate:
    private boolean Combate(Enemigo objetivo, Jugador jugador){
        boolean comabteTerminado = false;
        while (combateTerminado == false){
            //Función combate.
        }
        return combateTerminado;
    }*/


    public static void main(String[]args){
        Main trigger = new Main();
        //Jugador jugador = new Jugador("Player1",100,15); //Definición placeholder de jugador
        //Generación de la habitación.
        int[][] habitacion = new int[9][9];
        trigger.GeneracionHabitacion(habitacion);
        //Habitación de Ejemplo:
        habitacion[2][3] = 2;//Jugador
        habitacion[0][1] = 3;//Puerta
        habitacion[3][4] = 4;//Item
        habitacion[5][5] = 5;//Arma
        habitacion[7][7] = 6;//Enemigo

        trigger.ImprimirHabitacion(habitacion);
        trigger.RecorridoHabitacion(habitacion);

    }
}
