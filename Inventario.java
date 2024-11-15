import java.util.Random;
import java.util.Scanner;

public class Inventario {
    public Scanner myScanner = new Scanner(System.in);
    public Random rand = new Random();
    
    public Item[] inventario_items = new Item[3];
    public Item arma_equipped = null;
    public Item armadura_equipped = null;

    // cuantos items hay
    public int cuantosItems = -1;

    // prin el inv
    public void printInventario() {
        for (Item inventario_item : inventario_items) {
            if (inventario_item != null) {
                System.out.println(inventario_item.getNombre() + ": " + inventario_item.getDescripcion());
            }
        }
    }

    // Añade item
    public void addInventario(Item objeto, String tipo) {
        switch (tipo) {
            case "item" -> {
                cuantosItems += 1;
                inventario_items[cuantosItems] = objeto;
            }
            case "arma" -> arma_equipped = objeto;
            case "armadura" -> armadura_equipped = objeto;
        }
    }

    // remueve item del inventario
    public void removeItem(int index) {
        if (index >= 0 && index < cuantosItems) {
            inventario_items[index] = null; // remueve item
            for (int i = index; i < cuantosItems; i++) {
                inventario_items[i] = inventario_items[i + 1]; // mueve los items hacia la "izquierda"
            }
            cuantosItems--;
        }
    }

    public int getCuantosItems() { 
        return cuantosItems; 
    }
    public Item[] getInventarioItems() { 
        return inventario_items; 
    }
    public Item getArma() { 
        return arma_equipped; 
    }

    // Generates a random weapon
    public Item generarArma() {
        int seleccionTipoArma = rand.nextInt(3);
        switch (seleccionTipoArma) {
            case 0:
                return new Item("Arma Secreta", "arma_secreta", "Esta ataca porcentualmente, reduciendo la vida en un 50% de la vida actual por cada ataque, agregado al ataque base del agente (cada vez pegará menos que la anterior).", 50, 1);
            case 1: 
                return new Item("Arma Básica", "arma_basica", "Aumenta el daño en un 20%.", 20, 1);
            case 2: 
                return new Item("Arma Legendaria", "arma_legendaria", "El ataque base se multiplica por 2.", 6, 1);
            default:
                return null;
        }
    }

    // Genera item random
    public Item generarItem() {
        int seleccionTipoItem = rand.nextInt(9);
        switch (seleccionTipoItem) {
            case 0:
                return new Item("Armadura Básica", "armadura_base", "Reduce el daño en un 20%. Se pierde un 2% con cada golpe recibido. ", 20, 50);
            case 1:
                return new Item("Armadura Legendaria", "armadura_legendaria", "Reduce todo ataque en 50%.", 50, 3);
            case 2:
                return new Item("Armadura Secreta", "armadura_secreta", "Reduce el daño en un 30%. Se pierde un 2% con cada golpe recibido, pero luego de una batalla recupera todos los puntos de armadura.", 30, 50);
            case 3:
                return new Item("Buff de Ataque", "buff_ataque", "Aumenta el ataque base entre un 10% a un 20% de manera acumulativa.", 15, 1);
            case 4:
                return new Item("Buff de Defensa", "buff_defensa", "Aumenta el ataque base en un 15% de manera acumulativa.", 15, 1);
            case 5:
                return new Item("Buff de Sangre", "buff_sangre", "Por cada golpe, sube un 20% del daño final realizado a la vida en la batalla actual.", 20, 1);
            case 6:
                return new Item("Debuff de Ataque", "debuff_ataque", "Aumenta el ataque base entre un 10% a un 20% de manera acumulativa.", 15, 1);
            case 7:
                return new Item("Debuff de Defensa", "debuff_defensa", "Reduce la defensa en un 15%.", 15, 1);
            case 8:
                return new Item("Debuff de Envenenar", "debuff_envenenar", "Por cada turno en batalla, pierde de 1, incrementando a 10 puntos de vida. Luego de eso sigue perdiendo 10 puntos por turno. El envenenamiento dura 20 turnos,o hasta tomar un buff de sangre.", 5, 20);
            default:
                return null;
        }
    }
}
