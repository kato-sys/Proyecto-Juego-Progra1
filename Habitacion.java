
import java.util.Random;
public class Habitacion {
    Habitacion[] vecinos;
    int[][] habitacion;
    private static final Random rand = new Random();
    int filas;
    int columnas;
    public Enemigo[] enemigos;
    boolean habitacionIncial;
    boolean habitacionJefe;
    boolean habitacionSalida;

    public Habitacion() {
        filas = rand.nextInt(9) + 8; //Estos son valores para hacer aleatoria el tamaño de la habitación.
        columnas = rand.nextInt(9) + 8;
        this.vecinos = new Habitacion[4];
        this.habitacion = new int[filas][columnas];
        enemigos = new Enemigo[4];
        LlenarHabitacion();
    }

    public boolean getHabitacionInicial(){
        return habitacionIncial;
    }
    
    public boolean getHabitacionJefe(){
        return habitacionJefe;
    }

    public boolean getHabitacionSalida(){
        return habitacionSalida;
    }

    public void setHabitacionInicial(boolean esInicial){
        this.habitacionIncial = esInicial;
    }

    public void setHabitacionJefe(boolean esJefe){
        this.habitacionJefe = esJefe;
    }
    public void setHabitacionSalida(boolean esSalida){
        this.habitacionSalida = esSalida;
    }

    public void LlenarHabitacion() {
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
        
        //Aquí voy a tratar de generar las paredes internas aleatorias. Son de 2 a 5 de longitud, sin cruzarse. 
        int numParedes = rand.nextInt(4) + 2; //Acá se define el número de paredes dentro de una habitación, según las instrucciones pueden ser de 2 a 5. 
        for (int i = 0; i < numParedes; i++){
            int longitud = rand.nextInt(4) + 2; //Aquí se define la longitud d elas paredes, que puede ser de 2 a 5.
            boolean horizontal = rand.nextBoolean(); //Para hacerlo más chiva aquí hago un boolean para definir si es horizontal o vertical. 
            int x = rand.nextInt(filas - (horizontal ? 1 : longitud) - 2) + 1; 
            int y = rand.nextInt(columnas - (horizontal ? longitud : 1) - 2) + 1; //Estas dos líneas definen la longitud de la fila. Básicamente es un operador ternario. Que si la pared es horizontal, restamos uno porque entonces la pared solo ocupa una fila. Y si es vertical pues le restamos la longitud. Y luego le sumamos uno para asegurarnos de que la pared no este al borde de la habitacion. 
            for (int j = 0; j < longitud; j++){
                if(habitacion[x][y] == 0){
                    habitacion[x][y] = 1;
                    if(horizontal) y++;
                    else x++;
                }else break; //Esto es para prevenir errores... ;-;
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
        int puertaEsteFila = rand.nextInt(filas - 2) + 1;
        habitacion[puertaEsteFila][columnas - 1] = 9;
        //Puerta Oeste (Izquierda)
        int puertaOesteFila = rand.nextInt(filas - 2) + 1;
        habitacion[puertaOesteFila][0] = 10;

        // Aquí se van a generar los enemigos según probabilidad.
        double probEnemigos = rand.nextDouble();
        if(probEnemigos < 0.02){
          colocarEnemigos(4); //Cuatro enemigos se generan.
        }else if(probEnemigos < 0.12){
          colocarEnemigos(3);
        }else if(probEnemigos < 0.37){
          colocarEnemigos(2);
        } else {
          colocarEnemigos(1);
        }

        //Probabilidad de colocar un ítem aleatorio. (17 %)
        if (rand.nextDouble() < 0.17) {
          colocarItemAleatorio();
        }
        //Probabilidad 2% de que salga una armadura o arma.
        if (rand.nextDouble() < 0.02){
          colocarArmaduraOArma();
        }
        if (rand.nextDouble() < 0.1) { // Probabilidad del 10%
          colocarDebuffs(2); // Genera 2 debuffs
        }


         //Se me olvidó colocar al jugador. XDD
         //Colocar el jugador aquí. 
         if (habitacionIncial){
            colocarJugadorHabitacionBase();
         }
    }

    public void connectar(Habitacion nuevaHabitacion, int direction) {
        vecinos[direction] = nuevaHabitacion;
        nuevaHabitacion.vecinos[direccionOpuesta(direction)] = this;
    }

    public int direccionOpuesta(int direction) {
        switch (direction) {
            case 0: return 1; // Norte
            case 1: return 0; // Sur
            case 2: return 3; // Este
            case 3: return 2; // Oeste
            default: return -1; // Dirección Inválida
        }
    }


    //Método para colocar los enemigos.
    private void colocarEnemigos(int cantidad){
        for(int i = 0; i < cantidad; i++){
          int x,y;
          do{
            x = rand.nextInt(filas - 2) + 1;
            y = rand.nextInt(columnas - 2) + 1;
          }while (habitacion[x][y] != 0);//Esto es para asegurarse de que la posición sea válida. 
          
          enemigos[i] = new Enemigo("Goblin", 20, 5, 2, x, y);
          habitacion[x][y] = 6; //Colocar enemigo. 
          }
      }

    //Método para colocar items aleatorios.
    private void colocarItemAleatorio(){
        int x,y;
        do{
            x = rand.nextInt(filas - 2) + 1;
            y = rand.nextInt(columnas - 2) + 1;
        } while (habitacion[x][y] != 0); //Nuevamente, asegurarse de que esté vacía. 
        habitacion[x][y] = 4; //Colocar Item.
    }

    //Método para colocar armaduras/armas aleatorias.
    private void colocarArmaduraOArma(){
        int x,y;
        do {
            x = rand.nextInt(filas - 2) + 1;
            y = rand.nextInt(columnas - 2) + 1;
            }while (habitacion[x][y] != 0); //Asegurarse de que esté vacío.
            if (rand.nextBoolean()){
                habitacion[x][y] = 5; //Colocamos arma. Como el arma y la armadura son dos cosas diferentes, puse un boolean para que lo decidiera ahí mismo. 
            }else {
                habitacion[x][y] = 4; //Colocar Armadura
            }
    }

    public void colocarJugadorHabitacionBase(){
        int x,y;
        do{
          x = rand.nextInt(filas - 2) + 1;
          y = rand.nextInt(columnas - 2) + 1;
        } while(habitacion[x][y] != 0);
        habitacion[x][y] = 2;
      }

public void colocarJugador(int direction) {
    int x = 0, y = 0;
    boolean doorFound = false;

    // Find the door position
    for (int i = 0; i < filas; i++) {
        for (int j = 0; j < columnas; j++) {
            if (habitacion[i][j] == (direction + 3)) {
                x = i;
                y = j;
                doorFound = true;
                break;
            }
        }
        if (doorFound) break;
    }

    // Move the player forward from the door, with boundary checks
    switch (direction) {
        case 0: // Entering from the Norte (North)
            x = Math.min(x + 1, filas - 1);
            break;
        case 1: // Entering from the Sur (South)
            x = Math.max(x - 1, 0);
            break;
        case 2: // Entering from the Este (East)
            y = Math.max(y - 1, 0);
            break;
        case 3: // Entering from the Oeste (West)
            y = Math.min(y + 1, columnas - 1);
            break;
    }

    // If the new position is the door, adjust to an empty adjacent space
    if (habitacion[x][y] == (direction + 3)) {
        int newX = x, newY = y;
        boolean found = false;
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        for (int i = 0; i < 4; i++) {
            newX = x + dx[i];
            newY = y + dy[i];
            if (newX >= 0 && newX < filas && newY >= 0 && newY < columnas && habitacion[newX][newY] == 0) {
                x = newX;
                y = newY;
                found = true;
                break;
            }
        }
        if (!found) {
            // If no adjacent space found, place randomly within bounds but not on walls
            do {
                x = rand.nextInt(filas - 2) + 1;
                y = rand.nextInt(columnas - 2) + 1;
            } while (habitacion[x][y] != 0);
        }
    }

    // Place the player if the position is valid
    if (habitacion[x][y] == 0) {
        habitacion[x][y] = 2; // Assuming '2' represents the player
    } else {
        // If the spot is taken, find a nearby valid spot
        do {
            x = rand.nextInt(filas - 2) + 1;
            y = rand.nextInt(columnas - 2) + 1;
        } while (habitacion[x][y] != 0);
        habitacion[x][y] = 2;
    }
}



      private void colocarDebuffs(int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            int x, y;
            do {
                x = rand.nextInt(filas - 2) + 1;
                y = rand.nextInt(columnas - 2) + 1;
            } while (habitacion[x][y] != 0);
            habitacion[x][y] = 7; // Colocar debuff
        }
      }

    // Método para verificar si quedan enemigos en la habitación
    public boolean quedanEnemigos() {
        for (Enemigo enemigo : enemigos) {
            if (enemigo != null && enemigo.getVida() > 0) {
                return true; // Hay al menos un enemigo vivo
            }
        }
        return false; // No quedan enemigos vivos
    }

    // Método para eliminar un enemigo específico
    public void eliminarEnemigo(Enemigo enemigo) {
    for (int i = 0; i < enemigos.length; i++) {
        if (enemigos[i] != null && enemigos[i].equals(enemigo)) {
                enemigos[i] = null; // Eliminar al enemigo específico
                break; // Salir del bucle una vez que se ha eliminado
            }
        }
    }

    // Método para obtener un enemigo en una posición específica
    public int getEnemigoPorPosicion(int x, int y) {
        for (int i = 0; i < enemigos.length; i++) {
            if (enemigos[i] != null && enemigos[i].getVida() > 0) {
                if(habitacion[x][y] == 6 && enemigos[i].getPosFila() == x && enemigos[i].getPosColumna() == y){
                    return i;
                }
            }
        }
        return 0;
    }
}
