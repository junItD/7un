package top.i7un.springboot.mycatch;

import java.io.Serializable;
import java.util.List;

public class PageDataWrapper<D> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int count;
    private List<D> data;

    public PageDataWrapper() {
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<D> getData() {
        return data;
    }

    public void setData(List<D> data) {
        this.data = data;
    }
}