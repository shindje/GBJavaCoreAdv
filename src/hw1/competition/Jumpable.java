package hw1.competition;

public interface Jumpable extends Contestant {
    int getMaxJumpHeight();

    default void jump(Obstacle o, int height) {
        System.out.println(getName() + " перепргынул " + height + " м");
    }
}
