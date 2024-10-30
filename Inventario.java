public class Inventario
{
    /**
     * Aquí se guardan los objetos ítems y armas ya preexistentes 
     * en vectores que funcionan como el inventario
     */
    private Item[] inventario_items;
    public Item arma_equipped;
    public Item armadura_equipped;
    
    //lleva cuenta de la cantidad de objetos en el arreglo, para ahorrarnos revisar los espacios null
    private int cuantosItems;

    public Inventario()
    {
        inventario_items = new Item[3];
        cuantosItems = -1;
    }

    
      public void printInventario()
      {  
        for(int i = 0; i < inventario_items.length; i++)
        {
            System.out.println(inventario_items[i].getNombre() + ": " + inventario_items[i].getDescripcion());
        }
        //se podrían imprimir el arma y armadura si se quisiera también
     }

    public void addInventario(Item objeto, String tipo) //nuevamente, booleano es para saber si el objeto a agregar es un item o un arma
    {
        switch(tipo)
        {
            case "item":
                cuantosItems++;
                inventario_items[cuantosItems] = objeto;
                break;
            case "arma":
                arma_equipped = objeto;
                break;
            case "armadura":
                armadura_equipped = objeto;
        }
    }
    public void removeItem(int index) {
        if (index >= 0 && index < cuantosItems) {
            inventario_items[index] = null; // Elimina el ítem
            for (int i = index; i < cuantosItems; i++) {
                inventario_items[i] = inventario_items[i + 1]; // Desplaza los ítems
            }
            cuantosItems--; // Decrementa la cantidad de ítems
        }
    }
    public int getCuantosItems() {
        return cuantosItems;
    }

    // Método para obtener el arreglo de ﾃｭtems
    public Item[] getInventarioItems() {
        return inventario_items;
    }

    // Método para obtener el arreglo de armas
    public Item getArma() {
        return arma_equipped;
    }

}
