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
 * ���ɱ������
 */
public class FinalPDF extends JFrame {
    //���ݱ���ֶ���
    private static final int colNum = 9;
    //���ñ�������
    private static final int spacing = 2;
    private static final int padding = 1;

    private static final Document document = new Document();
    private static final String path = "C://Windows/Fonts/simhei.ttf";
    private static final Font font = FontFactory.getFont(path, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED, 15f, Font.NORMAL,
            BaseColor.BLACK);
    private final JFrame MY;//this
    private final JFrame MENU;//������

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
        this.setTitle("���ձ���");
        this.setResizable(false);
        this.setVisible(true);
    }

    // ���캯��
    public void init() {
        //���ý���λ�úʹ�С
        this.setBounds(400, 200, 400, 200);
        //��ȡ�������
        Container contentPane = this.getContentPane();
        //�������
        JPanel jp1 = new JPanel();
        JLabel lb1 = new JLabel("������PDF�����λ��(�磺D:/test.PDF)");
        JLabel lb2 = new JLabel("·����");
        JTextField tx_path = new JTextField(16);
        JButton bt_ok = new JButton("���ɱ���");
        JButton bt_back = new JButton("��         ��");
        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createHorizontalBox();
        Box box3 = Box.createHorizontalBox();
        Box box4 = Box.createVerticalBox();
        //������
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
        ���ü����¼�
         */
        //�������
        bt_back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MENU.setEnabled(true);
                MY.dispose();
            }
        });
        //���ȷ��
        bt_ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pdfpath = tx_path.getText();
                //���ĵ��д���һ��pdf�ļ�
                try {
                    PdfWriter.getInstance(document, new FileOutputStream(pdfpath));
                    document.open();
                    Paragraph p = new Paragraph("�� �� �� Ϣ", font);
                    p.setAlignment(Element.ALIGN_CENTER);
                    p.setSpacingAfter(30f);
                    document.add(p);
                    usertable();//���������û���Ϣ��
                    //��ȡ�����
                    double totalbanlance = -1;
                    Cookies req1 = new Cookies();
                    req1.setOrder(11);
                    Cookies resp1 = Main.request(MY, req1);
                    if (resp1 != null)
                        totalbanlance = resp1.getMoney();
                    document.add(new Paragraph("�洢����" + totalbanlance, font));
                    //��ȡ�û�����
                    int totalusernum = -1;
                    Cookies req2 = new Cookies();
                    req2.setOrder(12);
                    Cookies resp2 = Main.request(MY, req2);
                    if (resp2 != null)
                        totalusernum = Integer.parseInt(resp2.getMemo());
                    document.add(new Paragraph("�ܿ�������" + totalusernum, font));
                    //����������������
                    int newusernum = -1;
                    Cookies req3 = new Cookies();
                    req3.setOrder(13);
                    Cookies resp3 = Main.request(MY, req3);
                    if (resp3 != null)
                        newusernum = Integer.parseInt(resp3.getMemo());
                    document.add(new Paragraph("��������������: " + newusernum, font));
                    //��ע��Ϣ
                    document.add(new Paragraph("��λ����������", font));
                    Date time = new Date();
                    document.add(new Paragraph("�������ڣ�" + time, font));
                } catch (Exception ee) {
                    ee.printStackTrace();
                } finally {
                    document.close();
                }
            }
        });
    }

    //���������û���Ϣ��
    public void usertable() {
        try {
            //�����ֶ���
            List<String> list = Arrays.asList("����", "�û���", "����", "���ID", "�绰����", "�Ա�", "��������", "���", "��ע");
            ArrayList<String> ColumnLabels = new ArrayList<>(list);
            //��������colNum���ֶεı��
            PdfPTable table = new PdfPTable(colNum);
            int[] cellsWidth = {10, 10, 10, 10, 10, 10, 10, 10, 10};
            //�����ֶε�Ԫ�����Դ�С
            table.setWidths(cellsWidth);
            //���ñ��ռ�ĵ��Ĵ�С����
            table.setWidthPercentage(90);
            table.getDefaultCell().setPadding(padding);
            table.getDefaultCell().setBorderWidth(spacing);
            //���õ�Ԫ���ı�����
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            //����ֶ�
            for (int i = 0; i < colNum; i++) {
                table.getDefaultCell().setBackgroundColor(BaseColor.GRAY);
                //��ȡ�ֶ���
                table.addCell(new Paragraph(ColumnLabels.get(i), font));
            }
            table.setHeaderRows(1);//��ͷ���ý���,��ʾ��һ�����ڱ�ͷ

            table.getDefaultCell().setBorderWidth(1);
            //��ȡ�����û���Ϣ
            Cookies req = new Cookies();
            req.setOrder(10);
            Cookies resp3 = Main.request(MY, req);
            //����
            if (resp3 != null) {
                ArrayList<Cookies> resultlist = resp3.getList();
                for (Cookies cookies : resultlist) {
                    //���ñ�����ɫ
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
