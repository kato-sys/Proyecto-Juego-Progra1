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
                inventario_items[i] = inventario_items[i + 1]; // muve los items hacia la "izquierda"
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
                return new Item("Arma Secreta", "arma_secreta", "+5 de daño", 5, 1);
            case 1: 
                return new Item("Arma Básica", "arma_basica", "+3 de daño", 3, 1);
            case 2: 
                return new Item("Arma Legendaria", "arma_legendaria", "+6 de daño", 6, 1);
            default:
                return null;
        }
    }

    // Genera item random
    public Item generarItem() {
        int seleccionTipoItem = rand.nextInt(3);
        switch (seleccionTipoItem) {
            case 0:
                return new Item("Armadura Base", "armadura_base", "Reduce ataque recibido hasta agotarse", 4, 3);
            case 1:
                return new Item("Armadura Legendaria", "armadura_legendaria", "Reduce el daño recibido a la mitad", 7, 1);
            case 2:
                return new Item("Buff de Ataque", "buff_ataque", "Incrementa el ataque en 5-10%", 5, 1);
            default:
                return null;
        }
    }
}
