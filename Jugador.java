public class Jugador{
    String jugadorNombre;
    int jugadorVida;
    int jugadorAtaque;
    int defensa;
    private Inventario inventario; // Agrega este atributo
    //Método constructor
    public Jugador(String nombre, int vida, int ataque, int defensa){
        this.jugadorNombre = nombre;
        this.jugadorVida = vida;
        this.jugadorAtaque = ataque;
        this.defensa = defensa;
        this.inventario = new Inventario();
    }

    //Getters
    public String getNombre(){
        return jugadorNombre;
    }
    public int getVida(){
        return jugadorVida;
    }
    public int getAtaque(){
        return jugadorAtaque;
    }
    public Inventario getInventario(){
        return inventario;
    }
    //Setters
    public void setVida(int nuevaVida){
        //Cambiar la vida para recibir daño durante el combate. 
        this.jugadorVida = nuevaVida;
    }
    //Esto es por si un item modifica el ataque del jugador.
    public void setAtaque(int nuevoAtaque){
        this.jugadorVida = nuevoAtaque;
    }


    //Combate
    //Metodo para atacar una referencia por parámetro de un monstruo
    public void atacar(Enemigo objetivo) {
        int daño = this.jugadorAtaque;
        objetivo.recibirDaño(daño);
        System.out.println(this.jugadorNombre + " ataca a " + objetivo.getNombre() + " y le causa " + daño + " de daño.");
    }
    //Método que recibe el daño reducir de la salud del jugador
    public void recibirDaño(int daño) {
        this.jugadorVida -= daño;
        if (this.jugadorVida < 0) {
            this.jugadorVida = 0;
        }
    }

    //Imprimir stats
    public String JugadorStatus() {
        return "Nombre: " + jugadorNombre + ", Vida: " + jugadorVida + ", Ataque: " + jugadorAtaque;
    }

}
