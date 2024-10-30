public class Item {
    private String nombre;
    private String tipo; // "arma", "armadura", "buff", etc.
    private String descripcion;
    private int poder; // Valor de ataque, defensa o porcentaje de efecto
    private int durabilidad; // Para la durabilidad de armaduras... O algo así.

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
                int daño = (int) (enemigo.getVida() * 0.5) + jugador.getAtaque();
                enemigo.recibirDaño(daño);
                break;
            case "arma_basica":
                enemigo.recibirDaño(jugador.getAtaque());
                break;
            case "arma_legendaria":
                enemigo.recibirDaño(jugador.getAtaque() * 2);
                break;
            case "armadura_base":
                jugador.recibirDaño(poder);
                durabilidad--;
                if (durabilidad <= 0) {
                    System.out.println("La armadura se ha roto.");
                }
                break;
            case "armadura_legendaria":
                jugador.recibirDaño(enemigo.getAtaque() / 2);
                break;
            case "buff_ataque":
                jugador.setAtaque(jugador.getAtaque() + (int) (jugador.getAtaque() * (Math.random() * 0.05 + 0.05)));
                break;
            case "debuff_defensa":
                enemigo.reducirDefensa((int) (enemigo.getDefensa() * 0.15));
                break;
            case "envenenar":
                enemigo.envenenar();
                break;
            default:
                System.out.println("El ítem no tiene un efecto válido.");
        }
    }
}
