package ua.com.juja.model;

import java.util.LinkedList;
import java.util.List;

public class DataSet {

    List<Data> data = new LinkedList<>();

    private class Data {

        private String name;
        private Object value;

        public Data(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public Object getValue() {
            return value;
        }
    }

    public void put(String column, Object value) {
        for (Data elementDataSet : data) {
            if (elementDataSet.getName().equals(column)) {
                elementDataSet.value = value;
                return;
            }
        }
        data.add(new Data(column, value));
    }

    public List<String> getNames() {
        List<String> result = new LinkedList();
        for (Data elementDataSet : data) {
            result.add(elementDataSet.getName());
        }
        return result;
    }

    public List<Object> getValues() {
        List<Object> result = new LinkedList();
        for (Data elementDataSet : data) {
            result.add(elementDataSet.getValue());
        }
        return result;
    }

    public Object get(String column) {
        for (Data elementDataSet : data) {
            if (elementDataSet.getName().equals(column)) {
                return elementDataSet.getValue();
            }
        }
        return null;
    }

    public void update(DataSet newValue) {
        List<String> columns = newValue.getNames();
        for (String column : columns) {
            Object data = newValue.get(column);
            put(column, data);
        }
    }

    @Override
    public String toString() {
        return "columns:" + getNames() + ", " +
                "values:" + getValues();
    }
}