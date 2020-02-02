package ua.com.juja.view;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TableGenerator {

    static final int PADDINGS = 2;
    private static final String NEW_LINE = "\n";
    private static final String TABLE_JOINT_SYMBOL = "+";
    private static final String TABLE_SPLIT_SYMBOL = "|";
    private static final String TABLE_MINUS_SYMBOL = "-";

    public String generateTable(List<String> columns, List<List<String>> rows) {
        StringBuilder stringBuilder = new StringBuilder();

        Map<Integer, Integer> columnsNumberAndSize = getTableWidth(columns, rows);

        createRowLine(stringBuilder, columns, columnsNumberAndSize);
        stringBuilder.append(NEW_LINE);

        putData(stringBuilder, columns, columnsNumberAndSize);
        stringBuilder.append(NEW_LINE);

        createRowLine(stringBuilder, columns, columnsNumberAndSize);
        stringBuilder.append(NEW_LINE);

        for (List<String> row : rows) { // TODO test putDATA for value + 2 rows + whole table
            putData(stringBuilder, row, columnsNumberAndSize);
            stringBuilder.append(NEW_LINE);
        }

        createRowLine(stringBuilder, columns, columnsNumberAndSize);

        return stringBuilder.toString();
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

    void createRowLine(StringBuilder stringBuilder, List<String> columns, Map<Integer, Integer> columnsNumberAndSize) {
        for (int index = 0; index < columns.size(); index++) {
            if (index == 0) {
                stringBuilder.append(TABLE_JOINT_SYMBOL);
            }

            Integer oneColumnWidth = columnsNumberAndSize.get(index);
            for (int j = 0; j < oneColumnWidth + PADDINGS * 2; j++) {
                stringBuilder.append(TABLE_MINUS_SYMBOL);
            }

            stringBuilder.append(TABLE_JOINT_SYMBOL);
        }
    }

    void putData(StringBuilder stringBuilder, List<String> data, Map<Integer, Integer> columnsNumberAndSize) {
        for (int index = 0; index < data.size(); index++) {
            String value = data.get(index);
            fillColumn(stringBuilder, value, index, columnsNumberAndSize);
        }
    }

    void fillColumn(
            StringBuilder stringBuilder, String value, int index, Map<Integer, Integer> columnsNumberAndSize)
    {
        int paddingSize = getOptimumPaddingSize(index, value.length(), columnsNumberAndSize, PADDINGS);
        if (index == 0) {
            stringBuilder.append(TABLE_SPLIT_SYMBOL);
        }

        fillSpace(stringBuilder, paddingSize);
        stringBuilder.append(value);
        if (value.length() % 2 != 0) {
            stringBuilder.append(" ");
        }

        fillSpace(stringBuilder, paddingSize);
        stringBuilder.append(TABLE_SPLIT_SYMBOL);
    }

    int getOptimumPaddingSize(
            int index, int valueLength, Map<Integer, Integer> columnsNumberAndSize, int paddings)
    {
        if (valueLength % 2 != 0) {
            valueLength++;
        }

        if (valueLength < columnsNumberAndSize.get(index)) {
            paddings = paddings + (columnsNumberAndSize.get(index) - valueLength) / 2;
        }

        return paddings;
    }

    private void fillSpace(StringBuilder stringBuilder, int length) {
        for (int i = 0; i < length; i++) {
            stringBuilder.append(" ");
        }
    }
}