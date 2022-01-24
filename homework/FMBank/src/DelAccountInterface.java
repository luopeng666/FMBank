import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 销户界面
 */
public class DelAccountInterface extends JDialog {
    private final DelAccountInterface MY = this;
    private final String USERID;//用户ID（身份ID）

    public DelAccountInterface(JFrame memu, String userID) {
        super(memu, "Message");
        this.USERID = userID;
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                memu.setEnabled(true);
            }
        });
        //设置界面位置和大小
        setBounds(600, 250, 300, 120);
        //获取内容面板
        Container contentPane = getContentPane();
        //定义组件
        JLabel lb = new JLabel("请输入密码：");
        JPasswordField pf_pw = new JPasswordField(16);
        JButton bt_ok = new JButton("确定");
        JButton bt_back = new JButton("返回");
        JPanel jp = new JPanel();
        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createHorizontalBox();
        Box box3 = Box.createVerticalBox();
        //添加组件
        box1.add(lb);
        box1.add(pf_pw);
        box2.add(bt_ok);
        box2.add(Box.createHorizontalStrut(10));
        box2.add(bt_back);
        box3.add(box1);
        box3.add(Box.createVerticalStrut(10));
        box3.add(box2);
        jp.add(box3);
        contentPane.add(jp);
        this.setVisible(true);

        /*
        设置监听事件
         */
        //点击确认
        bt_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                memu.setEnabled(true);
                MY.dispose();
            }
        });
        //点击返回
        bt_ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取用户输入的密码
                if (pf_pw.getText().length() == 0) {
                    JOptionPane.showMessageDialog(MY, "请输入密码");
                    return;
                }
                //获取真正的用户密码
                String passWord = null;
                Cookies resp = Main.request(new JFrame(), new Cookies(2, USERID));
                if (resp != null) {
                    passWord = resp.getPassWord();
                    if (!pf_pw.getText().equals(passWord)) {
                        JOptionPane.showMessageDialog(MY, "密码错误");
                        return;
                    }
                    //发送请求进行销户
                    Cookies req = new Cookies(9, USERID);
                    Cookies resp2 = Main.request(new JFrame(), req);
                    if (resp2 != null) {
                        if (resp2.getMemo().equals("success")) {
                            JOptionPane.showMessageDialog(MY, "销户成功,点击退出");
                            memu.dispose();
                        } else {
                            JOptionPane.showMessageDialog(MY, "销户失败");
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(MY, "销户失败");
                }
            }
        });
    }
}
