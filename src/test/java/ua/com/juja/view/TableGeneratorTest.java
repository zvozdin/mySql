package ua.com.juja.view;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class TableGeneratorTest {

    private TableGenerator tableGenerator;
    private Map<Integer, Integer> table;
    private Set<String> columns;
    private List<List<String>> rows;

    @Before
    public void setUp() {
        // given
        tableGenerator = new TableGenerator();
        table = new LinkedHashMap<>();
        columns = getDataForTable().keySet();

        List<String> row = new ArrayList<>(getDataForTable().values());
        rows = new ArrayList<>();
        rows.add(row);
    }

    @Test
    public void test_ColumnsNameNumber() {
        // when
        tableGenerator.setNamesNumberAndSize(new LinkedList<>(columns), table);

        // then
        assertEquals(columns.size(), table.size());
    }

    @Test
    public void test_ColumnsNameSize() {
        // when
        tableGenerator.setNamesNumberAndSize(new LinkedList<>(columns), table);

        // then
        assertEquals("{0=1, 1=2, 2=3}", table.toString());
    }

    @Test
    public void test_ColumnsValueSize() {
        // given
        tableGenerator.setNamesNumberAndSize(new LinkedList<>(columns), table);

        // when
        tableGenerator.checkAndSetColumnsValueSize(rows, table);

        // then
        assertEquals("{0=2, 1=2, 2=3}", table.toString());
    }

    @Test
    public void test_EvenColumnsData() {
        // given
        tableGenerator.setNamesNumberAndSize(new LinkedList<>(columns), table);
        tableGenerator.checkAndSetColumnsValueSize(rows, table);

        // when
        tableGenerator.evenColumnsData(new LinkedList<>(columns), table);

        // then
        assertEquals("{0=2, 1=2, 2=4}", table.toString());
    }

    @Test
    public void test_GetTableWidth() {
        assertEquals("{0=2, 1=2, 2=4}",
                tableGenerator.getTableWidth(new LinkedList<>(columns), rows).toString());
    }

    private static Map<String, String> getDataForTable() {
        Map<String, String> data = new LinkedHashMap<>();
        data.put("1", "12");
        data.put("12", "12");
        data.put("123", "12");
        return data;
    }
}