package hw2;

public class MySizeArrayException extends IllegalArgumentException{
    private int wrongSize;
    private int rowNum;
    private boolean isRowsSizeWrong;

    MySizeArrayException(int wrongSize, boolean isRowsSizeWrong) {
        this.wrongSize = wrongSize;
        this.isRowsSizeWrong = isRowsSizeWrong;
    }

    MySizeArrayException(int wrongSize, boolean isRowsSizeWrong, int rowNum) {
        this(wrongSize, isRowsSizeWrong);
        this.rowNum = rowNum;
    }

    public int getWrongSize() {
        return wrongSize;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public boolean isRowsSizeWrong() {
        return isRowsSizeWrong;
    }
}
