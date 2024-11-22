
import java.util.Random;

public class misc {

    Random random = new Random();
    private int prob;

    public boolean probabilidad(int probabilidad) {
        prob = random.nextInt(100);
        return probabilidad > prob;
    }

    public int probArmas()
    {
        prob = random.nextInt(50);
        //0-30: básica
        if(prob < 30)
        {
            return 1;
        }
        //30-45: secreta
        else if(prob < 45)
        {
            return 0;

        }
        //45-50: legendaria
        else
        {
            return 2;
        }
    }

    public int probItems()
    {
        prob = random.nextInt(50);
        //0-15: armadura basica
        if(prob < 15)
        {
            return 0;
        }
        //15-25: armadura secreta
        else if(prob < 25)
        {
            return 2;
        }
        //25-30: legendaria
        else 
        {
            return 1;
        }
    }

    public Item probBuffs()
    {
        prob = random.nextInt(5);
        switch(prob)
        {
            case 0:
                return new Item("Buff de aumento de ataque", "buff_ataque", "Aumenta el ataque base entre un 10% a un 20% de manera acumulativa.", 20, 4);
                break;
            case 1:
                return new Item("Buff de aumento de defensa", "buff_defensa", "Aumenta la defensa en un 15% de manera acumulativa.", 15, 4);
                break;
            case 2:
                return new Item("Buff de sangre", "buff_sangre", "Por cada golpe, sube un 20% del daño final realizado a la vida en la batalla actual.", 20, 4);
                break;
            case 3:
                return new Item("Debuff de defensa", "debuff_defensa", "Reduce la defensa en un 15%.", 15, 4);
                break;
            case 4:
                return new Item("Debuff de ataque", "debuff_ataque", "Reduce el ataque en un 20%.", 20, 4);
                break;
            case 5:
                return new Item("Debuff de envenenar", "debuff_envenenar", "Por cada turno en batalla, pierde de 1, incrementando a 10 puntos de vida. Luego de eso sigue perdiendo 10 puntos por turno. El envenenamiento dura 20 turnos,o hasta tomar un buff de sangre.", 10, 20);
                break;

        }
        return -1;
    }
}
