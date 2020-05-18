package hw1.competition;

public class Wall implements Obstacle{
    int height;

    Wall(int height) {
        this.height = height;
    }

    @Override
    public boolean overcome(Contestant c) {
        if (c instanceof Jumpable) {
            Jumpable j = (Jumpable) c;
            if (j.getMaxJumpHeight() < height) {
                System.out.println(c.getName() + " не смог перепрыгнуть " + height + " м");
                return false;
            } else {
                j.jump(this, height);
                return true;
            }
        } else {
            System.out.println(c.getName() + " не умеет прыгать");
            return false;
        }
    }
}
