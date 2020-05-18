package hw1.competition;

public interface Runable extends Contestant {
    int getMaxRunLength();

    default void run(Obstacle o, int length) {
        System.out.println(getName() + " пробежал " + length + " м");
    }
}
