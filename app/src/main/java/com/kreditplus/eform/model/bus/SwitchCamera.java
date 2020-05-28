package com.kreditplus.eform.model.bus;

public class SwitchCamera {
    public boolean isFront;
    public boolean isOther;

    public boolean isOther() {
        return isOther;
    }

    public void setOther(boolean other) {
        isOther = other;
    }

    public SwitchCamera(boolean isFront, boolean isOther) {
        this.isFront = isFront;
        this.isOther = isOther();
    }
    public boolean isFront() {
        return isFront;
    }

    public void setFront(boolean front) {
        isFront = front;
    }
}
