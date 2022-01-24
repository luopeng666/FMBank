import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 导出用户信息界面
 */
public class ExportUserInfInterface extends JFrame {
    private final JFrame MY;//this
    private final JFrame MENU;//主界面
    private final int Y;//y坐标
    private final int X;//x坐标
    private final int WIDTH;//界面宽度
    private final int HEIGHT;//界面高度

    public ExportUserInfInterface(JFrame menu) {
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
        this.setTitle("导出用户信息");
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
        JLabel lb1 = new JLabel("请输入导出文件位置(如：D:/export.xls)");
        JLabel lb2 = new JLabel("路径：");
        JTextField tx_path = new JTextField(16);
        JButton bt_ok = new JButton("导出用户信息");
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
        //点击返回
        bt_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MENU.setEnabled(true);
                MY.dispose();
            }
        });
        //点击确认
        bt_ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = tx_path.getText();
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(path);
                    //获取工作簿
                    HSSFWorkbook workbook = new HSSFWorkbook();
                    // 获取Excel第一个选项卡对象
                    Sheet sheet = workbook.createSheet();
                    //获取第一行
                    Row row1 = sheet.createRow(0);
                    //设置每列的标签
                    row1.createCell(0).setCellValue("cardid");
                    row1.createCell(1).setCellValue("username");
                    row1.createCell(2).setCellValue("userpassword");
                    row1.createCell(3).setCellValue("userid");
                    row1.createCell(4).setCellValue("phonenum");
                    row1.createCell(5).setCellValue("sex");
                    row1.createCell(6).setCellValue("birthday");
                    row1.createCell(7).setCellValue("money");
                    row1.createCell(8).setCellValue("memo");
                    //发送请求，获取所有用户信息
                    Cookies req = new Cookies();
                    req.setOrder(10);
                    Cookies resp = Main.request(MY, req);
                    int n = 1;//计行数
                    if (resp != null) {
                        ArrayList<Cookies> list = resp.getList();
                        //遍历每个用户
                        for (Cookies cookies : list) {
                            //添加新行
                            Row row = sheet.createRow(n);
                            //填值
                            row.createCell(0).setCellValue(cookies.getCardID());
                            row.createCell(1).setCellValue(cookies.getUserName());
                            row.createCell(2).setCellValue(cookies.getPassWord());
                            row.createCell(3).setCellValue(cookies.getUserID());
                            row.createCell(4).setCellValue(cookies.getPhone());
                            row.createCell(5).setCellValue("" + cookies.getSex());
                            row.createCell(6).setCellValue(cookies.getBirthday());
                            row.createCell(7).setCellValue(cookies.getMoney());
                            row.createCell(8).setCellValue(cookies.getMemo());
                            n++;
                        }
                    }
                    workbook.write(fos);

                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
