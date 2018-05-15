class WrongUnsafeRead {
    void m(int t, int bt) {
        bt = -1;
        if (t >= 0) {
                bt = t;
        }
        byte b = (byte) bt;
    }
}
