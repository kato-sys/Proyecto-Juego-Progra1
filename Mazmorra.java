public class Mazmorra {
    Habitacion entrada;
    Habitacion salida;
    
    public Mazmorra()
    {
        entrada = new Habitacion();
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
