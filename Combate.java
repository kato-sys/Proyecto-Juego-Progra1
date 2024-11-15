import java.util.Scanner;
import java.util.Random;
public class Combate {
    private Misc misc = new Misc();
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

                        int probabilidadDrop = 30; //En teoría esto debe de tener un 30% de probabilidad de que el enemigo dropee el item.
                        if(misc.probabilidad(probabilidadDrop)){
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

    public Item debuffAleatorio()
    {
        int num = rand.nextInt(2);
        Item debuff = null;
        switch(num)
        {
            case 0:
                debuff = new Item("Debuff de Ataque", "debuff_ataque", "Aumenta el ataque base entre un 10% a un 20% de manera acumulativa.", 15, 1);
            case 1:
                debuff = new Item("Debuff de Defensa", "debuff_defensa", "Reduce la defensa en un 15%.", 15, 1);
            case 2:
                debuff = new Item("Debuff de Envenenar", "debuff_envenenar", "Por cada turno en batalla, pierde de 1, incrementando a 10 puntos de vida. Luego de eso sigue perdiendo 10 puntos por turno. El envenenamiento dura 20 turnos,o hasta tomar un buff de sangre.", 5, 20);
        }
        return debuff;

    }

    public Item buffAleatorio()
    {
        int num = rand.nextInt(2);
        Item buff = null;
        switch(num)
        {
            case 0:
                buff = new Item("Buff de Ataque", "buff_ataque", "Aumenta el ataque base entre un 10% a un 20% de manera acumulativa.", 15, 1);
            case 1:
                buff = new Item("Buff de Defensa", "buff_defensa", "Aumenta el ataque base en un 15% de manera acumulativa.", 15, 1);
            case 2:
                buff = new Item("Buff de Sangre", "buff_sangre", "Por cada golpe, sube un 20% del daño final realizado a la vida en la batalla actual.", 20, 1);
        }
        return buff;
    }
}
