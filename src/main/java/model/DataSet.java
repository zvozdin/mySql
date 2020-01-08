package model;

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

    public void put(String columnName, Object object) {
        for (Data elementDataSet : data) {
            if (elementDataSet.getName().equals(columnName)) {
                elementDataSet.value = object;
                return;
            }
        }
        data.add(new Data(columnName, object));
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

    public Object get(String columnName) {
        for (Data elementDataSet : data) {
            if (elementDataSet.getName().equals(columnName)) {
                return elementDataSet.getValue();
            }
        }
        return null;
    }

    public void update(DataSet newValue) {
        List<String> columnNames = newValue.getNames();
        for (String name : columnNames) {
            Object data = newValue.get(name);
            put(name, data);
        }
    }

    @Override
    public String toString() {
        return "" + getNames() + "\n" +
                "" + getValues();
    }
}
