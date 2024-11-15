import java.util.Random;
public class Mazmorra {
    Habitacion entrada;
    Habitacion salida;
    GeneracionHabitacion callGen = new GeneracionHabitacion();
    
    public Mazmorra()
    {
        entrada = callGen.getHabitacion();
    }
    
    public Habitacion generarNuevaHabitacion() {
        Random rand = new Random();
        Habitacion nuevaHabitacion = callGen.getHabitacion();
        int puertasGeneradas = 1; // Siempre se genera una puerta para conectar la habitación anterior. Es el 100% de la sinstrucciones

        // Probabilidades de generar más puertas
        if (rand.nextInt(100) < 60) puertasGeneradas++; // 60% de probabilidad de generar otra puerta
        if (rand.nextInt(100) < 40) puertasGeneradas++; // 40% de probabilidad de generar una tercera puerta
        if (rand.nextInt(100) < 20) puertasGeneradas++; // 20% de probabilidad de generar una cuarta puerta

        // Generar las puertas de la habitación según las probabilidades
        while (puertasGeneradas > 0) {
            int puerta = rand.nextInt(4) + 1; // 1: Norte, 2: Sur, 3: Este, 4: Oeste
            // Si ya existe una puerta en esa dirección, no la creamos
            switch (puerta) {
                case 1:
                    if (nuevaHabitacion.arriba == null) nuevaHabitacion.arriba = callGen.getHabitacion();
                    break;
                case 2:
                    if (nuevaHabitacion.abajo == null) nuevaHabitacion.abajo = callGen.getHabitacion();
                    break;
                case 3:
                    if (nuevaHabitacion.derecha == null) nuevaHabitacion.derecha = callGen.getHabitacion();
                    break;
                case 4:
                    if (nuevaHabitacion.izquierda == null) nuevaHabitacion.izquierda = callGen.getHabitacion();
                    break;
                }
            puertasGeneradas--;
        }
        return nuevaHabitacion;
    }


    public Habitacion irSiguiente(String cualPuerta, Habitacion habitacionActual)
    {
        Habitacion siguiente = null;
        switch(cualPuerta)
        {
            case "este":
                if (habitacionActual.derecha == null) {
                    habitacionActual.derecha = generarNuevaHabitacion();
                }
                System.out.println("Entraste por la Puerta Este a la Siguiente Habitación.");
                habitacionActual = habitacionActual.derecha;
                break;
            case "oeste":
                if (habitacionActual.izquierda == null) {
                    habitacionActual.izquierda = generarNuevaHabitacion();
                }
                System.out.println("Entraste por la Puerta Oeste a la Siguiente Habitación.");
                habitacionActual = habitacionActual.izquierda;
                break;
            case "norte":
                if (habitacionActual.arriba == null) {
                    habitacionActual.arriba = generarNuevaHabitacion();
                }
                System.out.println("Entraste por la Puerta Norte a la Siguiente Habitación.");
                habitacionActual = habitacionActual.arriba; 
                break;
            case "sur":
                if (habitacionActual.abajo == null) {
                    habitacionActual.abajo = generarNuevaHabitacion();
                }
                System.out.println("Entraste por la Puerta Sur a la Siguiente Habitación.");
                habitacionActual = habitacionActual.abajo; 
                break;
            default:
                System.out.println("no sé que pasó aquí jaja oops");
                break;
        }
     return siguiente;   
    }
}
