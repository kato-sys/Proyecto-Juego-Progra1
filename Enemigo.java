public class Enemigo{
    String nombreEnemigo;
    int vidaEnemigo;
    int ataqueEnemigo;
    int defensa; // Agrega defensa como atributo
    boolean envenenado; // Estado para el envenenamiento
    //Método constructora
    public Enemigo(String nombre, int vida, int ataque, int defensa){
        this.nombreEnemigo = nombre;
        this.vidaEnemigo = vida;
        this.ataqueEnemigo = ataque;
        this.defensa = defensa;
        this.envenenado = false; // Inicialmente no está envenenado
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
    public int getDefensa() {
        return defensa; // Método para obtener defensa
    }
    public boolean isEnvenenado() {
        return envenenado; // Método para ver si está envenenado
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

    public void reducirDefensa(int cantidad) {
        defensa -= cantidad; // Reduce la defensa
        if (defensa < 0) {
            defensa = 0; // Asegúrate de que la defensa no sea negativa
        }
        System.out.println(nombreEnemigo + " ahora tiene " + defensa + " de defensa.");
    }

    public void envenenar() {
        envenenado = true; // Cambia el estado a envenenado
        System.out.println(nombreEnemigo + " ha sido envenenado.");
    }

    public void recibirDañoPorVeneno() {
        if (envenenado) {
            // Aquí puedes definir el daño por veneno, por ejemplo, 5 de daño por turno
            recibirDaño(5); // Ajusta el valor según sea necesario
            System.out.println(nombreEnemigo + " ha recibido 5 de daño por veneno.");
        }
    }

    public String MonstruoStatus() {
        return "Nombre: " + nombreEnemigo + ", Vida: " + vidaEnemigo + ", Ataque: " + ataqueEnemigo;
    }
}