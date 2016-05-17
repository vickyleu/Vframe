package com.vickyleu.library.Base.Controller;


public class  BaseViewType {
    int min;
    int max;

    public BaseViewType(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public String toString() {
        return "BaseViewType{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
