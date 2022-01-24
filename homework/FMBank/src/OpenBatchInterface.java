import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

/**
 * 批量开户界面
 */
public class OpenBatchInterface extends JFrame {
    private final JFrame MY;//this
    private final JFrame MENU;//主界面
    private final int Y;//y坐标
    private final int X;//x坐标
    private final int WIDTH;//宽度
    private final int HEIGHT;//高度

    public OpenBatchInterface(JFrame menu) {
        super();
        MY = this;
        this.MENU = menu;
        this.X = 600;
        this.Y = 200;
        this.WIDTH = 400;
        this.HEIGHT = 200;
        init();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                MENU.setEnabled(true);
            }
        });
        this.setTitle("批量开户");
        this.setResizable(false);
        this.setVisible(true);

    }

    // 构造函数
    public void init() {
        //设置界面位置和大小
        this.setBounds(X, Y, WIDTH, HEIGHT);
        //获取内容面板
        Container contentPane = this.getContentPane();
        //定义组件
        JPanel jp1 = new JPanel();
        JLabel lb1 = new JLabel("请输入xls文件的位置(如：D:/1.xls)");
        JLabel lb2 = new JLabel("路径：");
        JTextField tx_path = new JTextField(16);
        JButton bt_ok = new JButton("批量开户");
        JButton bt_back = new JButton("返         回");
        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createHorizontalBox();
        Box box3 = Box.createHorizontalBox();
        Box box4 = Box.createVerticalBox();
        //添加组件
        box1.add(lb1);
        box2.add(lb2);
        box2.add(tx_path);
        box3.add(Box.createHorizontalStrut(20));
        box3.add(bt_ok);
        box3.add(Box.createHorizontalStrut(40));
        box3.add(bt_back);
        box4.add(Box.createVerticalStrut(30));
        box4.add(box1);
        box4.add(Box.createVerticalStrut(5));
        box4.add(box2);
        box4.add(Box.createVerticalStrut(20));
        box4.add(box3);
        jp1.add(box4);
        contentPane.add(jp1);
        /*
        设置监听事件
         */
        //点击确认
        bt_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MENU.setEnabled(true);
                MY.dispose();
            }
        });
        //点击返回
        bt_ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileInputStream fis = null;
                try {
                    String path = tx_path.getText();
                    fis = new FileInputStream(path);
                    HSSFWorkbook book = new HSSFWorkbook(fis);
                    // 获取Excel第一个选项卡对象
                    Sheet sheet = book.getSheetAt(0);
                    //得到行数
                    int n = sheet.getPhysicalNumberOfRows();

                    for (int i = 1; i < n; i++) {
                        Row row = sheet.getRow(i);
                        String userName = row.getCell(0).getStringCellValue();
                        String userPW = row.getCell(1).getStringCellValue();
                        String userID = row.getCell(2).getStringCellValue();
                        String userPhone = row.getCell(3).getStringCellValue();
                        String userSex = row.getCell(4).getStringCellValue();
                        String userBirth = row.getCell(5).getStringCellValue();
                        //进行注册
                        Cookies cookies = new Cookies(1, userName, userID, userPhone, userSex.charAt(0), userBirth, userPW);
                        Cookies resp = Main.request(MY, cookies);
                        if (resp != null) {
                            if (resp.getMemo().equals("success")) {
                                continue;
                            } else
                                JOptionPane.showMessageDialog(MY, "用户:" + userName + "注册失败！！！");
                        }
                    }

                } catch (IOException h) {
                    // TODO Auto-generated catch block
                    h.printStackTrace();
                } finally {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
