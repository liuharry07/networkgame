import java.io.File;

public class RenameFiles {
    public static void main(String[] args) {
        String[] suit = {"clubs", "diamonds", "hearts", "spades"};
        String[] value = {"02", "03", "04", "05", "06", "07", "08", "09", "10", "J", "Q", "K", "A"};

        for(int i = 0; i < 4; ++i) {
            for(int j = 0; j < 13; ++j) {
                File file = new File("card_" + suit[i] + "_" + value[j]);
            }
        }
    }
}
