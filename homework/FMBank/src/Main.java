import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * ���ࣺ����ͻ��˵�����
 * main������ʵ����һ����¼����
 */
public class Main {
    private static final String URL = "123.60.22.9";//����������
    private static final int PORT = 8000;//�˿ں�

    public static void main(String[] args) {
        Login login = new Login();
        login.init();
    }

    /**
     * ���󷽷�����������˷������󣬲������˽�����Ϣ����
     */
    public static Cookies request(JFrame jFrame, Cookies cookies) {
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in;
        Cookies resp = null;
        System.out.println("�������ӷ�����");
        try {
            //���ӷ�����
            socket = new Socket(URL, PORT);
            //�����˷�������
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(cookies);
            //�ӷ���˽��ܷ��ص���Ϣ
            in = new ObjectInputStream(socket.getInputStream());
            resp = (Cookies) in.readObject();

        } catch (ConnectException e) {
            JOptionPane.showMessageDialog(jFrame, "�������Ӵ���", "��Ϣ", 0);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();

        } finally {
            //�ر���Դ
            try {
                if (out != null) {
                    out.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resp;
        }
    }

}
