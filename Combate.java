import java.util.Scanner;

public class Combate {
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
