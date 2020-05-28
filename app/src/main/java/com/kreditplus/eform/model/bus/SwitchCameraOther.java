package com.kreditplus.eform.model.bus;

public class SwitchCameraOther {
    public boolean isFront;


    public SwitchCameraOther(boolean isFront) {
        this.isFront = isFront;
    }
    public boolean isFront() {
        return isFront;
    }

    public void setFront(boolean front) {
        isFront = front;
    }
}
