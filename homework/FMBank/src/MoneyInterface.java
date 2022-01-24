import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 查询余额界面
 */
public class MoneyInterface extends JFrame {
    private final JFrame MY;//this
    private final JFrame MENU;//主界面
    private final int Y;//y坐标
    private final int X;//x坐标
    private final int WIDTH;//界面宽度
    private final int HEIGHT;//界面高度
    private final String USERID;//（用户ID）身份ID

    //构造函数（参数：主界面，用户ID）
    public MoneyInterface(JFrame menu, String userID) throws HeadlessException {
        super();
        this.MY = this;
        this.MENU = menu;
        this.USERID = userID;
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
        this.setTitle("查询余额");
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
        JLabel lb_name = new JLabel("用户名：");
        JLabel lb_cardId = new JLabel("卡    号：");
        JLabel lb_money = new JLabel("余    额：");
        JButton back = new JButton("返回");
        JTextField name = new JTextField(10);
        name.setEditable(false);
        JTextField cardId = new JTextField(10);
        cardId.setEditable(false);
        JTextField money = new JTextField(10);
        money.setEditable(false);

        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createHorizontalBox();
        Box box3 = Box.createHorizontalBox();
        Box box4 = Box.createHorizontalBox();
        Box box5 = Box.createVerticalBox();
        //添加组件
        box1.add(lb_name);
        box1.add(name);
        box2.add(lb_cardId);
        box2.add(cardId);
        box3.add(lb_money);
        box3.add(money);
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

        //获取用户信息
        Cookies resp = Main.request(MY, new Cookies(2, USERID));
        if (resp != null) {
            name.setText(resp.getUserName());
            cardId.setText(resp.getCardID());
            money.setText("" + resp.getMoney());
        }
    }

}
