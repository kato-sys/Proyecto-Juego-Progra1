import java.util.Random;
public class Mazmorra {
    Habitacion entrada;
    Habitacion salida;
    
    public Mazmorra()
    {
        entrada = new Habitacion();
    }
    
    public Habitacion generarNuevaHabitacion() {
        Random rand = new Random();
        Habitacion nuevaHabitacion = new Habitacion();
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
                    if (nuevaHabitacion.arriba == null) nuevaHabitacion.arriba = new Habitacion();
                    break;
                case 2:
                    if (nuevaHabitacion.abajo == null) nuevaHabitacion.abajo = new Habitacion();
                    break;
                case 3:
                    if (nuevaHabitacion.derecha == null) nuevaHabitacion.derecha = new Habitacion();
                    break;
                case 4:
                    if (nuevaHabitacion.izquierda == null) nuevaHabitacion.izquierda = new Habitacion();
                    break;
                }
            puertasGeneradas--;
        }
        return nuevaHabitacion;
    }


    public Habitacion irSiguiente(int cualPuerta, Habitacion habitacionObj)
    {
        switch(cualPuerta)
        {
            case 1:
            if(habitacionObj.arriba != null)
                    {
                        habitacionObj = habitacionObj.arriba;
                    }
                    else
                    {
                        Habitacion siguiente = new Habitacion();
                        habitacionObj.arriba = siguiente;
                        siguiente.abajo = habitacionObj;
                        habitacionObj = siguiente;
                    }  
                break;
            case 2:
            if(habitacionObj.abajo != null)
                    {
                        habitacionObj = habitacionObj.abajo;
                    }
                    else
                    {
                        Habitacion siguiente = new Habitacion();
                        habitacionObj.abajo = siguiente;
                        siguiente.arriba = habitacionObj;
                        habitacionObj = siguiente;
                    }  
                break;
            case 3:
            if(habitacionObj.izquierda != null)
                    {
                        habitacionObj = habitacionObj.izquierda;
                    }
                    else
                    {
                        Habitacion siguiente = new Habitacion();
                        habitacionObj.izquierda = siguiente;
                        siguiente.derecha = habitacionObj;
                        habitacionObj = siguiente;
                    }  
                break;
            case 4:
            if(habitacionObj.derecha != null)
                    {
                        habitacionObj = habitacionObj.derecha;
                    }
                    else
                    {
                        Habitacion siguiente = new Habitacion();
                        habitacionObj.derecha = siguiente;
                        siguiente.izquierda = habitacionObj;
                        habitacionObj = siguiente;
                    }  
                break;
        }
     return habitacionObj;   
    }
}
