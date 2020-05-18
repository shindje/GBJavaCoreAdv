package hw1.dayofweek;

public enum DayOfWeek {
    MONDAY(8.5), TUESDAY(8.5), WEDNESDAY(8.5), THURSDAY(8.5),
    FRIDAY(6), SATURSDAY(0), SUNDAY(0);

    double workingHours;

    DayOfWeek(double workingHours) {
        this.workingHours = workingHours;
    }

    double getWorkingHours() {
        return workingHours;
    }
}
