package org.example;

import java.util.ArrayList;
import java.util.List;

public class AddArray {
    private List<Integer> numbers = new ArrayList<>();
    private Integer result = 0;

    public void pushNumberToArray(Integer num) {
        numbers.add(num);
    }

    public void computeResult() {
        result = 0;
        for (Integer number : numbers) {
            result += number;
        }
    }

    public Integer getResult() {
        return result;
    }
}
