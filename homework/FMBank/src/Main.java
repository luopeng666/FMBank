import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 主类：负责客户端的启动
 * main函数：实例化一个登录界面
 */
public class Main {
    private static final String URL = "123.60.22.9";//服务器域名
    private static final int PORT = 8000;//端口号

    public static void main(String[] args) {
        Login login = new Login();
        login.init();
    }

    /**
     * 请求方法：向服务器端发送请求，并与服务端进行信息交互
     */
    public static Cookies request(JFrame jFrame, Cookies cookies) {
        Socket socket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in;
        Cookies resp = null;
        System.out.println("正在连接服务器");
        try {
            //连接服务器
            socket = new Socket(URL, PORT);
            //向服务端发送请求
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(cookies);
            //从服务端接受返回的信息
            in = new ObjectInputStream(socket.getInputStream());
            resp = (Cookies) in.readObject();

        } catch (ConnectException e) {
            JOptionPane.showMessageDialog(jFrame, "网络连接错误", "信息", 0);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();

        } finally {
            //关闭资源
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
