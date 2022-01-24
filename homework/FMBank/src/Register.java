import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 注册界面：进行开户操作
 */
public class Register extends JFrame {

    private final JFrame MY;//相当于this

    public Register() {
        super();
        MY = this;
        init();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("开户界面");
        this.setResizable(false);
        this.setVisible(true);

    }

    private void init() {
        //设置界面位置和大小
        this.setBounds(400, 200, 400, 300);
        //定义组件
        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        JLabel lb_name = new JLabel("用户名:");
        JLabel lb_id = new JLabel("身份ID：");
        JLabel lb_phone = new JLabel("手机号：");
        JLabel lb_sex = new JLabel("性别：");
        JLabel lb_birth = new JLabel("出生日期：");
        JLabel lb_pw = new JLabel("密码：");
        JLabel lb_pw_2 = new JLabel("确认密码：");
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
        JRadioButton men = new JRadioButton("男");
        JRadioButton women = new JRadioButton("女");
        JButton bt_register = new JButton("注册/开户");
        JButton bt_back = new JButton("返回登陆界面");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(men);
        buttonGroup.add(women);
        //添加组件
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
        //设置字体和背景
        lb_name.setFont(new Font("宋体", Font.PLAIN, 17));
        lb_id.setFont(new Font("宋体", Font.PLAIN, 17));
        lb_phone.setFont(new Font("宋体", Font.PLAIN, 17));
        lb_sex.setFont(new Font("宋体", Font.PLAIN, 17));
        lb_birth.setFont(new Font("宋体", Font.PLAIN, 17));
        lb_pw.setFont(new Font("宋体", Font.PLAIN, 17));
        lb_pw_2.setFont(new Font("宋体", Font.PLAIN, 17));
        men.setSelected(true);
        contentPane.setBackground(Color.PINK);
        contentPane.setLayout(null);
        contentPane.add(jp1);
        contentPane.add(jp2);

        /*
        设置监听事件
         */
        //点击返回
        bt_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login().init();
                MY.dispose();
            }
        });
        //点击注册
        bt_register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取注册用户信息
                String nameText = name.getText();
                String idText = id.getText();
                String phoneText = phone.getText();
                char sex;
                String birthText = birth.getText();
                String pwText = new String(pw.getPassword());
                String pw2Text = new String(pw_2.getPassword());
                //校验用户名
                if (nameText.length() == 0) {
                    JOptionPane.showMessageDialog(MY, "请输入用户名");
                    return;
                }
                if (nameText.length() > 10) {
                    JOptionPane.showMessageDialog(MY, "用户名长度不能超过10");
                    return;
                }
                //校验id
                if (idText.length() == 0) {
                    JOptionPane.showMessageDialog(MY, "请输入ID");
                    return;
                }
                if (idText.length() != 12) {
                    JOptionPane.showMessageDialog(MY, "ID格式错误（长度应该为12）");
                    return;
                }
                //校验手机号
                String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(phoneText);
                boolean isMatch = m.matches();
                if (phoneText.length() == 0) {
                    JOptionPane.showMessageDialog(MY, "请输入手机号");
                    return;
                }
                if (phoneText.length() != 11) {
                    JOptionPane.showMessageDialog(MY, "请输入正确的手机号码");
                    return;
                }
                if (!isMatch) {
                    JOptionPane.showMessageDialog(MY, "请输入正确的手机号码");
                    return;
                }
                //判断性别
                if (women.isSelected()) {
                    sex = 'F';
                } else {
                    sex = 'M';
                }
                //校验日期
                if (birthText.length() == 0) {
                    JOptionPane.showMessageDialog(MY, "请输入出生日期");
                    return;
                }
                //匹配格式
                String regex2 = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
                Pattern pattern = Pattern.compile(regex2);
                Matcher m2 = pattern.matcher(birthText);
                boolean dateFlag = m2.matches();
                if (!dateFlag) {
                    JOptionPane.showMessageDialog(MY, "日期格式错误(tip：YYYY-MM-DD)");
                    return;
                }
                //校验年份
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
                    JOptionPane.showMessageDialog(MY, "日期信息错误,请重新输入");
                    return;
                }
                //获取系统时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String current_time = sdf.format(new Date());
                //判断注册人年龄是否在（18~70）岁之间
                int cur_year = Integer.parseInt(current_time.substring(0, 4));
                int cur_month = Integer.parseInt(current_time.substring(5, 7));
                int cur_day = Integer.parseInt(current_time.substring(8, 10));
                if (!(cur_year - year < 70 || (cur_year - year == 70 && cur_month - month < 0) || (cur_year - year == 70 && cur_month - month == 0 && cur_day - day < 0))) {
                    JOptionPane.showMessageDialog(MY, "注册年龄应小于70岁");
                    return;
                }
                if (!(cur_year - year > 18 || (cur_year - year == 18 && cur_month - month > 0) || (cur_year - year == 18 && cur_month - month == 0 && cur_day - day >= 0))) {
                    JOptionPane.showMessageDialog(MY, "注册年龄应在18岁及以上");
                    return;
                }
                //校验密码
                if (pwText.length() == 0) {
                    JOptionPane.showMessageDialog(MY, "请输入密码");
                    return;
                }
                if (pw2Text.length() == 0) {
                    JOptionPane.showMessageDialog(MY, "请再次输入密码");
                    return;
                }
                if (pwText.length() < 4 || pwText.length() > 12) {
                    JOptionPane.showMessageDialog(MY, "密码长度应在4-12之间");
                    return;
                }
                if (!(pwText.equals(pw2Text))) {
                    pw.setText("");
                    pw_2.setText("");
                    JOptionPane.showMessageDialog(MY, "密码不匹配，请重新输入密码");
                    return;
                }
                //进行注册
                Cookies cookies = new Cookies(1, nameText, idText, phoneText, sex, birthText, pwText);
                Cookies resp = Main.request(MY, cookies);
                if (resp != null) {
                    if (resp.getMemo().equals("success")) {
                        JOptionPane.showMessageDialog(MY, "注册成功！点击返回登陆界面");
                        new Login().init();
                        MY.dispose();
                    } else
                        JOptionPane.showMessageDialog(MY, "注册失败！！！");
                }
            }

        });

    }
}
