
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
}
