package ua.com.juja.view;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class TableGeneratorTest {

    private TableGenerator tableGenerator;
    private Map<Integer, Integer> columnsNumberAndSize;
    private List<String> columns;
    private List<List<String>> rows;

    @Before
    public void setUp() {
        // given
        tableGenerator = new TableGenerator();
        columns = new ArrayList<>(getDataForTable().keySet());

        List<String> row = new ArrayList<>(getDataForTable().values());
        rows = new ArrayList<>();
        rows.add(row);

        columnsNumberAndSize = tableGenerator.getTableWidth(columns, rows);
    }

    @Test
    public void test_ColumnsNameNumber() {
        // when
        tableGenerator.setColumnsNameNumberAndSize(columns);

        // then
        assertEquals(columns.size(), columnsNumberAndSize.size());
    }

    @Test
    public void test_ColumnsNameSize() {
        // when
        tableGenerator.setColumnsNameNumberAndSize(columns);

        // then
        assertEquals("{0=1, 1=2, 2=4}", columnsNumberAndSize.toString());
    }

    @Test
    public void test_ColumnsValueSize() {
        // given
        tableGenerator.setColumnsNameNumberAndSize(columns);

        // when
        tableGenerator.checkAndSetColumnsValueSize(rows);

        // then
        assertEquals("{0=3, 1=2, 2=4}", columnsNumberAndSize.toString());
    }

    @Test
    public void test_EvenColumnsData() {
        // given
        tableGenerator.setColumnsNameNumberAndSize(columns);
        tableGenerator.checkAndSetColumnsValueSize(rows);

        // when
        tableGenerator.evenColumnsData(columns);

        // then
        assertEquals("{0=4, 1=2, 2=4}", columnsNumberAndSize.toString());
    }

    @Test
    public void test_GetTableWidth() {
        assertEquals("{0=4, 1=2, 2=4}", columnsNumberAndSize.toString());
    }

    @Test
    public void test_DrawLine() {
        // when
        tableGenerator.drawLine(columns);

        // then
        assertEquals("+--------+------+--------+", tableGenerator.getLine());
    }

    @Test
    public void test_GetOptimumPaddingSize_NameLessValue() {
        // given
        Integer index = 0;
        String value = columns.get(index);

        // when
        int optimumPaddingSize = tableGenerator.getOptimumPaddingSize(index, value.length());

        // then
        assertEquals(3, optimumPaddingSize);
    }

    @Test
    public void test_GetOptimumPaddingSize_NameMoreValue() {
        // given
        Integer index = 2;
        String value = columns.get(index);

        // when
        int optimumPaddingSize = tableGenerator.getOptimumPaddingSize(index, value.length());

        // then
        assertEquals(2, optimumPaddingSize);
    }

    @Test
    public void test_FillColumn_NameLessValue() {
        // given
        Integer index = 0;
        String value = columns.get(index);

        // when
        tableGenerator.fillColumn(index, value);

        // then
        assertEquals("|   1    |", tableGenerator.getLine());
    }

    @Test
    public void test_FillColumn_NameMoreValue() {
        // given
        Integer index = 2;
        String value = columns.get(index);

        // when
        tableGenerator.fillColumn(index, value);

        // then
        assertEquals("  1234  |", tableGenerator.getLine());
    }

    @Test
    public void test_PutNameData() {
        // when
        tableGenerator.putData(columns);

        // then
        assertEquals("|   1    |  12  |  1234  |", tableGenerator.getLine());
    }

    @Test
    public void test_PutValueData_1row() {
        // when
        for (List<String> row : rows) {
            tableGenerator.putData(row);
        }

        // then
        assertEquals("|  123   |  12  |   1    |", tableGenerator.getLine());
    }

    @Test
    public void test_PutValueData_2rows() {
        // given
        List<String> row2 = new ArrayList<>(getDataForTable().values());
        rows.add(row2);

        // when
        for (List<String> row : rows) {
            tableGenerator.putData(row);
        }

        // then
        assertEquals("" +
                "|  123   |  12  |   1    |" +
                "|  123   |  12  |   1    |", tableGenerator.getLine());
    }

    @Test
    public void test_GenerateTable() {
        assertEquals("" +
                "+--------+------+--------+\n" +
                "|   1    |  12  |  1234  |\n" +
                "+--------+------+--------+\n" +
                "|  123   |  12  |   1    |\n" +
                "+--------+------+--------+", tableGenerator.generateTable(getDataForTable().keySet(), rows));
    }

    private Map<String, String> getDataForTable() {
        Map<String, String> data = new LinkedHashMap<>();
        data.put("1", "123");
        data.put("12", "12");
        data.put("1234", "1");
        return data;
    }
}