import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 主页面1：用于客户端普通用户的图形化界面操作
 * 功能包括：
 * 余额、存款、取款、转账、修改用户信息、申请销户、退出
 */
public class UserMain extends JFrame {
    private final JFrame MY;//this
    private final int Y;//y坐标
    private final int X;//x坐标
    private final int WIDTH;//界面宽度
    private final int HEIGHT;//界面高度

    private final String USERID;//用户ID（身份ID）
    private String USERNAME;//用户名

    //构造函数（传入用户ID）
    public UserMain(String userID) throws HeadlessException {
        super();
        this.MY = this;
        this.X = 600;
        this.Y = 100;
        this.WIDTH = 250;
        this.HEIGHT = 480;
        this.USERID = userID;
        updateTitle();
        init();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //界面不可伸缩
        this.setResizable(false);
        //可见
        this.setVisible(true);
    }

    //更新界面标题
    public void updateTitle() {
        //获取用户信息
        Cookies resp = Main.request(MY, new Cookies(2, USERID));
        if (resp != null) {
            this.USERNAME = resp.getUserName();
            this.setTitle("Hi:" + this.USERNAME);
        }

    }

    private void init() {
        //设置界面位置和大小
        this.setBounds(X, Y, WIDTH, HEIGHT);
        //定义和添加组件
        Container contentPane = this.getContentPane();
        //设置内容面板为绝对布局
        contentPane.setLayout(null);

        JLabel title = new JLabel("飞马银行欢迎您");
        title.setFont(new Font("宋体", Font.PLAIN, 17));
        JButton amount = new JButton("余 额");
        JButton withdraw = new JButton("取 钱");
        JButton deposit = new JButton("存 钱");
        JButton transfer = new JButton("转 账");
        JButton revise = new JButton("修 改");
        JButton delAccount = new JButton("销 户");
        JButton exit = new JButton("退 出");
        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        jp1.setBounds(0, 20, WIDTH, 80);
        jp2.setBounds(0, 100, WIDTH, HEIGHT - 100);
        jp1.add(title);

        Box box = Box.createVerticalBox();
        box.add(amount);
        box.add(Box.createVerticalStrut(15));
        box.add(withdraw);
        box.add(Box.createVerticalStrut(15));
        box.add(deposit);
        box.add(Box.createVerticalStrut(15));
        box.add(transfer);
        box.add(Box.createVerticalStrut(15));
        box.add(revise);
        box.add(Box.createVerticalStrut(15));
        box.add(delAccount);
        box.add(Box.createVerticalStrut(15));
        box.add(exit);
        jp2.add(box);

        contentPane.add(jp1);
        contentPane.add(jp2);

        /*
        设置监听事件
         */
        //查询余额
        amount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MoneyInterface(MY, USERID);
                MY.setEnabled(false);
            }
        });
        //取款
        withdraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WithdrawInterface(MY, USERID);
                MY.setEnabled(false);
            }
        });
        //存款
        deposit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DepositInterface(MY, USERID);
                MY.setEnabled(false);
            }
        });
        //转账
        transfer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TransferInterface(MY, USERID);
                MY.setEnabled(false);
            }
        });
        //修改用户信息
        revise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReviseInterface(MY, USERID);
                MY.setEnabled(false);
            }
        });
        //申请销户
        delAccount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DelAccountInterface(MY, USERID);
                MY.setEnabled(false);
            }
        });
        //退出
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                MY.dispose();
            }
        });
    }
}
