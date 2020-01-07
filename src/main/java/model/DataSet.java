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

    @Override
    public String toString() {
        return "columnNames | " + getNames() + "\n" +
                "values | " + getValues() + "\n";
    }
}
