package ua.com.juja.view;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TableGeneratorTest {

    private TableGenerator tableGenerator = new TableGenerator();
    private Map<Integer, Integer> table = new LinkedHashMap<>();

    @Test
    public void test_ColumnsNameNumber() {
        // when
        tableGenerator.setNamesNumberAndSize(
                new LinkedList<>(getDataForTable().keySet()), table);

        // then
        assertEquals(3, table.size());
    }

    @Test
    public void test_ColumnsNameSize() {
        // when
        tableGenerator.setNamesNumberAndSize(
                new LinkedList<>(getDataForTable().keySet()), table);

        // then
        assertEquals("{0=1, 1=2, 2=3}", table.toString());
    }

    private Map<String, String> getDataForTable() {
        Map<String, String> input = new LinkedHashMap<>();
        input.put("1", "12");
        input.put("12", "12");
        input.put("123", "12");
        return input;
    }
}