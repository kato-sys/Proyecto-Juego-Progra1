import java.util.Random;
import java.util.Scanner;

public class Inventario {
    public Scanner myScanner = new Scanner(System.in);
    public Random rand = new Random();
    misc misc = new misc();
    
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
    public Item getArmadura() {
        return armadura_equipped;
    }

    // Generates a random weapon
    public Item recogerGenerarArma(Jugador jugador) {
        Item arma = misc.probArmas();
        if (arma.getTipo().equals("armadura_base") || arma.getTipo().equals("armadura_legendaria")  || arma.getTipo().equals("armadura_secreta")) {
            jugador.setDefensa(arma.getPoder());
        } else {
            jugador.setAtaque(arma.getPoder());
        }
        return arma;
    }

    // Genera item random
    public Item recogerGenerarItem() {
        Item buff = misc.probBuffs();
        addInventario(buff, buff.getTipo());
        return buff;
        
    }
}
