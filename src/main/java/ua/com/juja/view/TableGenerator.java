package ua.com.juja.view;

import java.util.*;

public class TableGenerator {

    static final int PADDINGS = 2;
    private static final String NEW_LINE = "\n";
    private static final String PLUS = "+";
    private static final String SPLIT = "|";
    private static final String MINUS = "-";

    public String generateTable(Set<String> columnsSet, List<List<String>> rows) {
        List<String> columns = new ArrayList<>(columnsSet);
        StringBuilder table = new StringBuilder();

        Map<Integer, Integer> columnsNumberAndSize = getTableWidth(columns, rows);

        appendLine(table, columns, columnsNumberAndSize);
        table.append(NEW_LINE);

        putData(table, columns, columnsNumberAndSize);
        table.append(NEW_LINE);

        appendLine(table, columns, columnsNumberAndSize);
        table.append(NEW_LINE);

        for (List<String> row : rows) {
            putData(table, row, columnsNumberAndSize);
            table.append(NEW_LINE);
        }

        appendLine(table, columns, columnsNumberAndSize);

        return table.toString();
    }

    Map<Integer, Integer> getTableWidth(List<String> columns, List<List<String>> rows) {
        Map<Integer, Integer> columnsNumberAndSize = new LinkedHashMap<>();

        setNamesNumberAndSize(columns, columnsNumberAndSize);

        checkAndSetColumnsValueSize(rows, columnsNumberAndSize);

        evenColumnsData(columns, columnsNumberAndSize);

        return columnsNumberAndSize;
    }

    void setNamesNumberAndSize(List<String> columns, Map<Integer, Integer> columnsNumberAndSize) {
        for (int index = 0; index < columns.size(); index++) {
            columnsNumberAndSize.put(index, columns.get(index).length());
        }
    }

    void checkAndSetColumnsValueSize(List<List<String>> rows, Map<Integer, Integer> columnsNumberAndSize) {
        for (List<String> row : rows) {
            for (int index = 0; index < row.size(); index++) {
                if (row.get(index).length() > columnsNumberAndSize.get(index)) {
                    columnsNumberAndSize.put(index, row.get(index).length());
                }
            }
        }
    }

    void evenColumnsData(List<String> columns, Map<Integer, Integer> columnsNumberAndSize) {
        for (int index = 0; index < columns.size(); index++) {
            if (columnsNumberAndSize.get(index) % 2 != 0) {
                columnsNumberAndSize.put(index, columnsNumberAndSize.get(index) + 1);
            }
        }
    }

    void appendLine(StringBuilder table, List<String> columns, Map<Integer, Integer> columnsNumberAndSize) {
        for (int index = 0; index < columns.size(); index++) {
            if (index == 0) {
                table.append(PLUS);
            }

            Integer oneColumnWidth = columnsNumberAndSize.get(index);
            for (int j = 0; j < oneColumnWidth + PADDINGS * 2; j++) {
                table.append(MINUS);
            }

            table.append(PLUS);
        }
    }

    void putData(StringBuilder table, List<String> data, Map<Integer, Integer> columnsNumberAndSize) {
        for (int index = 0; index < data.size(); index++) {
            String value = data.get(index);
            fillColumn(table, value, index, columnsNumberAndSize);
        }
    }

    void fillColumn(StringBuilder table, String value, int index, Map<Integer, Integer> columnsNumberAndSize) {
        int paddingSize = getOptimumPaddingSize(index, value.length(), columnsNumberAndSize, PADDINGS);
        if (index == 0) {
            table.append(SPLIT);
        }

        fillSpace(table, paddingSize);
        table.append(value);
        if (value.length() % 2 != 0) {
            table.append(" ");
        }

        fillSpace(table, paddingSize);
        table.append(SPLIT);
    }

    int getOptimumPaddingSize(int index, int valueLength, Map<Integer, Integer> columnsNumberAndSize, int paddings) {
        if (valueLength % 2 != 0) {
            valueLength++;
        }

        if (valueLength < columnsNumberAndSize.get(index)) {
            paddings = paddings + (columnsNumberAndSize.get(index) - valueLength) / 2;
        }

        return paddings;
    }

    private void fillSpace(StringBuilder table, int length) {
        for (int i = 0; i < length; i++) {
            table.append(" ");
        }
    }
}