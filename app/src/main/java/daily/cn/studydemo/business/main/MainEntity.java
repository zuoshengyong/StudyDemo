package daily.cn.studydemo.business.main;

import java.io.Serializable;

/**
 * <pre>
 * author :zuoshengyong
 * e-mail: partric23@gmail.com
 * time: 2018/03/06
 * </pre>
 */
public class MainEntity implements Serializable {

    private static final long serialVersionUID = 2003773210509408421L;
    public long id;
    /**
     */
    public int type;
    /**
     */
    public String title;
    /**
     */
    public boolean deleted;
    /**
     */
    public long endTime;
    /**
     */
    public long amont;
    /**
     */
    public int status;
    /**
     */
    public String miliao;
    /**
     */
    public String endTimeStr;

    @Override
    public String toString() {
        return "MainEntity{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", deleted=" + deleted +
                ", endTime=" + endTime +
                ", amont=" + amont +
                ", status=" + status +
                ", miliao='" + miliao + '\'' +
                '}';
    }
}
