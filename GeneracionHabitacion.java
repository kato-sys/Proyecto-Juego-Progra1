import java.util.Random;

public class GeneracionHabitacion {
    private final Random rand = new Random();
    Habitacion habitacion;
    private final int filas;
    private final int columnas;
    private Enemigo[] enemigos;
    private static final int MAX_ENEMIGOS = 4; 
    
    public GeneracionHabitacion() {
        this.filas = rand.nextInt(9) + 8; //Estos son valores para hacer aleatoria el tamaño de la habitación.
        this.columnas = rand.nextInt(9) + 8;
        int[][] tamano = new int[filas][columnas]; 
        habitacion = new Habitacion(tamano, null, null, null, null);
        enemigos = new Enemigo[4];
    }

    public Habitacion getHabitacion(){
        return habitacion;
    }

    public Enemigo[] getEnemigos(){
      return enemigos;
    }

    public void LlenarHabitacion() {
        int filas = habitacion.tamano().length;
        int columnas = habitacion.tamano()[0].length;
        //int index = 0;

        // Entities (2 = Jugador, 3 = PuertaNorte, 4 = Item, 5 = Arma, 6 = Enemigo, 7 = Debuff, 8 = Puerta Sur, 9 = Puerta Este, 10 = Puerta Oeste)
        //int[] entities = {2, 4, 6, 5, 7};

        // techo y piso
        for (int i = 0; i < columnas; i++) {
            habitacion.setvalor(0, i, 1);
            habitacion.setvalor(filas - 1, i, 1);
        }
        // paredes
        for (int i = 0; i < filas; i++) {
            habitacion.setvalor(i, 0, 1);
            habitacion.setvalor(i, columnas - 1, 1);
        }
        // adentro
        for (int i = 1; i < filas - 1; i++) {
            for (int j = 1; j < columnas - 1; j++) {
                habitacion.setvalor(i, j, 0);
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
          if(habitacion.tamano()[x][y] == 0){
            habitacion.setvalor(x,y,1);
            if(horizontal) y++;
            else x++;
          }else break; //Esto es para prevenir errores... ;-;
        } 
        }
        

        
        //Puertas a los lados.
        //Puerta Norte (Arriba)
        int puertaNorteCol = rand.nextInt(columnas - 2) + 1;
        habitacion.setvalor(0, puertaNorteCol, 3);
        habitacion.arriba = new GeneracionHabitacion().getHabitacion();
        //Puerta Sur (Abajo)
        int puertaSurCol = rand.nextInt(columnas - 2) + 1;
        habitacion.setvalor(filas - 1, puertaSurCol, 8);
        habitacion.abajo = new GeneracionHabitacion().getHabitacion();
        //Puerta Este (Derecha)
        int puertaEsteFila = rand.nextInt(filas - 2) + 1;
        habitacion.setvalor(puertaEsteFila, columnas - 1, 9);
        habitacion.derecha = new GeneracionHabitacion().getHabitacion();
        //Puerta Oeste (Izquierda)
        int puertaOesteFila = rand.nextInt(filas - 2) + 1;
        habitacion.setvalor(puertaOesteFila, 0, 10);
        habitacion.izquierda = new GeneracionHabitacion().getHabitacion();
      

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


        colocarJugador(); //Se me olvidó colocar al jugador. XDD
    }

    //Método para colocar los enemigos.
    private void colocarEnemigos(int cantidad){
      for(int i = 0; i < cantidad && i < MAX_ENEMIGOS; i++){
        int x,y;
        do{
          x = rand.nextInt(filas - 2) + 1;
          y = rand.nextInt(columnas - 2) + 1;
        }while (habitacion.tamano()[x][y] != 0);//Esto es para asegurarse de que la posición sea válida. 
        
        enemigos[i] = new Enemigo("Goblin", 20, 5, 2);
        habitacion.setvalor(x,y,6); //Colocar enemigo. 
        }
    }

        // Método para verificar si quedan enemigos en la habitación
    public boolean quedanEnemigos() {
        for (Enemigo enemigo : enemigos) {
            if (enemigo != null && enemigo.getVida() > 0) {
                return true;
            }
        }
        return false;
    }

    // Método para eliminar un enemigo específico
    public void eliminarEnemigo(Enemigo enemigo) {
        for (int i = 0; i < enemigos.length; i++) {
            if (enemigos[i] == enemigo) {
                enemigos[i] = null;
                break;
            }
        }
    }

    // Método para obtener un enemigo en una posición específica
    public Enemigo getEnemigoPorPosicion(int x, int y) {
        for (int i = 0; i < enemigos.length; i++) {
            if (enemigos[i] != null && enemigos[i].getVida() > 0) {
                // Buscar la posición del enemigo en la habitación
                for (int f = 0; f < habitacion.tamano().length; f++) {
                    for (int c = 0; c < habitacion.tamano()[0].length; c++) {
                        if (habitacion.tamano()[f][c] == 6 && f == x && c == y) {
                            return enemigos[i];
                        }
                    }
                }
            }
        }
        return null;
    }

    //Método para colocar items aleatorios.
    private void colocarItemAleatorio(){
      int x,y;
      do{
        x = rand.nextInt(filas - 2) + 1;
        y = rand.nextInt(columnas - 2) + 1;
      } while (habitacion.tamano()[x][y] != 0); //Nuevamente, asegurarse de que esté vacía. 
        habitacion.setvalor(x,y,4); //Colocar Item.
    }


    
    //Método para colocar armaduras/armas aleatorias.
    private void colocarArmaduraOArma(){
      int x,y;
      do {
        x = rand.nextInt(filas - 2) + 1;
        y = rand.nextInt(columnas - 2) + 1;
      }while (habitacion.tamano()[x][y] != 0); //Asegurarse de que esté vacío.
      if (rand.nextBoolean()){
        habitacion.setvalor(x,y,5); //Colocamos arma. Como el arma y la armadura son dos cosas diferentes, puse un boolean para que lo decidiera ahí mismo. 
      }else {
        habitacion.setvalor(x,y,4); //Colocar Armadura
      }
    }

    private void colocarJugador(){
      int x,y;
      do{
        x = rand.nextInt(filas - 2) + 1;
        y = rand.nextInt(columnas - 2) + 1;
      } while(habitacion.tamano()[x][y] != 0);
      habitacion.setvalor(x,y,2);
    }

    private void colocarDebuffs(int cantidad) {
    for (int i = 0; i < cantidad; i++) {
        int x, y;
        do {
            x = rand.nextInt(filas - 2) + 1;
            y = rand.nextInt(columnas - 2) + 1;
        } while (habitacion.tamano()[x][y] != 0);
        habitacion.setvalor(x, y, 7); // Colocar debuff
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
