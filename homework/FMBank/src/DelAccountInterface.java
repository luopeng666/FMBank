import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * ��������
 */
public class DelAccountInterface extends JDialog {
    private final DelAccountInterface MY = this;
    private final String USERID;//�û�ID�����ID��

    public DelAccountInterface(JFrame memu, String userID) {
        super(memu, "Message");
        this.USERID = userID;
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                memu.setEnabled(true);
            }
        });
        //���ý���λ�úʹ�С
        setBounds(600, 250, 300, 120);
        //��ȡ�������
        Container contentPane = getContentPane();
        //�������
        JLabel lb = new JLabel("���������룺");
        JPasswordField pf_pw = new JPasswordField(16);
        JButton bt_ok = new JButton("ȷ��");
        JButton bt_back = new JButton("����");
        JPanel jp = new JPanel();
        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createHorizontalBox();
        Box box3 = Box.createVerticalBox();
        //������
        box1.add(lb);
        box1.add(pf_pw);
        box2.add(bt_ok);
        box2.add(Box.createHorizontalStrut(10));
        box2.add(bt_back);
        box3.add(box1);
        box3.add(Box.createVerticalStrut(10));
        box3.add(box2);
        jp.add(box3);
        contentPane.add(jp);
        this.setVisible(true);

        /*
        ���ü����¼�
         */
        //���ȷ��
        bt_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                memu.setEnabled(true);
                MY.dispose();
            }
        });
        //�������
        bt_ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //��ȡ�û����������
                if (pf_pw.getText().length() == 0) {
                    JOptionPane.showMessageDialog(MY, "����������");
                    return;
                }
                //��ȡ�������û�����
                String passWord = null;
                Cookies resp = Main.request(new JFrame(), new Cookies(2, USERID));
                if (resp != null) {
                    passWord = resp.getPassWord();
                    if (!pf_pw.getText().equals(passWord)) {
                        JOptionPane.showMessageDialog(MY, "�������");
                        return;
                    }
                    //���������������
                    Cookies req = new Cookies(9, USERID);
                    Cookies resp2 = Main.request(new JFrame(), req);
                    if (resp2 != null) {
                        if (resp2.getMemo().equals("success")) {
                            JOptionPane.showMessageDialog(MY, "�����ɹ�,����˳�");
                            memu.dispose();
                        } else {
                            JOptionPane.showMessageDialog(MY, "����ʧ��");
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(MY, "����ʧ��");
                }
            }
        });
    }
}
