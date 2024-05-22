public class Region {
    int x1;
    int y1;
    int x2;
    int y2;
    boolean enabled;

    public Region(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public boolean contains(int x, int y) {
        if(enabled & x > x1 && x < x2 && y > y1 && y < y2) {
            return true;
        }
        return false;
    }

    public void enabled(boolean enabled) {
        this.enabled = enabled;
    }
}
