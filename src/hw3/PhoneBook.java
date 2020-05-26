package hw3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PhoneBook {
    private Map<String, HashSet<String>> map = new HashMap<>();

    public void add(String lastName, String phoneNumber) throws IllegalArgumentException {
        if (lastName == null || phoneNumber == null)
            throw new IllegalArgumentException("Фамилия или номер телефона не указаны");
        lastName = lastName.trim();
        HashSet<String> numbers = map.getOrDefault(lastName, new HashSet<>());
        numbers.add(phoneNumber.trim());
        map.put(lastName, numbers);
    }

    public Set<String> get(String lastName) throws IllegalArgumentException {
        if (lastName == null)
            throw new IllegalArgumentException("Фамилия не указана");
        lastName = lastName.trim();
        return map.getOrDefault(lastName, new HashSet<>());
    }

    public String toString() {
        return map.entrySet().toString();
    }
}
