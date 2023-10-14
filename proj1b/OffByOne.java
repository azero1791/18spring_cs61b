public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y) {
        int dif1 = x - y;
        int dif2 = y - x;
        return dif1 == 1 || dif2 == 1;
    }

}
