package hw2;

public class MyArrayDataException extends IllegalArgumentException{
    private String wrongString;
    private int rowNum;
    private int colNum;

    MyArrayDataException(Throwable cause, String wrongString, int rowNum, int colNum) {
        super(cause);
        this.wrongString = wrongString;
        this.rowNum = rowNum;
        this.colNum = colNum;
    }

    public String getWrongString() {
        return wrongString;
    }

    public int getRowNum() {
        return rowNum;
    }

    public int getColNum() {
        return colNum;
    }
}
