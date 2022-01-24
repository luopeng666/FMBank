import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * ȡ�����
 */
public class WithdrawInterface extends JFrame {
    private final JFrame MY;//this
    private final JFrame MENU;//�����棨�˵����棩
    private final int Y;//y����
    private final int X;//x����
    private final int WIDTH;//������
    private final int HEIGHT;//����߶�
    private final String USERID;//�û�ID�����ID��

    //���캯���������������棬�û�ID��
    public WithdrawInterface(JFrame menu, String userID) throws HeadlessException {
        super();
        this.MY = this;
        this.USERID = userID;
        this.MENU = menu;
        this.X = 600;
        this.Y = 200;
        this.WIDTH = 350;
        this.HEIGHT = 250;
        init();
        //�Զ���رգ�������һ�����棩
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                MENU.setEnabled(true);
            }
        });
        this.setTitle("ȡ��");
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
        JLabel lb_money = new JLabel("��        �");
        JLabel lb_withdraw = new JLabel("ȡ���");
        JButton back = new JButton("����");
        JButton ok = new JButton("ȷ��");
        JTextField money = new JTextField(10);
        money.setEditable(false);
        JTextField withdraw = new JTextField(10);

        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createHorizontalBox();
        Box box3 = Box.createHorizontalBox();
        Box box4 = Box.createVerticalBox();
        //������
        box1.add(lb_money);
        box1.add(money);
        box2.add(lb_withdraw);
        box2.add(withdraw);
        box3.add(ok);
        box3.add(Box.createHorizontalStrut(20));
        box3.add(back);

        box4.add(Box.createVerticalStrut(30));
        box4.add(box1);
        box4.add(Box.createVerticalStrut(20));
        box4.add(box2);
        box4.add(Box.createVerticalStrut(30));
        box4.add(box3);

        jp.add(box4);
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
                //��ȡҪȡ��Ľ��
                double withdraw_money = Double.parseDouble(withdraw.getText());
                //�����
                double total_money = Double.parseDouble(money.getText());
                if (total_money < withdraw_money) {
                    JOptionPane.showMessageDialog(MY, "����");
                    withdraw.setText("");
                    return;
                }
                //�����������ȡ��
                Cookies resp = Main.request(MY, new Cookies(4, USERID, withdraw_money));
                if (resp != null && resp.getMemo().equals("success")) {
                    JOptionPane.showMessageDialog(MY, "ȡ��ɹ�");
                    //ˢ�������Ϣ
                    Cookies resp2 = Main.request(MY, new Cookies(3, USERID));
                    if (resp2 != null) {
                        money.setText("" + resp2.getMoney());
                    }
                    //���������
                    withdraw.setText("");
                }
            }
        });

    }

}