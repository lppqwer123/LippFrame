package com.first.frame.model;

public class BaseModel {

    /**
     * 0|非0，integer
     */
    public String status;
    /**
     * 状态码说明：status!=0时，才有此字段返回，状态码说明参考错误码表
     */
    public String error;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "status='" + status + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
