package hw1.competition;

public class Robot implements Runable, Jumpable {
    static int MAX_JUMP_HEIGHT = 0;
    static int MAX_RUN_LENTGH = 50;
    boolean contesting;

    String name;

    Robot(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return "Робот " + name;
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
