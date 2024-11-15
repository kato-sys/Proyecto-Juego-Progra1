import java.util.Random;

public class GeneracionHabitacion {
    private final Random rand = new Random();
    Habitacion habitacion;

    
    public GeneracionHabitacion() {
        int filas = rand.nextInt(9) + 8; //Estos son valores para hacer aleatoria el tamaño de la habitación.
        int columnas = rand.nextInt(9) + 8;
        int[][] tamano = new int[filas][columnas]; 
        habitacion = new Habitacion(tamano, null, null, null, null);
    }

    public Habitacion getHabitacion(){
        return habitacion;
    }

    public void LlenarHabitacion() {
        int[][] hab = habitacion.tamano();
        int filas = hab.length;
        int columnas = hab[0].length;
        int index = 0;

        // Entities (2 = Jugador, 3 = PuertaNorte, 4 = Item, 5 = Arma, 6 = Enemigo, 7 = Debuff, 8 = Puerta Sur, 9 = Puerta Este, 10 = Puerta Oeste)
        int[] entities = {2, 4, 6, 5, 7};

        // techo y piso
        for (int i = 0; i < columnas; i++) {
            hab[0][i] = 1;
            hab[filas - 1][i] = 1;
        }

        // paredes
        for (int i = 0; i < filas; i++) {
            hab[i][0] = 1;
            hab[i][columnas - 1] = 1;
        }

        // adentro
        for (int i = 1; i < filas - 1; i++) {
            for (int j = 1; j < columnas - 1; j++) {
                hab[i][j] = 0;
            }
        }

        //Puertas a los lados.
        //Puerta Norte (Arriba)
        int puertaNorteCol = rand.nextInt(columnas - 2) + 1;
        hab[0][puertaNorteCol] = 3;
        //Puerta Sur (Abajo)
        int puertaSurCol = rand.nextInt(columnas - 2) + 1;
        hab[filas - 1][puertaSurCol] = 8;
        //Puerta Este (Derecha)
        int puertaEsteFila = rand.nextInt(filas - 2) + 1;
        hab[puertaEsteFila][columnas - 1] = 9;
        //Puerta Oeste (Izquierda)
        int puertaOesteFila = rand.nextInt(filas - 2) + 1;
        hab[puertaOesteFila][0] = 10;
        // random entities
        while (index < entities.length) {
            int i = rand.nextInt(filas);
            int j = rand.nextInt(columnas);
            if (hab[i][j] == 0) { // revisar que no haya nada
                hab[i][j] = entities[index];
                index++;
            }
        }
    }

    public void ImprimirHabitacion() {
        for (int[] fila : habitacion.tamano()) {
            for (int celda : fila) {
                switch (celda) {
                    case 1 ->
                        System.out.print("#"); // Pared
                    case 2 ->
                        System.out.print("@"); // Player
                    case 3 ->
                        System.out.print("/"); // Puerta Norte
                    case 4 ->
                        System.out.print("+"); // Item
                    case 5 ->
                        System.out.print("}"); // arma
                    case 6 ->
                        System.out.print("E"); // Enemigo
                    case 7 ->
                        System.out.print("D"); // Debuff
                    case 8 ->
                        System.out.print("/"); // Puerta Sur
                    case 9 ->
                        System.out.print("/"); // Puerta Este
                    case 10 ->
                        System.out.print("/"); //Puerta Oeste
                    default ->
                        System.out.print("."); // piso
                }
            }
            System.out.println();
        }
    }
}