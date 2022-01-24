import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * ��ѯ������
 */
public class MoneyInterface extends JFrame {
    private final JFrame MY;//this
    private final JFrame MENU;//������
    private final int Y;//y����
    private final int X;//x����
    private final int WIDTH;//������
    private final int HEIGHT;//����߶�
    private final String USERID;//���û�ID�����ID

    //���캯���������������棬�û�ID��
    public MoneyInterface(JFrame menu, String userID) throws HeadlessException {
        super();
        this.MY = this;
        this.MENU = menu;
        this.USERID = userID;
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
        this.setTitle("��ѯ���");
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
        JLabel lb_name = new JLabel("�û�����");
        JLabel lb_cardId = new JLabel("��    �ţ�");
        JLabel lb_money = new JLabel("��    �");
        JButton back = new JButton("����");
        JTextField name = new JTextField(10);
        name.setEditable(false);
        JTextField cardId = new JTextField(10);
        cardId.setEditable(false);
        JTextField money = new JTextField(10);
        money.setEditable(false);

        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createHorizontalBox();
        Box box3 = Box.createHorizontalBox();
        Box box4 = Box.createHorizontalBox();
        Box box5 = Box.createVerticalBox();
        //������
        box1.add(lb_name);
        box1.add(name);
        box2.add(lb_cardId);
        box2.add(cardId);
        box3.add(lb_money);
        box3.add(money);
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

        //��ȡ�û���Ϣ
        Cookies resp = Main.request(MY, new Cookies(2, USERID));
        if (resp != null) {
            name.setText(resp.getUserName());
            cardId.setText(resp.getCardID());
            money.setText("" + resp.getMoney());
        }
    }

}
