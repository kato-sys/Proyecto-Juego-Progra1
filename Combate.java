import java.util.Scanner;
import java.util.Random;
public class Combate {
    private misc misc = new misc();
    private Random rand = new Random();
    private Scanner input = new Scanner(System.in);

    public void combate(Enemigo enemigo, Jugador jugador, int[][] habitacion) {
        System.out.println("¡Inicio de combate!");
        int playerdecision;

        //Acá el enemigo utiliza un buff si lo tiene.
        usarItemsBuffEnemigo(enemigo,jugador);

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
                        //Acá el enemigo suelta un item si tiene, y si es derrotado. 
                        dejarItemAlDerrotarEnemigo(enemigo,jugador);
                        break;    
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

            //Aquí el enemigo usa ítems si su vida es 50% O menos.
            if(enemigo.getVida() < enemigo.getVidaInicial() * 0.5){
              usarItemsVidaEnemigo(enemigo,jugador);
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

    private void usarItemsBuffEnemigo(Enemigo enemigo, Jugador jugador){
      Item[] items = enemigo.getInventario().getInventarioItems();
      for(int i = 0; i <= enemigo.getInventario().getCuantosItems(); i++) {
        if(items[i] != null && items[i].getTipo().contains("buff")){
          items[i].aplicarEfectoEnemigo(enemigo, jugador);
          System.out.println(enemigo.getNombre()+" usó un/a: "+ items[i].getNombre());
          enemigo.getInventario().removeItem(i);
        }
      }
    }
    private void usarItemsVidaEnemigo(Enemigo enemigo, Jugador jugador){
      Item[] items = enemigo.getInventario().getInventarioItems();
      for(int i = 0; i <= enemigo.getInventario().getCuantosItems(); i++){
        if (items[i] != null && items[i].getTipo().contains("vida")){
          items[i].aplicarEfectoEnemigo(enemigo, jugador);
          System.out.println(enemigo.getNombre() + " se curó usando un/a: "+ items[i].getNombre());
          enemigo.getInventario().removeItem(i);
        }
      }
    }
    private void dejarItemAlDerrotarEnemigo(Enemigo enemigo, Jugador jugador){
      Item[] items = enemigo.getInventario().getInventarioItems();
      int itemCount = enemigo.getInventario().getCuantosItems();
      if(itemCount > 0){
        //Esto es que deja caer un ítem aleatorio del inventario enemigo (Si es que hay items)
        int randomIndex = rand.nextInt(itemCount +1);
        Item itemDrop = items[randomIndex];
        System.out.println("El enemigo soltó un: "+ itemDrop.getNombre());
        jugador.getInventario().addInventario(itemDrop, "item");
        enemigo.getInventario().removeItem(randomIndex);
      }else{
        //Si no tiene items, ocurre lo de que tiene un 15% de probabilidad de que suelte un ítem aleatorio
        int probabilidadDrop = 15;
        if(misc.probabilidad(probabilidadDrop)){
          Item itemDrop = rand.nextBoolean() ? jugador.getInventario().generarArma() :jugador.getInventario().generarItem();
          if(itemDrop != null){
            System.out.println("El enemigo soltó item al ser derrotado. Soltó un: " + itemDrop.getNombre());
            jugador.getInventario().addInventario(itemDrop, "item");
          }
        }
      }
    }
    
}
