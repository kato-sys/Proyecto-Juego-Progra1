
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
    int deDondeViene = 0; // 0 si es el primer cuarto, 1 para norte, 2 para sur, 3 para este, 4 para oeste
    private static int contadorHabitaciones = 0;
    int numeroHabitacion;
    int puertas = 1;
    int cualPuerta;
    misc prob = new misc();
    boolean[] puertasPrevias = {true, true, true, true};
    

    public Habitacion() {
        filas = rand.nextInt(9) + 8; //Estos son valores para hacer aleatoria el tamaño de la habitación.
        columnas = rand.nextInt(9) + 8;
        this.vecinos = new Habitacion[4];
        this.habitacion = new int[filas][columnas];
        enemigos = new Enemigo[5];
        this.numeroHabitacion = contadorHabitaciones++; // Incrementa el contador y asigna el número
        if(habitacionSalida){
            GenerarHabitacionSalida();
        }else{
            LlenarHabitacion();
        }
        
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


    public int getNumeroHabitacion() {
        return numeroHabitacion; // Devuelve el número único de esta habitación
    }


    public void GenerarHabitacionSalida(){
        // techo y piso
        for (int i = 0; i < 9; i++) {
            habitacion[0][i] = 1;
            habitacion[9 - 1][i] = 1;
        }
        // paredes
        for (int i = 0; i < 9; i++) {
            habitacion[i][0] = 1;
            habitacion[i][9 - 1] = 1;
        }
        // adentro
        for (int i = 1; i < 9 - 1; i++) {
            for (int j = 1; j < 9 - 1; j++) {
                habitacion[i][j] = 0;
            }
        }
        //Puerta Final
        int puertaNorteCol = rand.nextInt(columnas - 2) + 1;
        habitacion[0][puertaNorteCol] = 12;
        
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
        
        // Aquí voy a tratar de generar las paredes internas aleatorias. Son de 2 a 5 de longitud, sin cruzarse.
        int numParedes = rand.nextInt(4) + 2; // Número de paredes dentro de la habitación.
        for (int i = 0; i < numParedes; i++) {
            int longitud = rand.nextInt(4) + 2; // Longitud de las paredes.
            boolean horizontal = rand.nextBoolean(); // Horizontal o vertical.
            int x, y;

            // Asegurar que la pared se genera lejos de puertas y bordes.
            boolean posicionValida;
            do {
                posicionValida = true;
                x = rand.nextInt(filas - (horizontal ? 1 : longitud) - 2) + 1;
                y = rand.nextInt(columnas - (horizontal ? longitud : 1) - 2) + 1;

                // Verificar que la pared no bloquee puertas o esté demasiado cerca.
                for (int j = 0; j < longitud; j++) {
                    int nuevoX = x + (horizontal ? 0 : j);
                    int nuevoY = y + (horizontal ? j : 0);
            
                    if (habitacion[nuevoX][nuevoY] != 0 || // Celda ocupada.
                        (nuevoX == 0 || nuevoX == filas - 1 || nuevoY == 0 || nuevoY == columnas - 1) || // Cerca de bordes.
                        (Math.abs(nuevoX - 0) < 2 && habitacion[0][nuevoY] == 3) || // Cerca de puerta norte.
                        (Math.abs(nuevoX - (filas - 1)) < 2 && habitacion[filas - 1][nuevoY] == 8) || // Cerca de puerta sur.
                        (Math.abs(nuevoY - (columnas - 1)) < 2 && habitacion[nuevoX][columnas - 1] == 9) || // Cerca de puerta este.
                        (Math.abs(nuevoY - 0) < 2 && habitacion[nuevoX][0] == 10)) { // Cerca de puerta oeste.
                        posicionValida = false;
                        break;
                    }
                }
            } while (!posicionValida);

            // Generar la pared.
            for (int j = 0; j < longitud; j++) {
                habitacion[x][y] = 1;
                if (horizontal) y++;
                else x++;
            }
        }

        
        //Cantidad de puertas.
        if(prob.probabilidad(60)) puertas += 1;
        if(prob.probabilidad(40)) puertas += 1;
        if(prob.probabilidad(20)) puertas += 1;
        while(puertas > 0){
            cualPuerta = rand.nextInt(4);
            switch (cualPuerta) {
                case 0: //Puerta Norte (Arriba)
                    if(puertasPrevias[0]){
                        int puertaNorteCol = rand.nextInt(columnas - 2) + 1;
                        habitacion[0][puertaNorteCol] = 3;
                        puertasPrevias[0] = false;
                        puertas -= 1;
                    }
                    break;
                case 1: //Puerta Sur (Abajo)
                    if(puertasPrevias[1]){
                        int puertaSurCol = rand.nextInt(columnas - 2) + 1;
                        habitacion[filas - 1][puertaSurCol] = 8;
                        puertasPrevias[1] = false;
                        puertas -= 1;
                    }
                    break;
                case 2: //Puerta Este (Derecha)
                    if(puertasPrevias[2]){
                        int puertaEsteFila = rand.nextInt(filas - 2) + 1;
                        habitacion[puertaEsteFila][columnas - 1] = 9;
                        puertasPrevias[2] = false;
                        puertas -= 1;
                    }
                    break;
                case 3: //Puerta Oeste (Izquierda)
                    if(puertasPrevias[3]){
                        int puertaOesteFila = rand.nextInt(filas - 2) + 1;
                        habitacion[puertaOesteFila][0] = 10;
                        puertasPrevias[3] = false;
                        puertas -= 1;
                    }
                    break;
            }
        }


        // Aquí se van a generar los enemigos según probabilidad.
        double probEnemigos = rand.nextDouble();
        if (habitacionJefe){
            colocarEnemigos(1);
        }
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
        if (habitacionJefe){
            enemigos[i] = new Enemigo("Mister Boss Final", 100, 10, 1, x, y);
        }
        if (rand.nextDouble() < 0.0001){
          enemigos[i] = new Enemigo("[[ETERNO RESPLANDOR DEL FALLECIMIENTO DE INFINITAS ESTRELLAS]]", 999, 999, 999, x, y);
        }else{
            int posibleEnemigo = rand.nextInt(4);
            switch(posibleEnemigo){
                case 0:
                    enemigos[i] = new Enemigo("Goblin", 10, 2, 2, x, y);
                    break;
                case 1:
                    enemigos[i] = new Enemigo("Orco", 25, 5, 5, x, y);
                    break;
                case 2:
                    enemigos[i] = new Enemigo("Espectro", 20, 1, 1, x, y);
                    break;
                case 3:
                    enemigos[i] = new Enemigo("Slime", 10, 2, 0, x, y);
                    break;
                default:
                    enemigos[i] = new Enemigo("Goblin", 10, 2, 2, x, y);
                    break;
            }
        }
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
        if(habitacionIncial)
        {
            int x,y;
            do{
            x = rand.nextInt(filas - 2) + 1;
            y = rand.nextInt(columnas - 2) + 1;
            } while(habitacion[x][y] != 0);
            habitacion[x][y] = 2;
        }
        else
        {
            switch(deDondeViene)
            {
                case 1: //viene de la puerta norte, entonces debe aparecer a la par de la puerta sur
                    for(int c = 0; c < habitacion[habitacion.length-1].length;c++){
                        if(habitacion[c][habitacion.length-1]== 0)
                        {
                            habitacion[c][habitacion.length-2] = 2;
                        }
                    }
                    break;
                case 2: //viene de la puerta sur, entonces debe aparecer a la par de la puerta norte
                    break;
                case 3: //viene de la puerta este, entonces debe aparecer a la par de la puerta oeste
                    break;
                case 4: //viene de la puerta oeste, entonces debe aparecer a la par de la puerta este
                    break;
                default:
                    System.out.println("Algo salio mal :(");
            }
        }
      }


public void colocarJugador(int dir) {
        switch(dir)
        {
            case 0: //puerta norte
                for(int f = 0; f < habitacion.length;f++)
                {
                    for(int c = 0; c < habitacion[f].length;c++)
                    {
                        if(habitacion[f][c] == 3)
                        {
                            habitacion[1][c] = 2;
                        }
                    }
                }
                break;
            case 1: //puerta sur
                for(int f = 0; f < habitacion.length;f++)
                {
                    for(int c = 0; c < habitacion[f].length;c++)
                    {
                        if(habitacion[f][c] == 8)
                        {
                            habitacion[f-1][c] = 2;
                        }
                    }
                }
                break;
            case 2: //puerta este
                for(int f = 0; f < habitacion.length;f++)
                {
                    for(int c = 0; c < habitacion[f].length;c++)
                    {
                        if(habitacion[f][c] == 9)
                        {
                            habitacion[1][c-1] = 2;
                        }
                    }
                }
                break;
            case 3: //puerta oeste
                for(int f = 0; f < habitacion.length;f++)
                {
                    for(int c = 0; c < habitacion[f].length;c++)
                    {
                        if(habitacion[f][c] == 10)
                        {
                            habitacion[f][1] = 2;
                        }
                    }
                }
                break;
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
