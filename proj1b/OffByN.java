public class OffByN implements CharacterComparator {
    private int N;

    public OffByN(int n) {
        N = n;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int dif1 = x - y;
        int dif2 = y - x;
        return dif1 == N || dif2 == N;
    }

}
