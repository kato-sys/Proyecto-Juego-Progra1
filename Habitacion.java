public class Habitacion {
    Habitacion arriba;
    Habitacion abajo;
    Habitacion izquierda;
    Habitacion derecha;
    int[][] habitacionTamano;

    public Habitacion(int[][] habitacionTamano, Habitacion arriba, Habitacion abajo, Habitacion izquierda, Habitacion derecha) {
        this.habitacionTamano = habitacionTamano;
        this.arriba = arriba;
        this.abajo = abajo;
        this.izquierda = izquierda;
        this.derecha = derecha;
    }

    public int[][] tamano(){
        return habitacionTamano;
    }

    public void setvalor(int x, int y, int valor){
        habitacionTamano[x][y] = valor;
    }
}
