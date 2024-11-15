
import java.util.Random;

public class misc {

    Random random = new Random();
    private int prob;

    public boolean probabilidad(int probabilidad) {
        prob = random.nextInt(100);
        return probabilidad > prob;
    }

}
