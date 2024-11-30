//Imports de librerías necesarias.
import java.util.Random;
import java.util.Scanner;


public class Combate {
    //Definiciones de clases
    private misc misc = new misc();
    private Random rand = new Random();
    private Scanner input = new Scanner(System.in);

    public void combate(Enemigo[] enemigos, Jugador jugador, int[][] habitacion, int index, Habitacion HabitacionGenerada) {
        System.out.println("¡Inicio de combate!");
        int playerdecision;

        //Acá el enemigo utiliza un buff si lo tiene.
        usarItemsBuffEnemigo(enemigos[index],jugador);
        boolean combateSigue = true;
        while (combateSigue) {
            System.out.println("VIDA: " + jugador.getVida());
            //Aquí el enemigo usa ítems si su vida es 50% O menos.
            if(enemigos[index].getVida() < enemigos[index].getVidaInicial() * 0.5){
              usarItemsVidaEnemigo(enemigos[index],jugador);
            }
            //Aquí el jugador se le da la elección de actuar. Con dos opciones, 0 para uitilizar un item. Y 1 para atacar.
            System.out.println("Que desea hacer? [0] para usar item, [1] para atacar.");
            playerdecision = input.nextInt();
            switch (playerdecision) {
                case 0:
                    enemigos[index].atacar(jugador);
                    if (jugador.getVida() <= 0) {
                        System.out.println(jugador.getNombre() + " ha sido derrotado.");
                    }
                    jugador.usarItem(jugador, enemigos[index]);
                    break;
                case 1:
                    jugador.atacar(enemigos[index]);
                    //Acá cada vez que el jugador ataca, se revisa la vida del enemigo. Si es cero, es derrotado.
                    if (enemigos[index].getVida() == 0) {
                        System.out.println(enemigos[index].getNombre() + " ha sido derrotado.");
                        //Acá el enemigo suelta un item si tiene, y si es derrotado. 
                        dejarItemAlDerrotarEnemigo(enemigos[index],jugador);
                        habitacion[enemigos[index].getPosFila()][enemigos[index].getPosColumna()] = 0;
                      // Verificar si hay más enemigos
                        if (!HabitacionGenerada.quedanEnemigos()) {
                          System.out.println("Todos los enemigos han sido derrotados.");
                        }
                        enemigos[index] = null;
                        combateSigue = false;
                        break;      
                    }
                    // Si el enemigo sigue vivo, ataca al jugador.
                    enemigos[index].atacar(jugador);
                    if (jugador.getVida() == 0) {
                        System.out.println(jugador.getNombre() + " ha sido derrotado.");
                        combateSigue = false;
                    }
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }


            
        }
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
          Item itemDrop = rand.nextBoolean() ? jugador.getInventario().recogerGenerarArma(jugador) :jugador.getInventario().recogerGenerarArma(jugador);
          if(itemDrop != null){
            System.out.println("El enemigo soltó item al ser derrotado. Soltó un: " + itemDrop.getNombre());
          }
        }
      }
    }
    
}
