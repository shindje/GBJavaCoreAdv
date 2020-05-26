package hw3;

public class Main2 {
    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add("Иванов", "333-111-222");
        phoneBook.add("Петров", "44-11-233");
        phoneBook.add("Петров", "44-11-233");
        phoneBook.add("Петров", "33-22-666");

        System.out.println("Телефон Иванова: " + phoneBook.get("Иванов"));

        System.out.println("Весь справочник: " + phoneBook);
    }
}
