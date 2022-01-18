import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Kripto {
    private final String symbols = "АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЪъЫыЬьЭэЮюЯя.,\":;-!? ";
    private final int keyCount = symbols.length();

    private FileWriter sourceFile;
    private int key;
    private FileReader resultFile;

    private Kripto() {
    }

    public Kripto(File sourcePath) throws IOException {
            this.sourceFile = new FileWriter(sourcePath);
    }

    public Kripto(FileWriter sourcePath) {
        this.sourceFile = sourcePath;
    }

    public Kripto(FileWriter sourceFile, int key) {
        this.sourceFile = sourceFile;
        this.key = key;
    }

    public FileWriter getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(FileWriter sourceFile) {
        this.sourceFile = sourceFile;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    //public Fil

}
