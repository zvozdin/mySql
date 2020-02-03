package ua.com.juja.view;

import java.util.*;

public class TableGenerator {

    static final int PADDINGS = 2;
    private static final String NEW_LINE = "\n";
    private static final String PLUS = "+";
    private static final String SPLIT = "|";
    private static final String MINUS = "-";

    private StringBuilder line = new StringBuilder();
    private Map<Integer, Integer> columnsNumberAndSize;

    public String generateTable(Set<String> columnsName, List<List<String>> rows) {
        List<String> columns = new ArrayList<>(columnsName);

        columnsNumberAndSize = getTableWidth(columns, rows);

        drawLine(columns);
        line.append(NEW_LINE);

        putData(columns);
        line.append(NEW_LINE);

        drawLine(columns);
        line.append(NEW_LINE);

        for (List<String> row : rows) {
            putData(row);
            line.append(NEW_LINE);
        }

        drawLine(columns);

        return line.toString();
    }

    Map<Integer, Integer> getTableWidth(List<String> columns, List<List<String>> rows) {
        columnsNumberAndSize = new LinkedHashMap<>();

        setColumnsNameNumberAndSize(columns);

        checkAndSetColumnsValueSize(rows);

        evenColumnsData(columns);

        return columnsNumberAndSize;
    }

    void setColumnsNameNumberAndSize(List<String> columns) {
        for (int index = 0; index < columns.size(); index++) {
            columnsNumberAndSize.put(index, columns.get(index).length());
        }
    }

    void checkAndSetColumnsValueSize(List<List<String>> rows) {
        for (List<String> row : rows) {
            for (int index = 0; index < row.size(); index++) {
                if (row.get(index).length() > columnsNumberAndSize.get(index)) {
                    columnsNumberAndSize.put(index, row.get(index).length());
                }
            }
        }
    }

    void evenColumnsData(List<String> columns) {
        for (int index = 0; index < columns.size(); index++) {
            if (columnsNumberAndSize.get(index) % 2 != 0) {
                columnsNumberAndSize.put(index, columnsNumberAndSize.get(index) + 1);
            }
        }
    }

    void drawLine(List<String> columns) {
        for (int index = 0; index < columns.size(); index++) {
            if (index == 0) {
                line.append(PLUS);
            }

            Integer oneColumnWidth = columnsNumberAndSize.get(index);
            for (int j = 0; j < oneColumnWidth + PADDINGS * 2; j++) {
                line.append(MINUS);
            }

            line.append(PLUS);
        }
    }

    void putData(List<String> data) {
        for (int index = 0; index < data.size(); index++) {
            String value = data.get(index);
            fillColumn(index, value);
        }
    }

    void fillColumn(int index, String value) {
        int paddingSize = getOptimumPaddingSize(index, value.length());
        if (index == 0) {
            line.append(SPLIT);
        }

        fillSpace(paddingSize);
        line.append(value);
        if (value.length() % 2 != 0) {
            line.append(" ");
        }

        fillSpace(paddingSize);
        line.append(SPLIT);
    }

    int getOptimumPaddingSize(int index, int valueLength) {
        if (valueLength % 2 != 0) {
            valueLength++;
        }

        if (valueLength < columnsNumberAndSize.get(index)) {
            return PADDINGS + (columnsNumberAndSize.get(index) - valueLength) / 2;
        }

        return PADDINGS;
    }

    private void fillSpace(int length) {
        for (int i = 0; i < length; i++) {
            line.append(" ");
        }
    }

    public String getLine() {
        return line.toString();
    }
}