import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.Font;

/**
 * 生成报表界面
 */
public class FinalPDF extends JFrame {
    //数据表的字段数
    private static final int colNum = 9;
    //设置表格的属性
    private static final int spacing = 2;
    private static final int padding = 1;

    private static final Document document = new Document();
    private static final String path = "C://Windows/Fonts/simhei.ttf";
    private static final Font font = FontFactory.getFont(path, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED, 15f, Font.NORMAL,
            BaseColor.BLACK);
    private final JFrame MY;//this
    private final JFrame MENU;//主界面

    public FinalPDF(JFrame memu) {
        super();
        MY = this;
        this.MENU = memu;
        init();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                MENU.setEnabled(true);
            }
        });
        this.setTitle("年终报表");
        this.setResizable(false);
        this.setVisible(true);
    }

    // 构造函数
    public void init() {
        //设置界面位置和大小
        this.setBounds(400, 200, 400, 200);
        //获取内容面板
        Container contentPane = this.getContentPane();
        //定义组件
        JPanel jp1 = new JPanel();
        JLabel lb1 = new JLabel("请输入PDF输出的位置(如：D:/test.PDF)");
        JLabel lb2 = new JLabel("路径：");
        JTextField tx_path = new JTextField(16);
        JButton bt_ok = new JButton("生成报表");
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
                String pdfpath = tx_path.getText();
                //在文档中创建一个pdf文件
                try {
                    PdfWriter.getInstance(document, new FileOutputStream(pdfpath));
                    document.open();
                    Paragraph p = new Paragraph("客 户 信 息", font);
                    p.setAlignment(Element.ALIGN_CENTER);
                    p.setSpacingAfter(30f);
                    document.add(p);
                    usertable();//生成所有用户信息表
                    //获取总余额
                    double totalbanlance = -1;
                    Cookies req1 = new Cookies();
                    req1.setOrder(11);
                    Cookies resp1 = Main.request(MY, req1);
                    if (resp1 != null)
                        totalbanlance = resp1.getMoney();
                    document.add(new Paragraph("存储总余额：" + totalbanlance, font));
                    //获取用户总数
                    int totalusernum = -1;
                    Cookies req2 = new Cookies();
                    req2.setOrder(12);
                    Cookies resp2 = Main.request(MY, req2);
                    if (resp2 != null)
                        totalusernum = Integer.parseInt(resp2.getMemo());
                    document.add(new Paragraph("总开户数：" + totalusernum, font));
                    //今年新增开户人数
                    int newusernum = -1;
                    Cookies req3 = new Cookies();
                    req3.setOrder(13);
                    Cookies resp3 = Main.request(MY, req3);
                    if (resp3 != null)
                        newusernum = Integer.parseInt(resp3.getMemo());
                    document.add(new Paragraph("今年新增开户数: " + newusernum, font));
                    //备注信息
                    document.add(new Paragraph("单位：飞马银行", font));
                    Date time = new Date();
                    document.add(new Paragraph("生成日期：" + time, font));
                } catch (Exception ee) {
                    ee.printStackTrace();
                } finally {
                    document.close();
                }
            }
        });
    }

    //生成所有用户信息表
    public void usertable() {
        try {
            //设置字段名
            List<String> list = Arrays.asList("卡号", "用户名", "密码", "身份ID", "电话号码", "性别", "出生日期", "余额", "备注");
            ArrayList<String> ColumnLabels = new ArrayList<>(list);
            //创建含有colNum个字段的表格
            PdfPTable table = new PdfPTable(colNum);
            int[] cellsWidth = {10, 10, 10, 10, 10, 10, 10, 10, 10};
            //设置字段单元格的相对大小
            table.setWidths(cellsWidth);
            //设置表格占文档的大小比例
            table.setWidthPercentage(90);
            table.getDefaultCell().setPadding(padding);
            table.getDefaultCell().setBorderWidth(spacing);
            //设置单元格文本居中
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            //添加字段
            for (int i = 0; i < colNum; i++) {
                table.getDefaultCell().setBackgroundColor(BaseColor.GRAY);
                //获取字段名
                table.addCell(new Paragraph(ColumnLabels.get(i), font));
            }
            table.setHeaderRows(1);//表头设置结束,表示第一行属于表头

            table.getDefaultCell().setBorderWidth(1);
            //获取所有用户信息
            Cookies req = new Cookies();
            req.setOrder(10);
            Cookies resp3 = Main.request(MY, req);
            //建表
            if (resp3 != null) {
                ArrayList<Cookies> resultlist = resp3.getList();
                for (Cookies cookies : resultlist) {
                    //设置背景颜色
                    table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
                    table.addCell(new Paragraph(cookies.getCardID(), font));
                    table.addCell(new Paragraph(cookies.getUserName(), font));
                    table.addCell(new Paragraph(cookies.getPassWord(), font));
                    table.addCell(new Paragraph(cookies.getUserID(), font));
                    table.addCell(new Paragraph(cookies.getPhone(), font));
                    table.addCell(new Paragraph(cookies.getSex() + "", font));
                    table.addCell(new Paragraph(cookies.getBirthday(), font));
                    table.addCell(new Paragraph(cookies.getMoney() + "", font));
                    table.addCell(new Paragraph(cookies.getMemo(), font));
                }
            }
            document.add(table);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
