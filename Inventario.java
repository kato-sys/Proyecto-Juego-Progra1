
public class Inventario
{
    /**
     * Aquí se guardan los objetos ítems y armas ya preexistentes 
     * en vectores que funcionan como el inventario
     */
    private Item[] inventario_items;
    private Item[] inventario_armas;
    /**
     * Las variables cuant@s llevan cuenta de la cantidad de objetos en el arreglo, para ahorrarnos revisar los espacios null
     */
    private int cuantosItems;
    private int cuantasArmas;

    public Inventario()
    {
        inventario_items = new Item[12];
        inventario_armas = new Item[12];
        cuantosItems = -1;
        cuantasArmas = -1;
    }

    
      public void printInventario(boolean imprimirItems) //booleano es para impriir o items o armas
      {
        if(imprimirItems)
        {
            for(int i = 0; i < inventario_items.length; i++)
            {
                System.out.println(inventario_items[i].getNombre() + ": " + inventario_items[i].getDescripcion() + "\n DEF boost: " + inventario_armas[i].getPoder());
            }
        }
        else
        {
            for(int i = 0; i < inventario_armas.length; i++)
            {
                System.out.println(inventario_armas[i].getNombre() + ": " + inventario_armas[i].getDescripcion() + "\n ATK boost: " + inventario_armas[i].getPoder());
            }
        }
     }

    public void addInventario(Item objeto, boolean esItem) //nuevamente, booleano es para saber si el objeto a agregar es un item o un arma
     {
         if(esItem)
        {
            cuantosItems++;
            inventario_items[cuantosItems] = objeto;
        }
        else
        {
            cuantasArmas++;
            inventario_items[cuantasArmas] = objeto;
        }
     }

}
