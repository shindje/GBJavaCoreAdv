package hw1.competition;

public class Main {
    public static void main(String[] args) {
        Contestant[] contestants = new Contestant[] {
                new Cat("Барсик"), new Human("Иван Иваныч"), new Robot("R2D2")
        };

        Obstacle[] obstacles = new Obstacle[] {
                new Wall(1),
                new RunningTrack(50)
        };

        for (Contestant c: contestants) {
            c.setContesting(true);
        }

        for (Obstacle o: obstacles) {
            for (Contestant c: contestants) {
                if (c.isContesting()) {
                    c.setContesting(o.overcome(c));
                }
            }
        }
    }
}
