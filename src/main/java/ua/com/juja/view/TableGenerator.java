package ua.com.juja.view;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TableGenerator {

    private int PADDING_SIZE = 2;
    private String NEW_LINE = "\n";
    private String TABLE_JOINT_SYMBOL = "+";
    private String TABLE_V_SPLIT_SYMBOL = "|";
    private String TABLE_H_SPLIT_SYMBOL = "-";

    public String generateTable(List<String> columns, List<List<String>> rows, int... overRiddenHeaderHeight) {
        StringBuilder stringBuilder = new StringBuilder();
        int rowHeight = overRiddenHeaderHeight.length > 0 ? overRiddenHeaderHeight[0] : 1;

        Map<Integer, Integer> columnsNumberAndSize = getTableWidth(columns, rows);

        createRowLine(stringBuilder, columns.size(), columnsNumberAndSize);
        stringBuilder.append(NEW_LINE);

        for (int headerIndex = 0; headerIndex < columns.size(); headerIndex++) {
            fillCell(stringBuilder, columns.get(headerIndex), headerIndex, columnsNumberAndSize);
        }

        stringBuilder.append(NEW_LINE);
        createRowLine(stringBuilder, columns.size(), columnsNumberAndSize);

        for (List<String> row : rows) {
            for (int i = 0; i < rowHeight; i++) {
                stringBuilder.append(NEW_LINE);
            }

            for (int cellIndex = 0; cellIndex < row.size(); cellIndex++) {
                fillCell(stringBuilder, row.get(cellIndex), cellIndex, columnsNumberAndSize);
            }
        }

        stringBuilder.append(NEW_LINE);
        createRowLine(stringBuilder, columns.size(), columnsNumberAndSize);

        return stringBuilder.toString();
    }

    private void fillSpace(StringBuilder stringBuilder, int length) {
        for (int i = 0; i < length; i++) {
            stringBuilder.append(" ");
        }
    }

    private void createRowLine(StringBuilder stringBuilder, int headersListSize, Map<Integer, Integer> columnMaxWidthMapping) {
        for (int i = 0; i < headersListSize; i++) {
            if (i == 0) {
                stringBuilder.append(TABLE_JOINT_SYMBOL);
            }

            for (int j = 0; j < columnMaxWidthMapping.get(i) + PADDING_SIZE * 2; j++) {
                stringBuilder.append(TABLE_H_SPLIT_SYMBOL);
            }

            stringBuilder.append(TABLE_JOINT_SYMBOL);
        }
    }

    private Map<Integer, Integer> getTableWidth(List<String> columns, List<List<String>> rows) {
        Map<Integer, Integer> columnsNumberAndSize = new LinkedHashMap<>();

        setNamesNumberAndSize(columns, columnsNumberAndSize);

        checkAndSetColumnsValueSize(rows, columnsNumberAndSize);

        setColumnsDataToCenter(columns, columnsNumberAndSize);

        return columnsNumberAndSize;
    }

    public void setNamesNumberAndSize(List<String> columns, Map<Integer, Integer> columnsNumberAndWidth) {
        for (int index = 0; index < columns.size(); index++) {
            columnsNumberAndWidth.put(index, columns.get(index).length());
        }
    }

    private void checkAndSetColumnsValueSize(List<List<String>> rows, Map<Integer, Integer> columnsNumberAndWidth) {
        for (List<String> row : rows) {
            for (int index = 0; index < row.size(); index++) {
                if (row.get(index).length() > columnsNumberAndWidth.get(index)) {
                    columnsNumberAndWidth.put(index, row.get(index).length());
                }
            }
        }
    }

    private void setColumnsDataToCenter(List<String> columns, Map<Integer, Integer> columnsNumberAndWidth) {
        for (int index = 0; index < columns.size(); index++) {
            if (columnsNumberAndWidth.get(index) % 2 != 0) {
                columnsNumberAndWidth.put(index, columnsNumberAndWidth.get(index) + 1);
            }
        }
    }

    private int getOptimumCellPadding(int cellIndex, int datalength, Map<Integer, Integer> columnMaxWidthMapping, int cellPaddingSize) {
        if (datalength % 2 != 0) {
            datalength++;
        }

        if (datalength < columnMaxWidthMapping.get(cellIndex)) {
            cellPaddingSize = cellPaddingSize + (columnMaxWidthMapping.get(cellIndex) - datalength) / 2;
        }

        return cellPaddingSize;
    }

    private void fillCell(StringBuilder stringBuilder, String cell, int cellIndex, Map<Integer, Integer> columnMaxWidthMapping) {
        int cellPaddingSize = getOptimumCellPadding(cellIndex, cell.length(), columnMaxWidthMapping, PADDING_SIZE);
        if (cellIndex == 0) {
            stringBuilder.append(TABLE_V_SPLIT_SYMBOL);
        }

        fillSpace(stringBuilder, cellPaddingSize);
        stringBuilder.append(cell);
        if (cell.length() % 2 != 0) {
            stringBuilder.append(" ");
        }

        fillSpace(stringBuilder, cellPaddingSize);
        stringBuilder.append(TABLE_V_SPLIT_SYMBOL);
    }
}