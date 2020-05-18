package hw1.dayofweek;

public class Main {
    static String getWorkingHours(DayOfWeek dayOfWeek) {
        if (dayOfWeek == DayOfWeek.SATURSDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return "Сегодня выходной";
        } else {
            boolean find = false;
            double workingHours = 0;
            for (DayOfWeek d : DayOfWeek.values()) {
                if (dayOfWeek == d) {
                    find = true;
                }
                if (find) {
                    workingHours += d.getWorkingHours();
                }
            }
            return String.format("До конца недели осталось рабочих %.1f ч.", workingHours);
        }
    }

    public static void main(String[] args) {
        for (DayOfWeek d: DayOfWeek.values()) {
            System.out.println(getWorkingHours(d));
        }
    }
}
