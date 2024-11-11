import java.util.Scanner;
import java.util.Random;
public class Combate {
    private Random rand = new Random();
    private Scanner input = new Scanner(System.in);

    public void combate(Enemigo enemigo, Jugador jugador, int[][] habitacion) {
        System.out.println("¡Inicio de combate!");
        int playerdecision;

        while (jugador.getVida() > 0 && enemigo.getVida() > 0) {
            System.out.println("Que desea hacer? [0] para usar item, [1] para atacar.");
            playerdecision = input.nextInt();
            switch (playerdecision) {
                case 0:
                    enemigo.atacar(jugador);
                    if (jugador.getVida() <= 0) {
                        System.out.println(jugador.getNombre() + " ha sido derrotado.");
                    }

                    jugador.usarItem(jugador, enemigo);
                    break;
            
                case 1:
                    jugador.atacar(enemigo);
                    if (enemigo.getVida() <= 0) {
                        System.out.println(enemigo.getNombre() + " ha sido derrotado.");
                        for (int i = 0; i < habitacion.length; i++) {
                            for (int j = 0; j < habitacion[0].length; j++) {
                                if (habitacion[i][j] == 6) {
                                    habitacion[i][j] = 0;
                                }
                            }
                        }

                        double probabilidadDrop = 0.3; //En teoría esto debe de tener un 30% de probabilidad de que el enemigo dropee el item.
                        if(rand.nextDouble() < probabilidadDrop){
                          Item itemDrop = rand.nextBoolean() ? jugador.inventario.generarArma() : jugador.inventario.generarItem();
                          if (itemDrop != null){
                            System.out.println("¡El enemigo soltó un ítem cuando lo derrotaste! Soltó un: "+ itemDrop.getNombre());
                            jugador.inventario.addInventario(itemDrop,"item");
                          }
                        }

                    }

                    enemigo.atacar(jugador);
                    if (jugador.getVida() <= 0) {
                        System.out.println(jugador.getNombre() + " ha sido derrotado.");
                    }
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
        }
    }
}
