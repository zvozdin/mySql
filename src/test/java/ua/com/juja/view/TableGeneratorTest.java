package ua.com.juja.view;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class TableGeneratorTest {

    private TableGenerator tableGenerator;
    private Map<Integer, Integer> columnsNumberAndSize;
    private Map<Integer, Integer> table;
    private List<String> columns;
    private List<List<String>> rows;

    @Before
    public void setUp() {
        // given
        tableGenerator = new TableGenerator();
        table = new LinkedHashMap<>();
        columns = new ArrayList<>(getDataForTable().keySet());

        List<String> row = new ArrayList<>(getDataForTable().values());
        rows = new ArrayList<>();
        rows.add(row);

        columnsNumberAndSize = tableGenerator.getTableWidth(columns, rows);
    }

    @Test
    public void test_ColumnsNameNumber() {
        // when
        tableGenerator.setNamesNumberAndSize(columns, table);

        // then
        assertEquals(columns.size(), table.size());
    }

    @Test
    public void test_ColumnsNameSize() {
        // when
        tableGenerator.setNamesNumberAndSize(columns, table);

        // then
        assertEquals("{0=1, 1=2, 2=4}", table.toString());
    }

    @Test
    public void test_ColumnsValueSize() {
        // given
        tableGenerator.setNamesNumberAndSize(columns, table);

        // when
        tableGenerator.checkAndSetColumnsValueSize(rows, table);

        // then
        assertEquals("{0=3, 1=2, 2=4}", table.toString());
    }

    @Test
    public void test_EvenColumnsData() {
        // given
        tableGenerator.setNamesNumberAndSize(columns, table);
        tableGenerator.checkAndSetColumnsValueSize(rows, table);

        // when
        tableGenerator.evenColumnsData(columns, table);

        // then
        assertEquals("{0=4, 1=2, 2=4}", table.toString());
    }

    @Test
    public void test_GetTableWidth() {
        assertEquals("{0=4, 1=2, 2=4}",
                tableGenerator.getTableWidth(columns, rows).toString());
    }

    @Test
    public void test_CreateRowLine() {
        // given
        StringBuilder line = new StringBuilder();

        // when
        tableGenerator.createRowLine(line, columns, columnsNumberAndSize);

        // then
        assertEquals("+--------+------+--------+", line.toString());
    }

    @Test
    public void test_GetOptimumPaddingSize_NameLessValue() {
        // given
        Integer index = 0;
        String value = columns.get(index);

        // when
        int optimumPaddingSize = tableGenerator
                .getOptimumPaddingSize(index, value.length(), columnsNumberAndSize, TableGenerator.PADDINGS);

        // then
        assertEquals(3, optimumPaddingSize);
    }

    @Test
    public void test_GetOptimumPaddingSize_NameMoreValue() {
        // given
        Integer index = 2;
        String value = columns.get(index);

        // when
        int optimumPaddingSize = tableGenerator
                .getOptimumPaddingSize(index, value.length(), columnsNumberAndSize, TableGenerator.PADDINGS);

        // then
        assertEquals(2, optimumPaddingSize);
    }

    @Test
    public void test_FillColumn_NameLessValue() {
        // given
        StringBuilder line = new StringBuilder();
        Integer index = 0;
        String value = columns.get(index);

        // when
        tableGenerator.fillColumn(line, value, index, columnsNumberAndSize);

        // then
        assertEquals("|   1    |", line.toString());
    }

    @Test
    public void test_FillColumn_NameMoreValue() {
        // given
        StringBuilder line = new StringBuilder();
        Integer index = 2;
        String value = columns.get(index);

        // when
        tableGenerator.fillColumn(line, value, index, columnsNumberAndSize);

        // then
        assertEquals("  1234  |", line.toString());
    }

    @Test
    public void test_PutNameData() {
        // given
        StringBuilder line = new StringBuilder();

        // when
        tableGenerator.putData(line, columns, columnsNumberAndSize);

        // then
        assertEquals("|   1    |  12  |  1234  |", line.toString());
    }

    private static Map<String, String> getDataForTable() {
        Map<String, String> data = new LinkedHashMap<>();
        data.put("1", "123");
        data.put("12", "12");
        data.put("1234", "1");
        return data;
    }
}