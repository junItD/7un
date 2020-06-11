package top.i7un.springboot.mycatch;

/**
 * Created by Edianzu on 2017/6/28.
 */
public class PageParam {
    private int page = 1;
    private int pageSize = 10;
    private int offset;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getOffset() {
        return offset;
    }

    public void calOffset() {
        this.offset = (page - 1) * pageSize;
    }

}
