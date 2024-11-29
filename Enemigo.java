public class Enemigo {
    //Variables del enemigo.
    private String nombreEnemigo;
    private int vidaEnemigo;
    private int ataqueEnemigo;
    private int defensaEnemigo;
    private boolean tieneDebuff; 
    private boolean envenenado;
    public Inventario inventario;
    private int enemigoVidaInicial;
    private int posFila;  // Agregamos la posición en la fila
    private int posColumna; 
    
    //Método constructor de un enemigo.
    public Enemigo(String nombre, int vida, int ataque, int defensa, int posFila, int posColumna) {
        this.nombreEnemigo = nombre;
        this.vidaEnemigo = vida;
        this.enemigoVidaInicial = vida;
        this.ataqueEnemigo = ataque;
        this.defensaEnemigo = defensa;
        this.envenenado = false;
        this.tieneDebuff = false;
        this.inventario = new Inventario();
        this.posFila = posFila; // Asignamos la posición en i
        this.posColumna = posColumna; // Asignamos la posición en j
    }

    // Métodos getters 
    //Posición enemigo.
    public int getPosFila() {
        return posFila;
    }
    public int getPosColumna() {
        return posColumna;
    }
    //Stats e Inventario del Enemigo.
    public int getVidaInicial(){
      return enemigoVidaInicial;
    }
    public Inventario getInventario(){
      return inventario;
    }
    public String getNombre() { 
        return nombreEnemigo; 
    }
    public int getVida() { 
        return vidaEnemigo; 
    }
    public int getAtaque() { 
        return ataqueEnemigo; 
    }
    public int getDefensa() { 
        return defensaEnemigo; 
    }
    // Métodos Setters
    //Posición.
    public void setPosFila(int nuevaPosFila) {
        this.posFila = nuevaPosFila;
    }
    public void setPosColumna(int nuevaPosColumna) {
        this.posColumna = nuevaPosColumna;
    }
    public void setVida(int nuevaVida){
      this.vidaEnemigo = nuevaVida;
    }
    public void setAtaque(int nuevoAtaque){
      this.ataqueEnemigo = nuevoAtaque;
    }
    public void setDefensa(int nuevaDefensa){
      this.defensaEnemigo = nuevaDefensa;
    }


    public void recibirDaño(int daño) {
        int dañoFinal = daño - defensaEnemigo; // reduce daño con su defensa
        if (dañoFinal < 0) dañoFinal = 0;
        this.vidaEnemigo -= dañoFinal;
        System.out.println(nombreEnemigo + " recibe " + dañoFinal + " de daño. Vida restante: " + vidaEnemigo);
        //Daño adicional si tiene debuff
        if (tieneDebuff) {
            this.vidaEnemigo -= 5;
            System.out.println("¡El Debuff te hace recibir daño adicional!");
        }
        if (this.vidaEnemigo < 0) {
            this.vidaEnemigo = 0;
        }
    }

    // Veneno (futuro)
    public void envenenar() {
        this.envenenado = true;
        System.out.println(nombreEnemigo + " ha sido envenenado.");
    }

    // daño de veneno
    public void recibirDañoPorVeneno() {
        if (envenenado) {
            recibirDaño(5);
            System.out.println(nombreEnemigo + " ha recibido 5 de daño por veneno.");
        }
    }

    public void activarDebuff() {
        this.tieneDebuff = true; 
    }
    public void desactivarDebuff() { 
        this.tieneDebuff = false; 
    }

    public String MonstruoStatus() {
        return "Nombre: " + nombreEnemigo + ", Vida: " + vidaEnemigo + ", Ataque: " + ataqueEnemigo;
    }

    public void atacar(Jugador objetivo) {
        int daño = this.ataqueEnemigo;
        objetivo.recibirDaño(daño);
        System.out.println(this.nombreEnemigo + " ataca a " + objetivo.getNombre() + " y le causa " + daño + " de daño.");
    }
}
