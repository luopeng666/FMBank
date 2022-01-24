import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ��ҳ��2�����ڿͻ��˹���Ա�û���ͼ�λ��������
 * ���ܰ�����
 * ����ȡ�ת�ˡ��޸��û���Ϣ����������
 * ����������xls�ļ���ʽ���������û���Ϣ��xls�ļ���ʽ�����������ձ���PDF��ʽ�����˳�
 */
public class ManagerMain extends JFrame {
    private final JFrame MY;//this
    private final int Y;//y����
    private final int X;//x����
    private final int WIDTH;//������
    private final int HEIGHT;//����߶�
    private final String USERID;//�û�ID�����ID��

    //���캯���������û�ID��
    public ManagerMain(String userID) throws HeadlessException {
        super();
        this.MY = this;
        this.USERID = userID;
        this.X = 600;
        this.Y = 100;
        this.WIDTH = 350;
        this.HEIGHT = 450;
        init();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //���ý������
        this.setTitle("����Ա����");
        //���治������
        this.setResizable(false);
        //�ɼ�
        this.setVisible(true);
    }

    private void init() {
        //���ý���λ�úʹ�С
        this.setBounds(X, Y, WIDTH, HEIGHT);
        //�����������
        Container contentPane = this.getContentPane();
        contentPane.setLayout(null);

        JLabel title = new JLabel("�������л�ӭ��");
        title.setFont(new Font("����", Font.PLAIN, 20));
        JLabel lb_basic = new JLabel("��������");
        JButton amount = new JButton("���");
        JButton withdraw = new JButton("ȡǮ");
        JButton deposit = new JButton("��Ǯ");
        JButton transfer = new JButton("ת��");
        JButton revise = new JButton("�޸�");
        JButton exit = new JButton("�˳�");
        JLabel lb_super = new JLabel("�߼�����");
        JButton delSelf = new JButton("����");
        JButton openAll = new JButton("��������");
        JButton exportAll = new JButton("�����û���Ϣ");
        JButton report = new JButton("���ɱ���");


        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        jp1.setBounds(0, 20, WIDTH, 100);
        jp2.setBounds(0, 100, WIDTH, HEIGHT - 100);
        jp1.add(title);

        Box box1 = Box.createVerticalBox();
        Box box2 = Box.createVerticalBox();
        Box box3 = Box.createHorizontalBox();

        box1.add(Box.createVerticalStrut(15));
        box1.add(lb_basic);
        box1.add(Box.createVerticalStrut(15));
        box1.add(amount);
        box1.add(Box.createVerticalStrut(15));
        box1.add(withdraw);
        box1.add(Box.createVerticalStrut(15));
        box1.add(deposit);
        box1.add(Box.createVerticalStrut(15));
        box1.add(transfer);
        box1.add(Box.createVerticalStrut(15));
        box1.add(revise);

        box2.add(Box.createVerticalStrut(15));
        box2.add(lb_super);
        box2.add(Box.createVerticalStrut(15));
        box2.add(delSelf);
        box2.add(Box.createVerticalStrut(15));
        box2.add(openAll);
        box2.add(Box.createVerticalStrut(15));
        box2.add(exportAll);
        box2.add(Box.createVerticalStrut(15));
        box2.add(report);
        box2.add(Box.createVerticalStrut(15));
        box2.add(exit);

        box3.add(Box.createHorizontalStrut(15));
        box3.add(box1);
        box3.add(Box.createHorizontalStrut(50));
        box3.add(box2);
        jp2.add(box3);


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
        //ȡǮ
        withdraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WithdrawInterface(MY, USERID);
                MY.setEnabled(false);
            }
        });
        //��Ǯ
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
        //�޸�
        revise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReviseInterface(MY, USERID);
                MY.setEnabled(false);
            }
        });
        //����
        delSelf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DelAccountInterface(MY, USERID);
                MY.setEnabled(false);
            }
        });
        //��������
        openAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OpenBatchInterface(MY);
                MY.setEnabled(false);
            }
        });
        //�����û���Ϣ
        exportAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ExportUserInfInterface(MY);
            }
        });
        //���ɱ���
        report.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FinalPDF(MY);
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
