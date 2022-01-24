import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * �޸��������
 */
public class RevisePWInterface extends JFrame {
    private final JFrame MY;//this
    private final JFrame MENU;//������
    private final int Y;//y����
    private final int X;//x����
    private final int WIDTH;//������
    private final int HEIGHT;//����߶�
    private final String USERID;//�û�ID�����ID��

    //���캯���������������棬�û�ID��
    public RevisePWInterface(JFrame menu, String userID) throws HeadlessException {
        super();
        this.MY = this;
        this.USERID = userID;
        this.MENU = menu;
        this.X = 600;
        this.Y = 200;
        this.WIDTH = 300;
        this.HEIGHT = 200;
        init();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                MENU.setEnabled(true);
            }
        });
        this.setTitle("�޸�����");
        //��������
        this.setResizable(false);
        //�ɼ�
        this.setVisible(true);
    }

    private void init() {
        //���ý���λ�úʹ�С
        this.setBounds(X, Y, WIDTH, HEIGHT);
        //��ȡ�������
        Container contentPane = this.getContentPane();
        //�������
        JPanel jp1 = new JPanel();

        JLabel lb_old_pw = new JLabel("�� �� �룺");
        JLabel lb_new_pw = new JLabel("�� �� �룺");
        JLabel lb_new_pw2 = new JLabel("ȷ�����룺");
        JTextField old_pw = new JPasswordField(16);
        JTextField new_pw = new JPasswordField(16);
        JTextField new_pw2 = new JPasswordField(16);
        JButton ok = new JButton("ȷ��");
        JButton back = new JButton("����");

        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createHorizontalBox();
        Box box3 = Box.createHorizontalBox();
        Box box4 = Box.createHorizontalBox();
        Box box5 = Box.createVerticalBox();
        //������
        box1.add(lb_old_pw);
        box1.add(Box.createHorizontalStrut(5));
        box1.add(old_pw);
        box2.add(lb_new_pw);
        box2.add(Box.createHorizontalStrut(5));
        box2.add(new_pw);
        box3.add(lb_new_pw2);
        box3.add(new_pw2);
        box4.add(ok);
        box4.add(Box.createHorizontalStrut(20));
        box4.add(back);
        box5.add(Box.createVerticalStrut(20));
        box5.add(box1);
        box5.add(Box.createVerticalStrut(5));
        box5.add(box2);
        box5.add(Box.createVerticalStrut(5));
        box5.add(box3);
        box5.add(Box.createVerticalStrut(20));
        box5.add(box4);
        jp1.add(box5);
        contentPane.add(jp1);

        /*
        ���ü����¼�
         */
        //�������
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MENU.setEnabled(true);
                MY.dispose();
            }
        });
        //���ȷ��
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (old_pw.getText().length() == 0) {
                    JOptionPane.showMessageDialog(MY, "�����������");
                    return;
                }
                if (new_pw.getText().length() == 0) {
                    JOptionPane.showMessageDialog(MY, "������������");
                    return;
                }
                if (new_pw2.getText().length() == 0) {
                    JOptionPane.showMessageDialog(MY, "���ٴ�����������");
                    return;
                }
                if (new_pw.getText().length() < 4 || new_pw.getText().length() > 12) {
                    JOptionPane.showMessageDialog(MY, "���볤��Ӧ��4-12֮��");
                    return;
                }
                if (!new_pw2.getText().equals(new_pw.getText())) {
                    JOptionPane.showMessageDialog(MY, "���벻ƥ��");
                    return;
                }
                //��ȡ�û�����
                String passWord = null;
                Cookies resp = Main.request(MY, new Cookies(2, USERID));
                if (resp != null) {
                    passWord = resp.getPassWord();
                    if (!old_pw.getText().equals(passWord)) {
                        JOptionPane.showMessageDialog(MY, "�������");
                        return;
                    }
                    Cookies req = new Cookies(8, USERID);
                    req.setPassWord(new_pw.getText());
                    Cookies resp2 = Main.request(MY, req);
                    if (resp2 != null && resp2.getMemo().equals("success")) {
                        JOptionPane.showMessageDialog(MY, "�޸�����ɹ�");
                    }
                }
            }
        });
    }
}
