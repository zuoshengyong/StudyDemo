package daily.cn.commonlib.http.result;

import java.util.List;

/**
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/05
 */
public class HttpListResult<T> {
    public int pageNo;
    public int pageSize;
    public int totalCount;
    public int totalPages;
    public String ascOrder;
    public String descOrder;
    public List<T> result;

    @Override
    public String toString() {
        return "HttpListResult{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                ", totalPages=" + totalPages +
                ", ascOrder='" + ascOrder + '\'' +
                ", descOrder='" + descOrder + '\'' +
                ", result=" + result +
                '}';
    }
}
