import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 修改用户信息的界面
 */
public class ReviseInterface extends JFrame {
    private final JFrame MY;//this
    private final JFrame MENU;//主界面
    private final int Y;//y坐标
    private final int X;//x坐标
    private final int WIDTH;//界面宽度
    private final int HEIGHT;//界面高度
    private final String USERID;//用户ID（身份ID）

    //构造函数（参数：主界面，用户ID）
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
        this.setTitle("修改用户信息");
        //不可伸缩
        this.setResizable(false);
        //可见
        this.setVisible(true);
    }

    private void init() {
        //设置界面位置和大小
        this.setBounds(X, Y, WIDTH, HEIGHT);
        //获取内容面板
        Container contentPane = this.getContentPane();
        //定义组件
        JPanel jp1 = new JPanel();

        JLabel lb_name = new JLabel("用户名:");
        JLabel lb_cardId = new JLabel("卡号:");
        JLabel lb_id = new JLabel("身份ID：");
        JLabel lb_phone = new JLabel("手机号：");
        JLabel lb_sex = new JLabel("性别：");
        JLabel lb_birth = new JLabel("出生日期：");
        JTextField name = new JTextField(10);
        JTextField cardID = new JTextField(10);
        JTextField id = new JTextField(12);
        JTextField phone = new JTextField(11);
        JTextField birth = new JTextField(10);
        JRadioButton men = new JRadioButton("男");
        JRadioButton women = new JRadioButton("女");
        JButton bt_revisePW = new JButton("修改密码");
        JButton bt_ok = new JButton("确定修改");
        JButton bt_back = new JButton("返回");

        Box box1 = Box.createVerticalBox();
        Box box2 = Box.createVerticalBox();
        Box box3 = Box.createHorizontalBox();
        Box box4 = Box.createHorizontalBox();
        Box box5 = Box.createHorizontalBox();
        Box box6 = Box.createVerticalBox();

        //添加组件
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
        //设置字体
        lb_name.setFont(new Font("宋体", Font.PLAIN, 17));
        lb_cardId.setFont(new Font("宋体", Font.PLAIN, 17));
        lb_id.setFont(new Font("宋体", Font.PLAIN, 17));
        lb_phone.setFont(new Font("宋体", Font.PLAIN, 17));
        lb_sex.setFont(new Font("宋体", Font.PLAIN, 17));
        lb_birth.setFont(new Font("宋体", Font.PLAIN, 17));
        //设置不可编辑
        men.setSelected(true);
        id.setEditable(false);
        cardID.setEditable(false);

        contentPane.add(jp1);

        //获取用户信息并渲染到页面
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
        设置监听事件
         */
        //点击返回
        bt_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MENU.setEnabled(true);
                if (MENU instanceof UserMain)
                    ((UserMain) MENU).updateTitle();
                MY.dispose();
            }
        });
        //点击确认
        bt_ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //从文本域获取用户信息
                String new_name = name.getText();
                String new_phone = phone.getText();
                String new_birth = birth.getText();
                char new_sex = 'M';
                if (women.isSelected())
                    new_sex = 'F';
                //校验用户名
                if (new_name.length() == 0) {
                    JOptionPane.showMessageDialog(MY, "请输入用户名");
                    return;
                }
                if (new_name.length() > 10) {
                    JOptionPane.showMessageDialog(MY, "用户名长度不能超过10");
                    return;
                }
                //校验手机号
                String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(new_phone);
                boolean isMatch = m.matches();
                if (new_phone.length() == 0) {
                    JOptionPane.showMessageDialog(MY, "请输入手机号");
                    return;
                }
                if (new_phone.length() != 11) {
                    JOptionPane.showMessageDialog(MY, "请输入正确的手机号码");
                    return;
                }
                if (!isMatch) {
                    JOptionPane.showMessageDialog(MY, "请输入正确的手机号码");
                    return;
                }
                //校验日期
                if (new_birth.length() == 0) {
                    JOptionPane.showMessageDialog(MY, "请输入出生日期");
                    return;
                }
                //匹配格式
                String regex2 = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
                Pattern pattern = Pattern.compile(regex2);
                Matcher m2 = pattern.matcher(new_birth);
                boolean dateFlag = m2.matches();
                if (!dateFlag) {
                    JOptionPane.showMessageDialog(MY, "日期格式错误(tip：YYYY-MM-DD)");
                    return;
                }
                //校验年份
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
                    JOptionPane.showMessageDialog(MY, "日期信息错误,请重新输入");
                    return;
                }
                //发送请求进行修改操作
                Cookies resp = Main.request(MY, new Cookies(7, USERID, new_name, new_phone, new_sex, new_birth));
                if (resp != null) {
                    if (resp.getMemo().equals("success")) {
                        JOptionPane.showMessageDialog(MY, "修改个人信息成功");
                        //重新渲染页面
                        Cookies resp2 = Main.request(MY, new Cookies(2, USERID));
                        if (resp2 != null) {
                            name.setText(resp2.getUserName());
                            phone.setText(resp2.getPhone());
                            birth.setText(resp2.getBirthday());
                            char ch_sex = resp2.getSex();
                            if (ch_sex == 'F')
                                women.setSelected(true);
                        }
                    } else JOptionPane.showMessageDialog(MY, "修改个人信息失败");
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
