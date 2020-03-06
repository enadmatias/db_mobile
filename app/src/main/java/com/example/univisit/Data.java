package com.example.univisit;

public class Data {
      private String no;
      private String purpose;
      private String dateTime;
      private String status;

    public Data(String no, String purpose, String dateTime, String status) {
        this.no = no;
        this.purpose = purpose;
        this.dateTime = dateTime;
        this.status = status;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
