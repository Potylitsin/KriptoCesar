import javax.swing.*;
import java.io.*;
import java.util.*;

public class Kripto {
    private static final String symbols = "АаБбВвГгДдЕеЁёЖжЗзИиЙйКкЛлМмНнОоПпРрСсТтУуФфХхЦцЧчШшЩщЪъЫыЬьЭэЮюЯя.,\":;-!? ";
    private final int keyCount = symbols.length();

    private FileReader sourceFile;
    private BufferedReader sourceBuff;
    private int key;
    private FileWriter resultFile;
    private File analysFile;
    private StringBuilder resulStr;

    private Kripto() {
    }

    public Kripto(File sourcePath) throws IOException {
        this.sourceFile = new FileReader(sourcePath);
        sourceBuff = new BufferedReader(this.sourceFile);
    }

    public Kripto(File sourceFile, int key) throws IOException {
        this.sourceFile = new FileReader(sourceFile);
        sourceBuff = new BufferedReader(this.sourceFile);
        this.key = key;
    }

    public FileReader getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(FileReader sourceFile) {
        this.sourceFile = sourceFile;
    }

    public int getKey() {
        return key;
    }

    public File getAnalysFile() {
        return analysFile;
    }

    public void setAnalysFile(File analysFile) {
        this.analysFile = analysFile;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getSymbolsCount() {
        return symbols.length();
    }

    static public int normaliseKey(int key) {
        return key % symbols.length();
    }

    public boolean crypt(Boolean decode) throws IOException {

        resulStr = new StringBuilder();
        while (sourceBuff.ready()) {
            int codeSymb;
            String currentChar = String.valueOf((char) sourceBuff.read());
            int codeChar = symbols.indexOf(currentChar);
            if (!(codeChar == -1)) {
                if (!decode) {
                    codeSymb = normaliseKey(codeChar + key);
                    resulStr.append(symbols.charAt(codeSymb));
                } else {
                    codeSymb = normaliseKey(codeChar + symbols.length() - key);
                    resulStr.append(symbols.charAt(codeSymb));
                }
            } else {
                resulStr.append(currentChar);
            }

        }
        sourceBuff.close();

        return true;
    }

    public void analysisEncrypt() throws IOException {
        FileReader fReader = new FileReader(analysFile);
        BufferedReader bReader = new BufferedReader(fReader);
        resulStr = new StringBuilder();

        while (sourceBuff.ready()) {
            resulStr.append((char) sourceBuff.read());
        }
        List abs = new ArrayList();
        List absCode = new ArrayList();

        abs = calculateSymbols(bReader);
        absCode = calculateSymbols(resulStr.toString());

        replaceSymbols(abs, absCode);
        bReader.close();
        saveFile();
    }

    private void replaceSymbols(List<Map.Entry> abs, List<Map.Entry> absCode) {
        int min = Math.min(abs.size(), absCode.size());
        HashMap<Character, CharSequence> replases = new HashMap<>();
        for (int i = 0; i < min; i++) {
            if (symbols.indexOf((Character) abs.get(i).getKey()) == -1) {
                continue;
            }
            String sym = shield(abs.get(i).getKey().toString());
            replases.put((Character) abs.get(i).getKey(), "~" + i + "~");
            String strBeetwin = resulStr.toString().replaceAll(sym, "~" + i + "~");
            String strBeetwin2;
            if (replases.get((Character) absCode.get(i).getKey()) == null) {
                sym = shield(absCode.get(i).getKey().toString());
                strBeetwin2 = strBeetwin.replaceAll(sym, abs.get(i).getKey().toString());
            } else {
                strBeetwin2 = strBeetwin.replace(replases.get(absCode.get(i).getKey()), abs.get(i).getKey().toString());
            }
            resulStr.delete(0, resulStr.length());
            resulStr.append(strBeetwin2);
        }

    }

    private String shield(String str) {
        if (str.equals(".") || str.equals("?") || str.equals("(")
                || str.equals(")")) {
            str = "\\" + str;
        }
        return str;
    }

    private List calculateSymbols(BufferedReader br) throws IOException {
        HashMap<Character, Integer> statistic = new HashMap<>();
        while (br.ready()) {
            Character ch = (char) br.read();
            statistic.put(ch, statistic.get(ch) == null ? 0 : statistic.get(ch) + 1);
        }

        return sortMap(statistic);
    }

    private List calculateSymbols(String br) throws IOException {
        HashMap<Character, Integer> statistic = new HashMap<>();
        for (int i = 0; i < br.length(); i++) {
            Character ch = br.charAt(i);
            statistic.put(ch, statistic.get(ch) == null ? 0 : statistic.get(ch) + 1);
        }
        return sortMap(statistic);
    }

    private List sortMap(HashMap<Character, Integer> map) {
        List list = new ArrayList(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> a, Map.Entry<Integer, Integer> b) {
                return b.getValue() - a.getValue();
            }
        });
        return list;
    }

    public void saveFile() throws IOException {
        JFileChooser jDialog = new JFileChooser();
        jDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (jDialog.showSaveDialog(null) == 0) {
            resultFile = new FileWriter(jDialog.getSelectedFile());
            resultFile.write(resulStr.toString());
        }

        resultFile.close();
        sourceBuff.close();
    }

    public Boolean checkText() {
        return resulStr.indexOf(". ") > 0;
    }

}
