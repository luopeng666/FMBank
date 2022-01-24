import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * �޸��û���Ϣ�Ľ���
 */
public class ReviseInterface extends JFrame {
    private final JFrame MY;//this
    private final JFrame MENU;//������
    private final int Y;//y����
    private final int X;//x����
    private final int WIDTH;//������
    private final int HEIGHT;//����߶�
    private final String USERID;//�û�ID�����ID��

    //���캯���������������棬�û�ID��
    public ReviseInterface(JFrame menu, String userID) throws HeadlessException {
        super();
        this.MY = this;
        this.USERID = userID;
        this.MENU = menu;
        this.X = 600;
        this.Y = 200;
        this.WIDTH = 400;
        this.HEIGHT = 350;
        init();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                MENU.setEnabled(true);
            }
        });
        this.setTitle("�޸��û���Ϣ");
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

        JLabel lb_name = new JLabel("�û���:");
        JLabel lb_cardId = new JLabel("����:");
        JLabel lb_id = new JLabel("���ID��");
        JLabel lb_phone = new JLabel("�ֻ��ţ�");
        JLabel lb_sex = new JLabel("�Ա�");
        JLabel lb_birth = new JLabel("�������ڣ�");
        JTextField name = new JTextField(10);
        JTextField cardID = new JTextField(10);
        JTextField id = new JTextField(12);
        JTextField phone = new JTextField(11);
        JTextField birth = new JTextField(10);
        JRadioButton men = new JRadioButton("��");
        JRadioButton women = new JRadioButton("Ů");
        JButton bt_revisePW = new JButton("�޸�����");
        JButton bt_ok = new JButton("ȷ���޸�");
        JButton bt_back = new JButton("����");

        Box box1 = Box.createVerticalBox();
        Box box2 = Box.createVerticalBox();
        Box box3 = Box.createHorizontalBox();
        Box box4 = Box.createHorizontalBox();
        Box box5 = Box.createHorizontalBox();
        Box box6 = Box.createVerticalBox();

        //������
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(men);
        buttonGroup.add(women);

        box1.add(lb_name);
        box1.add(lb_cardId);
        box1.add(lb_id);
        box1.add(Box.createVerticalStrut(2));
        box1.add(lb_phone);
        box1.add(Box.createVerticalStrut(3));
        box1.add(lb_sex);
        box1.add(Box.createVerticalStrut(1));
        box1.add(lb_birth);
        box2.add(Box.createVerticalStrut(2));
        box2.add(name);
        box2.add(cardID);
        box2.add(Box.createVerticalStrut(0));
        box2.add(id);
        box2.add(phone);
        box4.add(men);
        box4.add(women);
        box2.add(box4);
        box2.add(birth);
        box3.add(box1);
        box3.add(box2);

        box5.add(bt_ok);
        box5.add(Box.createHorizontalStrut(5));
        box5.add(bt_back);
        box6.add(Box.createVerticalStrut(20));
        box6.add(box3);
        box6.add(Box.createVerticalStrut(10));
        box6.add(bt_revisePW);
        box6.add(Box.createVerticalStrut(30));
        box6.add(box5);

        jp1.add(box6);
        //��������
        lb_name.setFont(new Font("����", Font.PLAIN, 17));
        lb_cardId.setFont(new Font("����", Font.PLAIN, 17));
        lb_id.setFont(new Font("����", Font.PLAIN, 17));
        lb_phone.setFont(new Font("����", Font.PLAIN, 17));
        lb_sex.setFont(new Font("����", Font.PLAIN, 17));
        lb_birth.setFont(new Font("����", Font.PLAIN, 17));
        //���ò��ɱ༭
        men.setSelected(true);
        id.setEditable(false);
        cardID.setEditable(false);

        contentPane.add(jp1);

        //��ȡ�û���Ϣ����Ⱦ��ҳ��
        Cookies resp = Main.request(MY, new Cookies(2, USERID));
        if (resp != null) {
            name.setText(resp.getUserName());
            cardID.setText(resp.getCardID());
            id.setText(resp.getUserID());
            phone.setText(resp.getPhone());
            birth.setText(resp.getBirthday());
            char ch_sex = resp.getSex();
            if (ch_sex == 'F')
                women.setSelected(true);
        }
        /*
        ���ü����¼�
         */
        //�������
        bt_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MENU.setEnabled(true);
                if (MENU instanceof UserMain)
                    ((UserMain) MENU).updateTitle();
                MY.dispose();
            }
        });
        //���ȷ��
        bt_ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //���ı����ȡ�û���Ϣ
                String new_name = name.getText();
                String new_phone = phone.getText();
                String new_birth = birth.getText();
                char new_sex = 'M';
                if (women.isSelected())
                    new_sex = 'F';
                //У���û���
                if (new_name.length() == 0) {
                    JOptionPane.showMessageDialog(MY, "�������û���");
                    return;
                }
                if (new_name.length() > 10) {
                    JOptionPane.showMessageDialog(MY, "�û������Ȳ��ܳ���10");
                    return;
                }
                //У���ֻ���
                String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(new_phone);
                boolean isMatch = m.matches();
                if (new_phone.length() == 0) {
                    JOptionPane.showMessageDialog(MY, "�������ֻ���");
                    return;
                }
                if (new_phone.length() != 11) {
                    JOptionPane.showMessageDialog(MY, "��������ȷ���ֻ�����");
                    return;
                }
                if (!isMatch) {
                    JOptionPane.showMessageDialog(MY, "��������ȷ���ֻ�����");
                    return;
                }
                //У������
                if (new_birth.length() == 0) {
                    JOptionPane.showMessageDialog(MY, "�������������");
                    return;
                }
                //ƥ���ʽ
                String regex2 = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
                Pattern pattern = Pattern.compile(regex2);
                Matcher m2 = pattern.matcher(new_birth);
                boolean dateFlag = m2.matches();
                if (!dateFlag) {
                    JOptionPane.showMessageDialog(MY, "���ڸ�ʽ����(tip��YYYY-MM-DD)");
                    return;
                }
                //У�����
                int year = Integer.parseInt(new_birth.substring(0, 4));
                int month = Integer.parseInt(new_birth.substring(5, 7));
                int day = Integer.parseInt(new_birth.substring(8, 10));
                boolean tag = true;
                if (year < 1900 || year > 2022 || month < 1 || month > 12 || day < 1 || day > 31)
                    tag = false;
                else if (month == 4 || month == 6 || month == 9 || month == 11)
                    if (day == 31)
                        tag = false;
                    else if (month == 2) {
                        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
                            if (day > 29)
                                tag = false;
                            else if (day > 28)
                                tag = false;
                    } else if (year == 2022) {
                        if (month > 1)
                            tag = false;
                        else if (day > 2)
                            tag = false;
                    }
                if (!tag) {
                    JOptionPane.showMessageDialog(MY, "������Ϣ����,����������");
                    return;
                }
                //������������޸Ĳ���
                Cookies resp = Main.request(MY, new Cookies(7, USERID, new_name, new_phone, new_sex, new_birth));
                if (resp != null) {
                    if (resp.getMemo().equals("success")) {
                        JOptionPane.showMessageDialog(MY, "�޸ĸ�����Ϣ�ɹ�");
                        //������Ⱦҳ��
                        Cookies resp2 = Main.request(MY, new Cookies(2, USERID));
                        if (resp2 != null) {
                            name.setText(resp2.getUserName());
                            phone.setText(resp2.getPhone());
                            birth.setText(resp2.getBirthday());
                            char ch_sex = resp2.getSex();
                            if (ch_sex == 'F')
                                women.setSelected(true);
                        }
                    } else JOptionPane.showMessageDialog(MY, "�޸ĸ�����Ϣʧ��");
                }
            }
        });

        bt_revisePW.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RevisePWInterface(MY, USERID);
            }
        });
    }
}
