package com.hentica.app.widget.view.drawer;

/**
 * 坐标点
 * Created by mili on 2016/8/2.
 */
public class DrawPoint {

    public float x;
    public float y;

    public DrawPoint() {
    }

    public DrawPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public DrawPoint(DrawPoint src) {
        this.x = src.x;
        this.y = src.y;
    }

    /**
     * Set the pofloat's x and y coordinates
     */
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Set the pofloat's x and y coordinates
     */
    public void set(DrawPoint src) {
        this.x = src.x;
        this.y = src.y;
    }

    /**
     * Negate the pofloat's coordinates
     */
    public final void negate() {
        x = -x;
        y = -y;
    }

    /**
     * Offset the pofloat's coordinates by dx, dy
     */
    public final void offset(float dx, float dy) {
        x += dx;
        y += dy;
    }

    /**
     * Returns true if the pofloat's coordinates equal (x,y)
     */
    public final boolean equals(float x, float y) {
        return this.x == x && this.y == y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DrawPoint pofloat = (DrawPoint) o;

        if (x != pofloat.x) return false;
        if (y != pofloat.y) return false;

        return true;
    }

    @Override
    public int hashCode() {

        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DrawPoint(" + x + ", " + y + ")";
    }
}
