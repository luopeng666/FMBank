import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 转账界面
 */
public class TransferInterface extends JFrame {
    private final JFrame MY;//this
    private final JFrame MENU;//主界面
    private final int Y;//y坐标
    private final int X;//x坐标
    private final int WIDTH;//界面宽度
    private final int HEIGHT;//界面高度
    private final String USERID;//用户ID（身份ID）

    //构造函数（参数：主界面，用户ID）
    public TransferInterface(JFrame menu, String userID) throws HeadlessException {
        super();
        this.MY = this;
        this.USERID = userID;
        this.MENU = menu;
        this.X = 600;
        this.Y = 200;
        this.WIDTH = 350;
        this.HEIGHT = 250;
        init();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                MENU.setEnabled(true);
            }
        });
        this.setTitle("转账");
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
        JLabel lb_money = new JLabel("余       额：");
        JLabel lb_cardId = new JLabel("对方账户：");
        JLabel lb_transfer = new JLabel("转账金额：");
        JButton ok = new JButton("确认");
        JButton back = new JButton("返回");
        JTextField money = new JTextField(10);
        money.setEditable(false);
        JTextField cardId = new JTextField(10);
        JTextField transfer = new JTextField(10);


        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createHorizontalBox();
        Box box3 = Box.createHorizontalBox();
        Box box4 = Box.createHorizontalBox();
        Box box5 = Box.createVerticalBox();
        //添加组件
        box1.add(lb_money);
        box1.add(money);
        box2.add(lb_cardId);
        box2.add(cardId);
        box3.add(lb_transfer);
        box3.add(transfer);
        box4.add(ok);
        box4.add(Box.createHorizontalStrut(20));
        box4.add(back);

        box5.add(Box.createVerticalStrut(20));
        box5.add(box1);
        box5.add(Box.createVerticalStrut(20));
        box5.add(box2);
        box5.add(Box.createVerticalStrut(20));
        box5.add(box3);
        box5.add(Box.createVerticalStrut(20));
        box5.add(box4);
        box5.add(Box.createVerticalStrut(20));
        jp.add(box5);
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
                //获取转账金额和余额信息
                double transfer_money = Double.parseDouble(transfer.getText());
                double total_money = Double.parseDouble(money.getText());
                if (total_money < transfer_money) {
                    JOptionPane.showMessageDialog(MY, "余额不足");
                    transfer.setText("");
                    return;
                }
                //发送请求进行转账
                Cookies resp = Main.request(MY, new Cookies(6, USERID, cardId.getText(), transfer_money));
                if (resp != null && resp.getMemo().equals("success")) {
                    JOptionPane.showMessageDialog(MY, "转账成功");
                    //刷新余额信息
                    Cookies resp2 = Main.request(MY, new Cookies(3, USERID));
                    if (resp2 != null) {
                        money.setText("" + resp2.getMoney());
                    }
                    transfer.setText("");
                }
            }
        });

    }

}
