package swimmingpool.co.uk.jesmondswimmingpool.entity.custom;

import java.util.Date;

/**
 * Created by cody on 2017/12/1.
 */

public class ErrorBean {
    private String errorInfo;
    private Date errorDate;

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public Date getErrorDate() {
        return errorDate;
    }

    public void setErrorDate(Date errorDate) {
        this.errorDate = errorDate;
    }
}
