import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 修改密码界面
 */
public class RevisePWInterface extends JFrame {
    private final JFrame MY;//this
    private final JFrame MENU;//主界面
    private final int Y;//y坐标
    private final int X;//x坐标
    private final int WIDTH;//界面宽度
    private final int HEIGHT;//界面高度
    private final String USERID;//用户ID（身份ID）

    //构造函数（参数：主界面，用户ID）
    public RevisePWInterface(JFrame menu, String userID) throws HeadlessException {
        super();
        this.MY = this;
        this.USERID = userID;
        this.MENU = menu;
        this.X = 600;
        this.Y = 200;
        this.WIDTH = 300;
        this.HEIGHT = 200;
        init();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                MENU.setEnabled(true);
            }
        });
        this.setTitle("修改密码");
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

        JLabel lb_old_pw = new JLabel("旧 密 码：");
        JLabel lb_new_pw = new JLabel("新 密 码：");
        JLabel lb_new_pw2 = new JLabel("确认密码：");
        JTextField old_pw = new JPasswordField(16);
        JTextField new_pw = new JPasswordField(16);
        JTextField new_pw2 = new JPasswordField(16);
        JButton ok = new JButton("确认");
        JButton back = new JButton("返回");

        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createHorizontalBox();
        Box box3 = Box.createHorizontalBox();
        Box box4 = Box.createHorizontalBox();
        Box box5 = Box.createVerticalBox();
        //添加组件
        box1.add(lb_old_pw);
        box1.add(Box.createHorizontalStrut(5));
        box1.add(old_pw);
        box2.add(lb_new_pw);
        box2.add(Box.createHorizontalStrut(5));
        box2.add(new_pw);
        box3.add(lb_new_pw2);
        box3.add(new_pw2);
        box4.add(ok);
        box4.add(Box.createHorizontalStrut(20));
        box4.add(back);
        box5.add(Box.createVerticalStrut(20));
        box5.add(box1);
        box5.add(Box.createVerticalStrut(5));
        box5.add(box2);
        box5.add(Box.createVerticalStrut(5));
        box5.add(box3);
        box5.add(Box.createVerticalStrut(20));
        box5.add(box4);
        jp1.add(box5);
        contentPane.add(jp1);

        /*
        设置监听事件
         */
        //点击返回
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MENU.setEnabled(true);
                MY.dispose();
            }
        });
        //点击确认
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (old_pw.getText().length() == 0) {
                    JOptionPane.showMessageDialog(MY, "请输入旧密码");
                    return;
                }
                if (new_pw.getText().length() == 0) {
                    JOptionPane.showMessageDialog(MY, "请输入新密码");
                    return;
                }
                if (new_pw2.getText().length() == 0) {
                    JOptionPane.showMessageDialog(MY, "请再次输入新密码");
                    return;
                }
                if (new_pw.getText().length() < 4 || new_pw.getText().length() > 12) {
                    JOptionPane.showMessageDialog(MY, "密码长度应在4-12之间");
                    return;
                }
                if (!new_pw2.getText().equals(new_pw.getText())) {
                    JOptionPane.showMessageDialog(MY, "密码不匹配");
                    return;
                }
                //获取用户密码
                String passWord = null;
                Cookies resp = Main.request(MY, new Cookies(2, USERID));
                if (resp != null) {
                    passWord = resp.getPassWord();
                    if (!old_pw.getText().equals(passWord)) {
                        JOptionPane.showMessageDialog(MY, "密码错误");
                        return;
                    }
                    Cookies req = new Cookies(8, USERID);
                    req.setPassWord(new_pw.getText());
                    Cookies resp2 = Main.request(MY, req);
                    if (resp2 != null && resp2.getMemo().equals("success")) {
                        JOptionPane.showMessageDialog(MY, "修改密码成功");
                    }
                }
            }
        });
    }
}
