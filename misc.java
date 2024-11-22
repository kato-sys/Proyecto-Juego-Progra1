
import java.util.Random;

public class misc {

    Random random = new Random();
    private int prob;

    public boolean probabilidad(int probabilidad) {
        prob = random.nextInt(100);
        return probabilidad > prob;
    }

    public Item probArmas()
    {
        prob = random.nextInt(79);
        if(prob < 30) //0-29: arma básica
        {
            return new Item("Arma Básica", "arma_basica", "Aumenta el daño en un 20%.", 20, 1);
        }
        else if(prob < 45)  //30-44: arma secreta
        {
            return new Item("Arma Secreta", "arma_secreta", "Esta ataca porcentualmente, reduciendo la vida en un 50% de la vida actual por cada ataque, agregado al ataque base del agente (cada vez pegará menos que la anterior).", 50, 1);

        }
        else if(prob < 50) //45-49: arma legendaria
        {
            return new Item("Arma Legendaria", "arma_legendaria", "El ataque base se multiplica por 2.", 6, 1);
        }
        else if(prob < 65) //50-64: armadura basica
        {
            return new Item("Armadura Básica", "armadura_base", "Reduce el daño en un 20%. Se pierde un 2% con cada golpe recibido. ", 20, 50);
        }
        else if(prob < 75) //65-74: armadura secreta
        {
            return new Item("Armadura Secreta", "armadura_secreta", "Reduce el daño en un 30%. Se pierde un 2% con cada golpe recibido, pero luego de una batalla recupera todos los puntos de armadura.", 30, 50);
        }
        else //75-79: armadura legendaria
        {
            return new Item("Armadura Legendaria", "armadura_legendaria", "Reduce todo ataque en 50%.", 50, 3);
        }
    }

    public Item probBuffs()
    {
        prob = random.nextInt(5);
        switch(prob)
        {
            case 0:
                return new Item("Buff de aumento de ataque", "buff_ataque", "Aumenta el ataque base entre un 10% a un 20% de manera acumulativa.", 20, 4);
            case 1:
                return new Item("Buff de aumento de defensa", "buff_defensa", "Aumenta la defensa en un 15% de manera acumulativa.", 15, 4);
            case 2:
                return new Item("Buff de sangre", "buff_sangre", "Por cada golpe, sube un 20% del daño final realizado a la vida en la batalla actual.", 20, 4);
            case 3:
                return new Item("Debuff de defensa", "debuff_defensa", "Reduce la defensa en un 15%.", 15, 4);
            case 4:
                return new Item("Debuff de ataque", "debuff_ataque", "Reduce el ataque en un 20%.", 20, 4);
            case 5:
                return new Item("Debuff de envenenar", "debuff_envenenar", "Por cada turno en batalla, pierde de 1, incrementando a 10 puntos de vida. Luego de eso sigue perdiendo 10 puntos por turno. El envenenamiento dura 20 turnos,o hasta tomar un buff de sangre.", 10, 20);
        }
        return null;
    }
}
