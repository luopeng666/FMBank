import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 登录界面：负责客户端中普通用户和管理员的登录
 */
public class Login extends JFrame {
    private String USERID;//用户ID（身份ID）
    private String Pw;//用户密码
    private boolean rank;//用户类型
    private final JFrame MY;//this

    public Login() {
        init();
        MY = this;
        this.setTitle("飞马银行信息系统");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    void init() {
        //获取内容面板
        Container contentPane = this.getContentPane();
        //设置位置和大小
        this.setBounds(400, 200, 500, 300);
        contentPane.setLayout(null);
        //定义组件
        JLabel lb_id = new JLabel("身份id：");
        JPanel jP1 = new JPanel();
        JPanel jP2 = new JPanel();
        JButton login = new JButton("登录");
        JButton register = new JButton("开户");
        JCheckBox people = new JCheckBox("普通用户");
        JCheckBox manager = new JCheckBox("管理员");
        JTextField tf_id = new JTextField(10);
        JLabel lb_pw = new JLabel("密码：");
        JPasswordField pf_pw = new JPasswordField(16);
        ButtonGroup buttonGroup = new ButtonGroup();
        //添加组件
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
        //设置两个面板的位置和大小
        jP1.setBounds(0, 0, 500, 80);
        jP2.setBounds(0, 80, 500, 220);
        jP2.add(Box5);
        //添加背景图片
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
         设置事件监听
         */
        //点击登录
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取用户ID
                String userID = tf_id.getText();
                USERID = userID;
                //获取密码
                String pw = new String(pf_pw.getPassword());
                Pw = pw;
                //判断用户类型
                rank = manager.isSelected();
                //校验
                if (userID.length() == 0) {
                    JOptionPane.showMessageDialog(MY, "请输入身份id");
                    return;
                }
                if (pw.length() == 0) {
                    JOptionPane.showMessageDialog(MY, "请输入密码");
                    return;
                }
                if (check()) {
                    JOptionPane.showMessageDialog(MY, "登陆成功");
                    if (rank) {
                        new ManagerMain(USERID);
                    } else {
                        new UserMain(USERID);
                    }
                    MY.dispose();
                } else {
                    JOptionPane.showMessageDialog(MY, "登陆失败");
                }

            }

        });
        //点击开户
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Register();
                MY.dispose();
            }
        });
    }

    //发送登录请求，并返回登录结果
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
