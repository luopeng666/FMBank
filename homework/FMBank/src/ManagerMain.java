import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 主页面2：用于客户端管理员用户的图形化界面操作
 * 功能包括：
 * 余额、存款、取款、转账、修改用户信息、申请销户
 * 批量开户（xls文件格式）、导出用户信息（xls文件格式）、生成年终报表（PDF格式）、退出
 */
public class ManagerMain extends JFrame {
    private final JFrame MY;//this
    private final int Y;//y坐标
    private final int X;//x坐标
    private final int WIDTH;//界面宽度
    private final int HEIGHT;//界面高度
    private final String USERID;//用户ID（身份ID）

    //构造函数（传入用户ID）
    public ManagerMain(String userID) throws HeadlessException {
        super();
        this.MY = this;
        this.USERID = userID;
        this.X = 600;
        this.Y = 100;
        this.WIDTH = 350;
        this.HEIGHT = 450;
        init();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //设置界面标题
        this.setTitle("管理员界面");
        //界面不可伸缩
        this.setResizable(false);
        //可见
        this.setVisible(true);
    }

    private void init() {
        //设置界面位置和大小
        this.setBounds(X, Y, WIDTH, HEIGHT);
        //定义和添加组件
        Container contentPane = this.getContentPane();
        contentPane.setLayout(null);

        JLabel title = new JLabel("飞马银行欢迎您");
        title.setFont(new Font("宋体", Font.PLAIN, 20));
        JLabel lb_basic = new JLabel("基本功能");
        JButton amount = new JButton("余额");
        JButton withdraw = new JButton("取钱");
        JButton deposit = new JButton("存钱");
        JButton transfer = new JButton("转账");
        JButton revise = new JButton("修改");
        JButton exit = new JButton("退出");
        JLabel lb_super = new JLabel("高级功能");
        JButton delSelf = new JButton("销户");
        JButton openAll = new JButton("批量开户");
        JButton exportAll = new JButton("导出用户信息");
        JButton report = new JButton("生成报表");


        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        jp1.setBounds(0, 20, WIDTH, 100);
        jp2.setBounds(0, 100, WIDTH, HEIGHT - 100);
        jp1.add(title);

        Box box1 = Box.createVerticalBox();
        Box box2 = Box.createVerticalBox();
        Box box3 = Box.createHorizontalBox();

        box1.add(Box.createVerticalStrut(15));
        box1.add(lb_basic);
        box1.add(Box.createVerticalStrut(15));
        box1.add(amount);
        box1.add(Box.createVerticalStrut(15));
        box1.add(withdraw);
        box1.add(Box.createVerticalStrut(15));
        box1.add(deposit);
        box1.add(Box.createVerticalStrut(15));
        box1.add(transfer);
        box1.add(Box.createVerticalStrut(15));
        box1.add(revise);

        box2.add(Box.createVerticalStrut(15));
        box2.add(lb_super);
        box2.add(Box.createVerticalStrut(15));
        box2.add(delSelf);
        box2.add(Box.createVerticalStrut(15));
        box2.add(openAll);
        box2.add(Box.createVerticalStrut(15));
        box2.add(exportAll);
        box2.add(Box.createVerticalStrut(15));
        box2.add(report);
        box2.add(Box.createVerticalStrut(15));
        box2.add(exit);

        box3.add(Box.createHorizontalStrut(15));
        box3.add(box1);
        box3.add(Box.createHorizontalStrut(50));
        box3.add(box2);
        jp2.add(box3);


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
        //取钱
        withdraw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new WithdrawInterface(MY, USERID);
                MY.setEnabled(false);
            }
        });
        //存钱
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
        //修改
        revise.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReviseInterface(MY, USERID);
                MY.setEnabled(false);
            }
        });
        //销户
        delSelf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DelAccountInterface(MY, USERID);
                MY.setEnabled(false);
            }
        });
        //批量开户
        openAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OpenBatchInterface(MY);
                MY.setEnabled(false);
            }
        });
        //导出用户信息
        exportAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ExportUserInfInterface(MY);
            }
        });
        //生成报表
        report.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FinalPDF(MY);
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
