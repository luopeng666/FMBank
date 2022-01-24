import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * ת�˽���
 */
public class TransferInterface extends JFrame {
    private final JFrame MY;//this
    private final JFrame MENU;//������
    private final int Y;//y����
    private final int X;//x����
    private final int WIDTH;//������
    private final int HEIGHT;//����߶�
    private final String USERID;//�û�ID�����ID��

    //���캯���������������棬�û�ID��
    public TransferInterface(JFrame menu, String userID) throws HeadlessException {
        super();
        this.MY = this;
        this.USERID = userID;
        this.MENU = menu;
        this.X = 600;
        this.Y = 200;
        this.WIDTH = 350;
        this.HEIGHT = 250;
        init();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                MENU.setEnabled(true);
            }
        });
        this.setTitle("ת��");
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
        JPanel jp = new JPanel();
        JLabel lb_money = new JLabel("��       �");
        JLabel lb_cardId = new JLabel("�Է��˻���");
        JLabel lb_transfer = new JLabel("ת�˽�");
        JButton ok = new JButton("ȷ��");
        JButton back = new JButton("����");
        JTextField money = new JTextField(10);
        money.setEditable(false);
        JTextField cardId = new JTextField(10);
        JTextField transfer = new JTextField(10);


        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createHorizontalBox();
        Box box3 = Box.createHorizontalBox();
        Box box4 = Box.createHorizontalBox();
        Box box5 = Box.createVerticalBox();
        //������
        box1.add(lb_money);
        box1.add(money);
        box2.add(lb_cardId);
        box2.add(cardId);
        box3.add(lb_transfer);
        box3.add(transfer);
        box4.add(ok);
        box4.add(Box.createHorizontalStrut(20));
        box4.add(back);

        box5.add(Box.createVerticalStrut(20));
        box5.add(box1);
        box5.add(Box.createVerticalStrut(20));
        box5.add(box2);
        box5.add(Box.createVerticalStrut(20));
        box5.add(box3);
        box5.add(Box.createVerticalStrut(20));
        box5.add(box4);
        box5.add(Box.createVerticalStrut(20));
        jp.add(box5);
        contentPane.add(jp);

        //��ȡ�����Ϣ
        Cookies resp = Main.request(MY, new Cookies(3, USERID));
        if (resp != null) {
            money.setText("" + resp.getMoney());
        }

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
                //��ȡת�˽��������Ϣ
                double transfer_money = Double.parseDouble(transfer.getText());
                double total_money = Double.parseDouble(money.getText());
                if (total_money < transfer_money) {
                    JOptionPane.showMessageDialog(MY, "����");
                    transfer.setText("");
                    return;
                }
                //�����������ת��
                Cookies resp = Main.request(MY, new Cookies(6, USERID, cardId.getText(), transfer_money));
                if (resp != null && resp.getMemo().equals("success")) {
                    JOptionPane.showMessageDialog(MY, "ת�˳ɹ�");
                    //ˢ�������Ϣ
                    Cookies resp2 = Main.request(MY, new Cookies(3, USERID));
                    if (resp2 != null) {
                        money.setText("" + resp2.getMoney());
                    }
                    transfer.setText("");
                }
            }
        });

    }

}
