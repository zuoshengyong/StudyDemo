package daily.cn.commonlib.http.result;

import static android.R.id.message;

/**
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/05
 */
public class HttpResult<T> {
    /**
     * 返回状态值
     * 200表示成功
     */
    private String code;
    /**
     * 返回结果提示
     */
    private String msg;
    /**
     * 返回结果数据
     */
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
