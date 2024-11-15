public class Item {
    public String nombre;
    public String tipo; // e.g., "arma", "armadura", "buff"
    public String descripcion;
    public int poder; 
    public int durabilidad; 

    public Item(String nombre, String tipo, String descripcion, int poder, int durabilidad) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.poder = poder;
        this.durabilidad = durabilidad;
    }


    public String getNombre() { 
        return nombre; 
    }
    public String getTipo() { 
        return tipo; 
    }
    public String getDescripcion() { 
        return descripcion; 
    }
    public int getPoder() { 
        return poder; 
    }
    public int getDurabilidad() { 
        return durabilidad; 
    }

    public void aplicarEfecto(Jugador jugador, Enemigo enemigo) {
        switch (tipo) {
            case "arma_secreta": 
                jugador.setAtaque(poder);
                break;
            case "arma_basica": 
                jugador.setAtaque(poder);
                break;
            case "arma_legendaria":
                jugador.setAtaque(poder);
                break;
            case "armadura_base":
                jugador.recibirDaño(poder);
                break;
            case "armadura_legendaria":
                jugador.recibirDaño(enemigo.getAtaque() / 2);
                break;
            case "armadura_secreta":
                jugador.recibirDaño(poder);
                break;
            case "buff_ataque": 
                jugador.setAtaque(jugador.getAtaque() + (int) (jugador.getAtaque() * 0.1));
                break;
            case "buff_defensa":
                jugador.setAtaque(poder);
                break;
            case "buff_sangre":
                enemigo.atacar(jugador); //???
                break;
            case "debuff_ataque": 
                enemigo.atacar(jugador); //???
            break;
            case "debuff_defensa":
                enemigo.atacar(jugador); //???
            break;
            case "debuff_sangre":
                enemigo.atacar(jugador); //???
                break;
            default:
            System.out.println("El ítem no tiene un efecto válido.");
            break;
        }
    }
}
