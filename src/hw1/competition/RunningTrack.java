package hw1.competition;

public class RunningTrack implements Obstacle{
    int length;

    RunningTrack(int length) {
        this.length = length;
    }

    @Override
    public boolean overcome(Contestant c) {
        if (c instanceof Runable) {
            Runable r = (Runable) c;
            if (r.getMaxRunLength() < length) {
                System.out.println(c.getName() + " не смог пробежать " + length + " м");
                return false;
            } else {
                r.run(this, length);
                return true;
            }
        } else {
            System.out.println(c.getName() + " не умеет бегать");
            return false;
        }
    }
}
