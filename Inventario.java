
public class Inventario
{
    /**
     * Aquí se guardan los objetos ítems y armas ya preexistentes 
     * en vectores que funcionan como el inventario
     */
    public Item[] inventario_items;
    public Item arma_equipped;
    public Item armadura_equipped;

    
    //lleva cuenta de la cantidad de items en el arreglo, para ahorrarnos revisar los espacios null
    private int cuantosItems;

    public Inventario()
    {
        inventario_items = new Item[12];

    }

    
      public void printInventario()
      {
            for(int i = 0; i < inventario_items.length; i++)
            {
                System.out.println(inventario_items[i].getNombre() + ": " + inventario_items[i].getDescripcion() + "\n DEF boost: " + inventario_items[i].getPoder());
            }
     }

    public void addInventario(Item objeto, String tipo_item) //para saber si es item, arma o armadura
     {
        switch(tipo_item)
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
            break;
        }
     }

}
