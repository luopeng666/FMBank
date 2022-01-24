import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ע����棺���п�������
 */
public class Register extends JFrame {

    private final JFrame MY;//�൱��this

    public Register() {
        super();
        MY = this;
        init();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("��������");
        this.setResizable(false);
        this.setVisible(true);

    }

    private void init() {
        //���ý���λ�úʹ�С
        this.setBounds(400, 200, 400, 300);
        //�������
        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        JLabel lb_name = new JLabel("�û���:");
        JLabel lb_id = new JLabel("���ID��");
        JLabel lb_phone = new JLabel("�ֻ��ţ�");
        JLabel lb_sex = new JLabel("�Ա�");
        JLabel lb_birth = new JLabel("�������ڣ�");
        JLabel lb_pw = new JLabel("���룺");
        JLabel lb_pw_2 = new JLabel("ȷ�����룺");
        JTextField name = new JTextField(10);
        JTextField id = new JTextField(12);
        JTextField phone = new JTextField(11);
        JTextField birth = new JTextField(10);
        JPasswordField pw = new JPasswordField(16);
        JPasswordField pw_2 = new JPasswordField(16);

        Container contentPane = this.getContentPane();
        Box box1 = Box.createVerticalBox();
        Box box2 = Box.createVerticalBox();
        Box box3 = Box.createHorizontalBox();
        Box box4 = Box.createHorizontalBox();
        Box box5 = Box.createHorizontalBox();
        JRadioButton men = new JRadioButton("��");
        JRadioButton women = new JRadioButton("Ů");
        JButton bt_register = new JButton("ע��/����");
        JButton bt_back = new JButton("���ص�½����");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(men);
        buttonGroup.add(women);
        //������
        box1.add(lb_name);
        box1.add(lb_id);
        box1.add(Box.createVerticalStrut(2));
        box1.add(lb_phone);
        box1.add(Box.createVerticalStrut(3));
        box1.add(lb_sex);
        box1.add(Box.createVerticalStrut(1));
        box1.add(lb_birth);
        box1.add(lb_pw);
        box1.add(lb_pw_2);
        box2.add(Box.createVerticalStrut(2));
        box2.add(name);
        box2.add(Box.createVerticalStrut(0));
        box2.add(id);
        box2.add(phone);
        box4.add(men);
        box4.add(women);
        box2.add(box4);
        box2.add(birth);
        box2.add(pw);
        box2.add(pw_2);
        box5.add(bt_register);
        box5.add(bt_back);

        box3.add(box1);
        box3.add(box2);
        jp1.add(box3);
        jp1.setOpaque(false);
        men.setBackground(Color.PINK);
        women.setBackground(Color.PINK);
        jp1.setBounds(0, 30, 400, 200);
        jp2.setBounds(0, 230, 400, 70);
        jp2.add(box5);
        //��������ͱ���
        lb_name.setFont(new Font("����", Font.PLAIN, 17));
        lb_id.setFont(new Font("����", Font.PLAIN, 17));
        lb_phone.setFont(new Font("����", Font.PLAIN, 17));
        lb_sex.setFont(new Font("����", Font.PLAIN, 17));
        lb_birth.setFont(new Font("����", Font.PLAIN, 17));
        lb_pw.setFont(new Font("����", Font.PLAIN, 17));
        lb_pw_2.setFont(new Font("����", Font.PLAIN, 17));
        men.setSelected(true);
        contentPane.setBackground(Color.PINK);
        contentPane.setLayout(null);
        contentPane.add(jp1);
        contentPane.add(jp2);

        /*
        ���ü����¼�
         */
        //�������
        bt_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login().init();
                MY.dispose();
            }
        });
        //���ע��
        bt_register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //��ȡע���û���Ϣ
                String nameText = name.getText();
                String idText = id.getText();
                String phoneText = phone.getText();
                char sex;
                String birthText = birth.getText();
                String pwText = new String(pw.getPassword());
                String pw2Text = new String(pw_2.getPassword());
                //У���û���
                if (nameText.length() == 0) {
                    JOptionPane.showMessageDialog(MY, "�������û���");
                    return;
                }
                if (nameText.length() > 10) {
                    JOptionPane.showMessageDialog(MY, "�û������Ȳ��ܳ���10");
                    return;
                }
                //У��id
                if (idText.length() == 0) {
                    JOptionPane.showMessageDialog(MY, "������ID");
                    return;
                }
                if (idText.length() != 12) {
                    JOptionPane.showMessageDialog(MY, "ID��ʽ���󣨳���Ӧ��Ϊ12��");
                    return;
                }
                //У���ֻ���
                String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(phoneText);
                boolean isMatch = m.matches();
                if (phoneText.length() == 0) {
                    JOptionPane.showMessageDialog(MY, "�������ֻ���");
                    return;
                }
                if (phoneText.length() != 11) {
                    JOptionPane.showMessageDialog(MY, "��������ȷ���ֻ�����");
                    return;
                }
                if (!isMatch) {
                    JOptionPane.showMessageDialog(MY, "��������ȷ���ֻ�����");
                    return;
                }
                //�ж��Ա�
                if (women.isSelected()) {
                    sex = 'F';
                } else {
                    sex = 'M';
                }
                //У������
                if (birthText.length() == 0) {
                    JOptionPane.showMessageDialog(MY, "�������������");
                    return;
                }
                //ƥ���ʽ
                String regex2 = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
                Pattern pattern = Pattern.compile(regex2);
                Matcher m2 = pattern.matcher(birthText);
                boolean dateFlag = m2.matches();
                if (!dateFlag) {
                    JOptionPane.showMessageDialog(MY, "���ڸ�ʽ����(tip��YYYY-MM-DD)");
                    return;
                }
                //У�����
                int year = Integer.parseInt(birthText.substring(0, 4));
                int month = Integer.parseInt(birthText.substring(5, 7));
                int day = Integer.parseInt(birthText.substring(8, 10));
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
                //��ȡϵͳʱ��
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String current_time = sdf.format(new Date());
                //�ж�ע���������Ƿ��ڣ�18~70����֮��
                int cur_year = Integer.parseInt(current_time.substring(0, 4));
                int cur_month = Integer.parseInt(current_time.substring(5, 7));
                int cur_day = Integer.parseInt(current_time.substring(8, 10));
                if (!(cur_year - year < 70 || (cur_year - year == 70 && cur_month - month < 0) || (cur_year - year == 70 && cur_month - month == 0 && cur_day - day < 0))) {
                    JOptionPane.showMessageDialog(MY, "ע������ӦС��70��");
                    return;
                }
                if (!(cur_year - year > 18 || (cur_year - year == 18 && cur_month - month > 0) || (cur_year - year == 18 && cur_month - month == 0 && cur_day - day >= 0))) {
                    JOptionPane.showMessageDialog(MY, "ע������Ӧ��18�꼰����");
                    return;
                }
                //У������
                if (pwText.length() == 0) {
                    JOptionPane.showMessageDialog(MY, "����������");
                    return;
                }
                if (pw2Text.length() == 0) {
                    JOptionPane.showMessageDialog(MY, "���ٴ���������");
                    return;
                }
                if (pwText.length() < 4 || pwText.length() > 12) {
                    JOptionPane.showMessageDialog(MY, "���볤��Ӧ��4-12֮��");
                    return;
                }
                if (!(pwText.equals(pw2Text))) {
                    pw.setText("");
                    pw_2.setText("");
                    JOptionPane.showMessageDialog(MY, "���벻ƥ�䣬��������������");
                    return;
                }
                //����ע��
                Cookies cookies = new Cookies(1, nameText, idText, phoneText, sex, birthText, pwText);
                Cookies resp = Main.request(MY, cookies);
                if (resp != null) {
                    if (resp.getMemo().equals("success")) {
                        JOptionPane.showMessageDialog(MY, "ע��ɹ���������ص�½����");
                        new Login().init();
                        MY.dispose();
                    } else
                        JOptionPane.showMessageDialog(MY, "ע��ʧ�ܣ�����");
                }
            }

        });

    }
}
