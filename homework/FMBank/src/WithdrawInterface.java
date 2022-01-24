import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 取款界面
 */
public class WithdrawInterface extends JFrame {
    private final JFrame MY;//this
    private final JFrame MENU;//主界面（菜单界面）
    private final int Y;//y坐标
    private final int X;//x坐标
    private final int WIDTH;//界面宽度
    private final int HEIGHT;//界面高度
    private final String USERID;//用户ID（身份ID）

    //构造函数（参数：主界面，用户ID）
    public WithdrawInterface(JFrame menu, String userID) throws HeadlessException {
        super();
        this.MY = this;
        this.USERID = userID;
        this.MENU = menu;
        this.X = 600;
        this.Y = 200;
        this.WIDTH = 350;
        this.HEIGHT = 250;
        init();
        //自定义关闭（返回上一个界面）
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                MENU.setEnabled(true);
            }
        });
        this.setTitle("取款");
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
        JPanel jp = new JPanel();
        JLabel lb_money = new JLabel("余        额：");
        JLabel lb_withdraw = new JLabel("取款金额：");
        JButton back = new JButton("返回");
        JButton ok = new JButton("确定");
        JTextField money = new JTextField(10);
        money.setEditable(false);
        JTextField withdraw = new JTextField(10);

        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createHorizontalBox();
        Box box3 = Box.createHorizontalBox();
        Box box4 = Box.createVerticalBox();
        //添加组件
        box1.add(lb_money);
        box1.add(money);
        box2.add(lb_withdraw);
        box2.add(withdraw);
        box3.add(ok);
        box3.add(Box.createHorizontalStrut(20));
        box3.add(back);

        box4.add(Box.createVerticalStrut(30));
        box4.add(box1);
        box4.add(Box.createVerticalStrut(20));
        box4.add(box2);
        box4.add(Box.createVerticalStrut(30));
        box4.add(box3);

        jp.add(box4);
        contentPane.add(jp);

        //获取余额信息
        Cookies resp = Main.request(MY, new Cookies(3, USERID));
        if (resp != null) {
            money.setText("" + resp.getMoney());
        }
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
                //获取要取款的金额
                double withdraw_money = Double.parseDouble(withdraw.getText());
                //总余额
                double total_money = Double.parseDouble(money.getText());
                if (total_money < withdraw_money) {
                    JOptionPane.showMessageDialog(MY, "余额不足");
                    withdraw.setText("");
                    return;
                }
                //发送请求进行取款
                Cookies resp = Main.request(MY, new Cookies(4, USERID, withdraw_money));
                if (resp != null && resp.getMemo().equals("success")) {
                    JOptionPane.showMessageDialog(MY, "取款成功");
                    //刷新余额信息
                    Cookies resp2 = Main.request(MY, new Cookies(3, USERID));
                    if (resp2 != null) {
                        money.setText("" + resp2.getMoney());
                    }
                    //更新输入框
                    withdraw.setText("");
                }
            }
        });

    }

}