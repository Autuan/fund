package com.tb.fund.entity;

import java.io.Serializable;

public class Money implements Serializable {
    private Integer mid;

    private String name;

    private String date;

    private Integer sum;

    private Integer surplus;

    private static final long serialVersionUID = 1L;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date == null ? null : date.trim();
    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getSurplus() {
        return surplus;
    }

    public void setSurplus(Integer surplus) {
        this.surplus = surplus;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", mid=").append(mid);
        sb.append(", name=").append(name);
        sb.append(", date=").append(date);
        sb.append(", sum=").append(sum);
        sb.append(", surplus=").append(surplus);
        sb.append("]");
        return sb.toString();
    }

    public Money(Integer mid) {
        this.mid = mid;
    }

    public Money() {
    }
}