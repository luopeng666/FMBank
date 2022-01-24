import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ��ҳ��1�����ڿͻ�����ͨ�û���ͼ�λ��������
 * ���ܰ�����
 * ����ȡ�ת�ˡ��޸��û���Ϣ�������������˳�
 */
public class UserMain extends JFrame {
    private final JFrame MY;//this
    private final int Y;//y����
    private final int X;//x����
    private final int WIDTH;//������
    private final int HEIGHT;//����߶�

    private final String USERID;//�û�ID�����ID��
    private String USERNAME;//�û���

    //���캯���������û�ID��
    public UserMain(String userID) throws HeadlessException {
        super();
        this.MY = this;
        this.X = 600;
        this.Y = 100;
        this.WIDTH = 250;
        this.HEIGHT = 480;
        this.USERID = userID;
        updateTitle();
        init();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //���治������
        this.setResizable(false);
        //�ɼ�
        this.setVisible(true);
    }

    //���½������
    public void updateTitle() {
        //��ȡ�û���Ϣ
        Cookies resp = Main.request(MY, new Cookies(2, USERID));
        if (resp != null) {
            this.USERNAME = resp.getUserName();
            this.setTitle("Hi:" + this.USERNAME);
        }

    }

    private void init() {
        //���ý���λ�úʹ�С
        this.setBounds(X, Y, WIDTH, HEIGHT);
        //�����������
        Container contentPane = this.getContentPane();
        //�����������Ϊ���Բ���
        contentPane.setLayout(null);

        JLabel title = new JLabel("�������л�ӭ��");
        title.setFont(new Font("����", Font.PLAIN, 17));
        JButton amount = new JButton("�� ��");
        JButton withdraw = new JButton("ȡ Ǯ");
        JButton deposit = new JButton("�� Ǯ");
        JButton transfer = new JButton("ת ��");
        JButton revise = new JButton("�� ��");
        JButton delAccount = new JButton("�� ��");
        JButton exit = new JButton("�� ��");
        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        jp1.setBounds(0, 20, WIDTH, 80);
        jp2.setBounds(0, 100, WIDTH, HEIGHT - 100);
        jp1.add(title);

        Box box = Box.createVerticalBox();
        box.add(amount);
        box.add(Box.createVerticalStrut(15));
        box.add(withdraw);
        box.add(Box.createVerticalStrut(15));
        box.add(deposit);
        box.add(Box.createVerticalStrut(15));
        box.add(transfer);
        box.add(Box.createVerticalStrut(15));
        box.add(revise);
        box.add(Box.createVerticalStrut(15));
        box.add(delAccount);
        box.add(Box.createVerticalStrut(15));
        box.add(exit);
        jp2.add(box);

        contentPane.add(jp1);
        contentPane.add(jp2);

        /*
        ���ü����¼�
         */
        //��ѯ���
        amount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MoneyInterface(MY, USERID);
                MY.setEnabled(false);
            }
        });
        //ȡ��
        withdraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WithdrawInterface(MY, USERID);
                MY.setEnabled(false);
            }
        });
        //���
        deposit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DepositInterface(MY, USERID);
                MY.setEnabled(false);
            }
        });
        //ת��
        transfer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TransferInterface(MY, USERID);
                MY.setEnabled(false);
            }
        });
        //�޸��û���Ϣ
        revise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReviseInterface(MY, USERID);
                MY.setEnabled(false);
            }
        });
        //��������
        delAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DelAccountInterface(MY, USERID);
                MY.setEnabled(false);
            }
        });
        //�˳�
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                MY.dispose();
            }
        });
    }
}
