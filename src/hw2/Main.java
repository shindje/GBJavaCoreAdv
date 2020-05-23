package hw2;

public class Main {
    static int ARRAY_LEGNTH = 4;

    static int summ (String[][] arr) throws MySizeArrayException {
        if (arr.length != ARRAY_LEGNTH) {
            throw new MySizeArrayException(arr.length, true);
        }
        for (int i = 0; i < arr.length; i++) {
            String [] a = arr[i];
            if (a.length != ARRAY_LEGNTH)
                throw new MySizeArrayException(a.length, false, i + 1);
        }
        int summ = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                try {
                    summ += Integer.parseInt(arr[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(e, arr[i][j], i + 1, j + 1);
                }
            }
        }
        return summ;
    }

    public static void main(String[] args) {
        String[][] arr3x4 = new String[3][4];
        check(arr3x4);

        String[][] arr4xx = {
            {"5", "4", "5", "1"},
            {"2", "1", "3"},
            {"4", "5", "2", "1"},
            {"2", "1", "2", "4"}
        };
        check(arr4xx);

        String[][] arrBad = {
                {"5", "4", "5", "1"},
                {"2", "1", "3", "s"},
                {"4", "5", "2", "1"},
                {"2", "1", "2", "4"}
        };
        check(arrBad);

        String[][] arrGood = {
                {"5", "4", "5", "1"},
                {"2", "1", "3", "0"},
                {"4", "5", "2", "1"},
                {"2", "1", "2", "4"}
        };
        check(arrGood);
    }

    static void check(String[][] arr) {
        try {
            System.out.println("Сумма элементов массива - " + summ(arr));
        } catch (MySizeArrayException e) {
            System.out.print("Неверный размер массива: ");
            if (e.isRowsSizeWrong()) {
                System.out.println("неверное количество строк - " + e.getWrongSize());
            } else {
                System.out.printf("в строке %d неверное количество столбцов  - %d\n", e.getRowNum(), e.getWrongSize());
            }
        } catch (MyArrayDataException e) {
            System.out.printf("Ошибка преобразования в число значения \"%s\" в строке %d, столбце %d: %s\n", e.getWrongString(),
                    e.getRowNum(), e.getColNum(), e.getCause().getMessage());
        }
    }

}
