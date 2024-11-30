import java.util.Scanner;

public class Jugador {
    // Atributos
    String jugadorNombre;
    int jugadorVida;
    int jugadorAtaque;
    int defensa;
    int ultimaPosicionJugadorF;
    int ultimaPosicionJugadorC;
    private Scanner myScanner = new Scanner(System.in);
    public Inventario inventario; // inventario
    private boolean tieneDebuff; // status debuff
    public Movimiento callMovimiento;
    boolean bossDerrotado;

    public Jugador(String nombre, int vida, int ataque, int defensa) {
        this.jugadorNombre = nombre;
        this.jugadorVida = vida;
        this.jugadorAtaque = ataque;
        this.defensa = defensa;
        this.inventario = new Inventario();
        this.tieneDebuff = false;
        this.callMovimiento = new Movimiento();
        this.bossDerrotado = false;
    }

    public boolean getBossDerrotado(){
        return this.bossDerrotado;
    }
    public void getBossDerrotado(boolean derrotado){
        this.bossDerrotado = derrotado;
    }

    public int getUltimaPosicionJugadorF(){
        return ultimaPosicionJugadorF;
    }
        public int getUltimaPosicionJugadorC(){
        return ultimaPosicionJugadorC;
    }

    public String getNombre() { 
        return jugadorNombre; 
    }
    public int getVida() { 
        return jugadorVida; 
    }
    public int getAtaque() { 
        return jugadorAtaque;
    }
    public Inventario getInventario() { 
        return inventario;
    }

    public void setVida(int nuevaVida) { 
        this.jugadorVida = nuevaVida; 
    }
    public void setAtaque(int nuevoAtaque) { 
        this.jugadorAtaque = nuevoAtaque + jugadorAtaque; 
    }
    public void setDefensa(int nuevoDefensa){
        this.defensa = defensa + nuevoDefensa;
    }

    public void atacar(Enemigo objetivo) {
        int daño = this.jugadorAtaque;
        objetivo.recibirDaño(daño);
        //System.out.println(this.jugadorNombre + " ataca a " + objetivo.getNombre() + " y le causa " + daño + " de daño.");
    }

    public void recibirDaño(int daño) {
        this.jugadorVida -= (daño - defensa);
        if (tieneDebuff) {
            this.jugadorVida -= 5;
            System.out.println("¡El Debuff te hace recibir daño adicional!");
        }
        if (this.jugadorVida < 0) {
            this.jugadorVida = 0;
        }
    }

    public String JugadorStatus() {
        return "Nombre: " + jugadorNombre + ", Vida: " + jugadorVida + ", Ataque: " + jugadorAtaque;
    }

    public void activarDebuff() {
        this.tieneDebuff = true; 
    }
    public void desactivarDebuff() { 
        this.tieneDebuff = false; 
    }

    public boolean tieneDebuff() { 
        return this.tieneDebuff; 
    }

    public void recorrer(Jugador jugador, Enemigo enemigo) {
        callMovimiento.RecorridoHabitacion(jugador);
    }

    public void usarItem(Jugador var1, Enemigo var2) {
        inventario.printInventario();
        System.out.println("Elige un item para usar (indice 0, 1, 2): ");
        int var3 = myScanner.nextInt();
        myScanner.nextLine();
  
        try {
           if (var3 >= 0 && var3 <= inventario.getCuantosItems()) {
              Item var4 = inventario.getInventarioItems()[var3];
              if (var4 != null) {
                 var4.aplicarEfecto(var1, var2);
                 System.out.println("Has usado: " + var4.getNombre());
                 inventario.removeItem(var3);
              } else {
                 System.out.println("El item no es valido. Intenta de nuevo.");
              }
           } else {
              System.out.println("item invalido. Intenta de nuevo.");
           }
        } catch (NullPointerException | ArrayIndexOutOfBoundsException var5) {
           System.out.println("Error: El item seleccionado no es valido o no existe.");
        }
     }
}
