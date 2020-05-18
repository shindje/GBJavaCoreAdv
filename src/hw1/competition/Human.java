package hw1.competition;

public class Human implements Runable, Jumpable {
    static int MAX_JUMP_HEIGHT = 2;
    static int MAX_RUN_LENTGH = 100;
    boolean contesting;

    String name;

    Human (String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return "Человек " + name;
    }

    @Override
    public int getMaxJumpHeight() {
        return MAX_JUMP_HEIGHT;
    }

    @Override
    public int getMaxRunLength() {
        return MAX_RUN_LENTGH;
    }

    @Override
    public void setContesting(boolean stopped) {
        this.contesting = stopped;
    }

    @Override
    public boolean isContesting() {
        return contesting;
    }
}
