public class Enemigo{
    String nombreEnemigo;
    int vidaEnemigo;
    int ataqueEnemigo;
    //Método constructora
    public Enemigo(String nombre, int vida, int ataque){
        this.nombreEnemigo = nombre;
        this.vidaEnemigo = vida;
        this.ataqueEnemigo = ataque;
    }

    //Getters    
    public String getNombre(){
        return nombreEnemigo;
    }

    public int getVida(){
        return vidaEnemigo;
    }

    //Setters
    public void setVida(int salud) {
        this.vidaEnemigo = salud;
    }
    public int getAtaque() {
        return ataqueEnemigo;
    }

    public void atacar(Jugador objetivo) {
        int daño = this.ataqueEnemigo;
        objetivo.recibirDaño(daño);
        System.out.println(this.nombreEnemigo + " ataca a " + objetivo.getNombre() + " y le causa " + daño + " de daño.");
    }

    public void recibirDaño(int daño) {
        this.vidaEnemigo -= daño;
        if (this.vidaEnemigo < 0) {
            this.vidaEnemigo = 0;
        }
    }

    public String MonstruoStatus() {
        return "Nombre: " + nombreEnemigo + ", Vida: " + vidaEnemigo + ", Ataque: " + ataqueEnemigo;
    }
}