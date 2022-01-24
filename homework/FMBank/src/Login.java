import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ��¼���棺����ͻ�������ͨ�û��͹���Ա�ĵ�¼
 */
public class Login extends JFrame {
    private String USERID;//�û�ID�����ID��
    private String Pw;//�û�����
    private boolean rank;//�û�����
    private final JFrame MY;//this

    public Login() {
        init();
        MY = this;
        this.setTitle("����������Ϣϵͳ");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    void init() {
        //��ȡ�������
        Container contentPane = this.getContentPane();
        //����λ�úʹ�С
        this.setBounds(400, 200, 500, 300);
        contentPane.setLayout(null);
        //�������
        JLabel lb_id = new JLabel("���id��");
        JPanel jP1 = new JPanel();
        JPanel jP2 = new JPanel();
        JButton login = new JButton("��¼");
        JButton register = new JButton("����");
        JCheckBox people = new JCheckBox("��ͨ�û�");
        JCheckBox manager = new JCheckBox("����Ա");
        JTextField tf_id = new JTextField(10);
        JLabel lb_pw = new JLabel("���룺");
        JPasswordField pf_pw = new JPasswordField(16);
        ButtonGroup buttonGroup = new ButtonGroup();
        //������
        buttonGroup.add(people);
        buttonGroup.add(manager);
        Box Box1 = Box.createHorizontalBox();
        Box Box2 = Box.createHorizontalBox();
        Box Box3 = Box.createHorizontalBox();
        Box Box4 = Box.createHorizontalBox();
        Box Box5 = Box.createVerticalBox();
        Box1.add(lb_id);
        Box1.add(Box.createHorizontalStrut(35));
        Box1.add(tf_id);
        Box2.add(lb_pw);
        Box2.add(Box.createHorizontalStrut(45));
        Box2.add(pf_pw);
        Box3.add(people);
        Box3.add(Box.createHorizontalStrut(10));
        Box3.add(manager);
        Box4.add(login);
        Box4.add(Box.createHorizontalStrut(80));
        Box4.add(register);
        Box5.add(Box1);
        Box5.add(Box2);
        Box5.add(Box.createVerticalStrut(10));
        Box5.add(Box3);
        Box5.add(Box.createVerticalStrut(8));
        Box5.add(Box4);
        contentPane.add(jP1);
        contentPane.add(jP2);
        //������������λ�úʹ�С
        jP1.setBounds(0, 0, 500, 80);
        jP2.setBounds(0, 80, 500, 220);
        jP2.add(Box5);
        //��ӱ���ͼƬ
        ImageIcon icon = new ImageIcon("src/static/bank.jpg");
        JLabel lb_bg = new JLabel(icon);
        lb_bg.setSize(500, 300);
        this.getLayeredPane().add(lb_bg, new Integer(Integer.MIN_VALUE));
        JPanel jPanel = (JPanel) contentPane;
        jPanel.setOpaque(false);
        jP2.setOpaque(false);
        jP1.setOpaque(false);
        jP1.setBackground(null);
        jP2.setBackground(null);
        people.setBackground(null);
        manager.setBackground(null);
        people.setOpaque(false);
        manager.setOpaque(false);
        lb_id.setForeground(Color.cyan);
        lb_pw.setForeground(Color.cyan);
        people.setForeground(Color.cyan);
        manager.setForeground(Color.cyan);
        people.setSelected(true);

        /*
         �����¼�����
         */
        //�����¼
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //��ȡ�û�ID
                String userID = tf_id.getText();
                USERID = userID;
                //��ȡ����
                String pw = new String(pf_pw.getPassword());
                Pw = pw;
                //�ж��û�����
                rank = manager.isSelected();
                //У��
                if (userID.length() == 0) {
                    JOptionPane.showMessageDialog(MY, "���������id");
                    return;
                }
                if (pw.length() == 0) {
                    JOptionPane.showMessageDialog(MY, "����������");
                    return;
                }
                if (check()) {
                    JOptionPane.showMessageDialog(MY, "��½�ɹ�");
                    if (rank) {
                        new ManagerMain(USERID);
                    } else {
                        new UserMain(USERID);
                    }
                    MY.dispose();
                } else {
                    JOptionPane.showMessageDialog(MY, "��½ʧ��");
                }

            }

        });
        //�������
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Register();
                MY.dispose();
            }
        });
    }

    //���͵�¼���󣬲����ص�¼���
    private boolean check() {
        Cookies request = Main.request(MY, new Cookies(USERID, Pw, rank));
        if (request == null)
            return false;
        if (request.getMemo() != null && request.getMemo().equals("success"))
            return true;
        if (request.getMemo() == null)
            return false;
        return true;
    }
}
