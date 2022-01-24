import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * �����:�յ��ͻ������������ݿ�ͨ���ཻ�������Ϣ�����ظ��ͻ���
 */
public class Server {
    private final Server MY = this;
    private static final int PORT = 8000;//�˿ں�
    private static DBsolver dBsolver;//���ݿ�ͨ����

    public static void main(String[] args) {

        ServerSocket serverSocket;
        dBsolver = new DBsolver();
        try {
            serverSocket = new ServerSocket(PORT);//��������
            System.out.println("�ȴ���Ӧ������������������");
            new TaskThread().start();
            //�ɹ���ȡһ������Ϳ���һ���߳�ȥ����
            while (true) {
                Socket client = serverSocket.accept();
                //����һ���µ��߳�
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try (ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                             ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream())) {
                            System.out.println(client.getInetAddress());
                            Cookies resp = null;
                            Cookies cookies = (Cookies) in.readObject();
                            int order = cookies.getOrder();
                            switch (order) {
                                case 0://��½
                                    resp = login(cookies);
                                    break;
                                case 1://ע��
                                    resp = openAccount(cookies);
                                    break;
                                case 2://��ȡ�û���Ϣ
                                    resp = queryUserInf(cookies);
                                    break;
                                case 3://��ѯ���
                                    resp = queryMoney(cookies);
                                    break;
                                case 4://ȡ��
                                    resp = withdraw(cookies);
                                    break;
                                case 5://���
                                    resp = deposit(cookies);
                                    break;
                                case 6://ת��
                                    resp = transfer(cookies);
                                    break;
                                case 7://�޸ĸ�����Ϣ
                                    resp = reviseUserInf(cookies);
                                    break;
                                case 8://�޸�����
                                    resp = revisePW(cookies);
                                    break;
                                case 9://����
                                    resp = delAccount(cookies);
                                    break;
                                case 10://��ѯ�����û���Ϣ
                                    resp = queryAll();
                                    break;
                                case 11://��ѯ�����
                                    resp = queryTotalBalance();
                                    break;
                                case 12://��ѯ�û�����
                                    resp = queryTotalUserNum();
                                    break;
                                case 13://��ѯ��������������
                                    resp = queryNewUserNum();
                                    break;
                                default:
                                    break;
                            }
                            out.writeObject(resp);
                            System.out.println("�ѻظ�" + client.getInetAddress());
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    }

                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //0.��¼
    private static Cookies login(Cookies cookies) {
        String userID = cookies.getUserID();
        Cookies resp = new Cookies();
        Cookies db_resp = dBsolver.login(userID);

        if (db_resp == null)
            return resp;
        if (cookies.isRank()) {
            if (db_resp.getMemo() != null && db_resp.getMemo().equals("����Ա") && cookies.getPassWord().equals(db_resp.getPassWord())) {
                resp.setMemo("success");
                System.out.println("����Ա");
            }

        } else {
            if ((db_resp.getMemo() == null || !db_resp.getMemo().equals("����Ա")) && cookies.getPassWord().equals(db_resp.getPassWord()))
                resp.setMemo("success");
        }
        return resp;
    }

    //1.ע��
    private static Cookies openAccount(Cookies cookies) {
        Cookies resp = new Cookies("");
        boolean b = dBsolver.AddAccount(cookies);
        if (b)
            resp.setMemo("success");
        return resp;
    }

    //2.��ѯ�û�������Ϣ
    private static Cookies queryUserInf(Cookies cookies) {
        String userID = cookies.getUserID();
        //�������ݿ��ȡ�û�������Ϣ
        Cookies db_resp = dBsolver.QueryAll(userID);
        //�����û�������Ϣ
        return db_resp;
    }

    //3.��ѯ���
    private static Cookies queryMoney(Cookies cookies) {
        String userID = cookies.getUserID();
        //�������ݿ��ȡ�����Ϣ
        double money = dBsolver.Querymoney(userID);
        //�����û������Ϣ
        Cookies resp = new Cookies(money);
        return resp;
    }

    //4.ȡ��
    private static Cookies withdraw(Cookies cookies) {
        Cookies resp = null;
        String userID = cookies.getUserID();
        double money = cookies.getMoney();
        //�޸��û������Ϣ
        boolean b = dBsolver.withdrawMoney(userID, money);
        //���ؽ��
        if (b)
            resp = new Cookies("success");
        return resp;
    }

    //5.���
    private static Cookies deposit(Cookies cookies) {

        String userID = cookies.getUserID();
        double money = cookies.getMoney();
        //�޸��û������Ϣ
        boolean b = dBsolver.depositMoney(userID, money);
        //���ؽ��
        Cookies resp = new Cookies("");
        if (b)
            resp.setMemo("success");
        return resp;
    }

    //6.ת��
    private static Cookies transfer(Cookies cookies) {
        String userID = cookies.getUserID();
        String target_cardID = cookies.getCardID();
        double money = cookies.getMoney();
        //ת��
        boolean b = dBsolver.transferMoney(userID, target_cardID, money);
        //���ؽ��
        Cookies resp = null;
        if (b)
            resp = new Cookies("success");
        return resp;
    }

    //7.�޸��û�������Ϣ
    private static Cookies reviseUserInf(Cookies cookies) {
        String userID = cookies.getUserID();
        String userName = cookies.getUserName();
        String phone = cookies.getPhone();
        String birthday = cookies.getBirthday();
        char sex = cookies.getSex();
        //�޸ĸ�����Ϣ
        boolean b = dBsolver.reviseUserInf(userID, userName, phone, birthday, sex);
        //���ؽ��
        Cookies resp = new Cookies("");
        if (b)
            resp.setMemo("success");
        return resp;

    }

    //8.�޸�����
    private static Cookies revisePW(Cookies cookies) {
        String userID = cookies.getUserID();
        String new_pw = cookies.getPassWord();
        //�޸�����
        boolean b = dBsolver.revisePW(userID, new_pw);
        Cookies resp = null;
        if (b)
            resp = new Cookies("success");
        return resp;
    }

    //9������
    private static Cookies delAccount(Cookies cookies) {
        String userID = cookies.getUserID();
        //����
        boolean b = dBsolver.delAccount(userID);
        Cookies resp = new Cookies("");
        if (b)
            resp.setMemo("success");
        return resp;
    }

    //��ѯ�����û���Ϣ
    private static Cookies queryAll() {
        ArrayList<Cookies> list = dBsolver.queryAll();
        Cookies resp = new Cookies();
        resp.setList(list);
        return resp;
    }

    //��ѯ�����
    private static Cookies queryTotalBalance() {
        double totalbalance = -1;
        totalbalance = dBsolver.totalBalance();
        Cookies resp = new Cookies();
        resp.setMoney(totalbalance);
        return resp;
    }

    //��ѯ�û�����
    private static Cookies queryTotalUserNum() {
        int count = -1;
        count = dBsolver.totalUserNum();
        Cookies resp = new Cookies();
        resp.setMemo("" + count);
        return resp;
    }

    //��ѯ�����¿�����
    private static Cookies queryNewUserNum() {
        int count = -1;
        count = dBsolver.queryNewUserNum();
        Cookies resp = new Cookies();
        resp.setMemo("" + count);
        return resp;
    }


    /**
     * ��ʱ�����ࣨÿ��0���������û������䣬������70����û�ִ��ǿ��������
     */
    static class TaskThread extends Timer {
        private TaskThread timer = null;

        //ʱ����(һ��)
        private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;

        public void start() {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0); //�賿0��
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date date = calendar.getTime(); //��һ��ִ�ж�ʱ�����ʱ��
            //�����һ��ִ�ж�ʱ�����ʱ�� С�ڵ�ǰ��ʱ��
            //��ʱҪ�� ��һ��ִ�ж�ʱ�����ʱ���һ�죬�Ա���������¸�ʱ���ִ�С��������һ�죬���������ִ�С�
            if (date.before(new Date())) {
                date = this.addDay(date, 1);
            }
            timer = new TaskThread();
            System.out.println(calendar.getTime());
            //����ָ����������ָ����ʱ�俪ʼ�����ظ��Ĺ̶��ӳ�ִ�С�
            timer.schedule(new TimerTask() {
                @Override
                //��������û������䣬������70����û�ִ��ǿ������
                public void run() {
                    for (String userID : dBsolver.queryold()) {
                        dBsolver.delAccount(userID);
                    }
                }
            }, date, PERIOD_DAY);
        }

        // ���ӻ��������
        private Date addDay(Date date, int num) {
            Calendar startDT = Calendar.getInstance();
            startDT.setTime(date);
            startDT.add(Calendar.DAY_OF_MONTH, num);
            return startDT.getTime();
        }
    }
}

