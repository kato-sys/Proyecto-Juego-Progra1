import java.util.Random;

public class Habitacion {
    public Random rand = new Random();
    Habitacion arriba;
    Habitacion abajo;
    Habitacion izquierda;
    Habitacion derecha;

    public void GeneracionHabitacion(int[][] habitacion){
        this.arriba = null;
        this.abajo = null;
        this.derecha = null;
        this.izquierda = null;
      

        int filas = habitacion.length;
        int columnas = habitacion[0].length;
        int index = 0;

        // Entities (2 = player, 3 = door, 4 = item, 5 = weapon, 6 = enemy, 7 = debuff)
        int[] entities = {2, 3, 4, 6, 5, 7};

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

    public void ImprimirHabitacion(int[][] tablero) {
        for (int[] tablero1 : tablero) {
            for (int j = 0; j < tablero[0].length; j++) {
                switch (tablero1[j]) {
                    case 1 -> System.out.print("#"); // Pared
                    case 2 -> System.out.print("@"); // Player
                    case 3 -> System.out.print("/"); // puerta
                    case 4 -> System.out.print("+"); // Item
                    case 5 -> System.out.print("}"); // arma
                    case 6 -> System.out.print("E"); // Enemigo
                    case 7 -> System.out.print("D"); // Debuff
                    default -> System.out.print("."); // piso
                }
            }
            System.out.println();
        }
    }
}
