import java.util.Random;

public class Habitacion {
    public Random rand = new Random();
    Habitacion arriba;
    Habitacion abajo;
    Habitacion izquierda;
    Habitacion derecha;
    int[][] habitacionTamano;
    int tipoHabitacion; //normal 0, jefe 1, salida 2
    boolean yaVisitado; //si este cuarto es nuevo o si ya se ha pasado
    int cantidad = 0; //cuantos nodos cuartos hay
    int indice; //para asignarle numero a la habitacion

    public Habitacion(){
      int filas = rand.nextInt(9) + 8; //Estos son valores para hacer aleatoria el tamaño de la habitación.
      int columnas = rand.nextInt(9) + 8;
      this.habitacionTamano = new int[filas][columnas];
      GeneracionHabitacion(habitacionTamano);

      tipoHabitacion = rand.nextInt(2); //(no es solo eso, hay que tomar en cuenta las probabilidades, seguro aqui iria un metodo que use la clase de probabilidades)
      yaVisitado = false;
      cantidad++;
      indice = cantidad;
      //aquí tambié se subirían las probabilidades, ya que estan cambian con cada nuevo cuarto
    }

    //Getter para el movimiento del jugador.
    public int[][] getHabitacion(){
      return habitacionTamano;
    }
    
    public void GeneracionHabitacion(int[][] habitacion){  
        
        int filas = habitacion.length;
        int columnas = habitacion[0].length;
        int index = 0;

        // Entities (2 = Jugador, 3 = PuertaNorte, 4 = Item, 5 = Arma, 6 = Enemigo, 7 = Debuff, 8 = Puerta Sur, 9 = Puerta Este, 10 = Puerta Oeste)
        int[] entities = {2, 4, 6, 5, 7};

        // techo y piso
        for (int i = 0; i < columnas; i++) {
            habitacion[0][i] = 1;             
            habitacion[filas - 1][i] = 1;     
        }

        // paredes
        for (int i = 0; i < filas; i++) {
            habitacion[i][0] = 1;            
            habitacion[i][columnas - 1] = 1; 
        }

        // adentro
        for (int i = 1; i < filas - 1; i++) {
            for (int j = 1; j < columnas - 1; j++) {
                habitacion[i][j] = 0;
            }
        }

        //Puertas a los lados.
        //Puerta Norte (Arriba)
        int puertaNorteCol = rand.nextInt(columnas - 2) + 1;
        habitacion[0][puertaNorteCol] = 3;
        //Puerta Sur (Abajo)
        int puertaSurCol = rand.nextInt(columnas - 2) + 1;
        habitacion[filas - 1][puertaSurCol] = 8;
        //Puerta Este (Derecha)
        int puertaEsteFila = rand.nextInt(filas-2) + 1;
        habitacion[puertaEsteFila][columnas-1] = 9;
        //Puerta Oeste (Izquierda)
        int puertaOesteFila = rand.nextInt(filas - 2) + 1;
        habitacion[puertaOesteFila][0] = 10;
        // random entities
        while (index < entities.length) {
            int i = rand.nextInt(filas);
            int j = rand.nextInt(columnas);
            if (habitacion[i][j] == 0) { // revisar que no haya nada
                habitacion[i][j] = entities[index];
                index++;
            }
        }
    }

    public void ImprimirHabitacion(){
        for (int[] fila : habitacionTamano) {
            for (int celda : fila){
                switch (celda) {
                    case 1 -> System.out.print("#"); // Pared
                    case 2 -> System.out.print("@"); // Player
                    case 3 -> System.out.print("/"); // Puerta Norte
                    case 4 -> System.out.print("+"); // Item
                    case 5 -> System.out.print("}"); // arma
                    case 6 -> System.out.print("E"); // Enemigo
                    case 7 -> System.out.print("D"); // Debuff
                    case 8 -> System.out.print("/"); //Puerta Sur
                    case 9 -> System.out.print("/"); //Puerta Este
                    case 10 -> System.out.print("/"); //Puerta Oeste
                    default -> System.out.print("."); // piso
                }
            }
            System.out.println();
        }
    }
}
