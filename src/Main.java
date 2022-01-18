import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    static JFrame mFrame = getMainFrame();
    private static String fileName;

    private static Kripto kripto;

    public static void main(String[] args) {
        createMainWin();

    }

    static void createMainWin() {
        JPanel jPanel = new JPanel();
        mFrame.add(jPanel);
        jPanel.add(new JLabel("Ключ шифра: "));
        jPanel.add(addJTextField());
        JButton butCode = new JButton("Зашифровать");
        butCode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jDialog = new JFileChooser();
                jDialog.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.getName().endsWith("txt");
                    }

                    @Override
                    public String getDescription() {
                        return "*.txt";
                    }
                });

                if (jDialog.showOpenDialog(jPanel) == 0) {
                    try {
                        kripto = new Kripto(jDialog.getSelectedFile());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }


            }
        });
        butCode.setSize(5, 3);
        jPanel.add(butCode);
        JButton butDecode = new JButton("Расшифровать");
        butDecode.setSize(5, 3);
        jPanel.add(butDecode);

        jPanel.revalidate();

    }


    static JTextField addJTextField() {
        JTextField jTextField = new JTextField(7);
        ((AbstractDocument) jTextField.getDocument()).setDocumentFilter(new DocumentFilter() {
            Pattern regEx = Pattern.compile("\\d*");

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                Matcher matcher = regEx.matcher(text);
                if (!matcher.matches()) {
                    return;
                }
                super.replace(fb, offset, length, text, attrs);
            }
        });

        jTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                System.out.println(jTextField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                System.out.println(jTextField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                System.out.println(jTextField.getText());
            }
        });
        return jTextField;
    }

    static JFrame getMainFrame() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 100);
        frame.setResizable(false);
        Dimension vSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return frame;
    }


}
