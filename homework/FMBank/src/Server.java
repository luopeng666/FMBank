import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * 服务端:收到客户端请求后和数据库通信类交互获得信息并返回给客户端
 */
public class Server {
    private final Server MY = this;
    private static final int PORT = 8000;//端口号
    private static DBsolver dBsolver;//数据库通信类

    public static void main(String[] args) {

        ServerSocket serverSocket;
        dBsolver = new DBsolver();
        try {
            serverSocket = new ServerSocket(PORT);//开启服务
            System.out.println("等待响应》》》》》》》》》");
            new TaskThread().start();
            //成功获取一个请求就开辟一个线程去处理
            while (true) {
                Socket client = serverSocket.accept();
                //创建一个新的线程
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
                                case 0://登陆
                                    resp = login(cookies);
                                    break;
                                case 1://注册
                                    resp = openAccount(cookies);
                                    break;
                                case 2://获取用户信息
                                    resp = queryUserInf(cookies);
                                    break;
                                case 3://查询余额
                                    resp = queryMoney(cookies);
                                    break;
                                case 4://取款
                                    resp = withdraw(cookies);
                                    break;
                                case 5://存款
                                    resp = deposit(cookies);
                                    break;
                                case 6://转账
                                    resp = transfer(cookies);
                                    break;
                                case 7://修改个人信息
                                    resp = reviseUserInf(cookies);
                                    break;
                                case 8://修改密码
                                    resp = revisePW(cookies);
                                    break;
                                case 9://销户
                                    resp = delAccount(cookies);
                                    break;
                                case 10://查询所有用户信息
                                    resp = queryAll();
                                    break;
                                case 11://查询总余额
                                    resp = queryTotalBalance();
                                    break;
                                case 12://查询用户总数
                                    resp = queryTotalUserNum();
                                    break;
                                case 13://查询今年新增开户数
                                    resp = queryNewUserNum();
                                    break;
                                default:
                                    break;
                            }
                            out.writeObject(resp);
                            System.out.println("已回复" + client.getInetAddress());
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


    //0.登录
    private static Cookies login(Cookies cookies) {
        String userID = cookies.getUserID();
        Cookies resp = new Cookies();
        Cookies db_resp = dBsolver.login(userID);

        if (db_resp == null)
            return resp;
        if (cookies.isRank()) {
            if (db_resp.getMemo() != null && db_resp.getMemo().equals("管理员") && cookies.getPassWord().equals(db_resp.getPassWord())) {
                resp.setMemo("success");
                System.out.println("管理员");
            }

        } else {
            if ((db_resp.getMemo() == null || !db_resp.getMemo().equals("管理员")) && cookies.getPassWord().equals(db_resp.getPassWord()))
                resp.setMemo("success");
        }
        return resp;
    }

    //1.注册
    private static Cookies openAccount(Cookies cookies) {
        Cookies resp = new Cookies("");
        boolean b = dBsolver.AddAccount(cookies);
        if (b)
            resp.setMemo("success");
        return resp;
    }

    //2.查询用户个人信息
    private static Cookies queryUserInf(Cookies cookies) {
        String userID = cookies.getUserID();
        //请求数据库获取用户个人信息
        Cookies db_resp = dBsolver.QueryAll(userID);
        //返回用户个人信息
        return db_resp;
    }

    //3.查询余额
    private static Cookies queryMoney(Cookies cookies) {
        String userID = cookies.getUserID();
        //请求数据库获取余额信息
        double money = dBsolver.Querymoney(userID);
        //返回用户余额信息
        Cookies resp = new Cookies(money);
        return resp;
    }

    //4.取款
    private static Cookies withdraw(Cookies cookies) {
        Cookies resp = null;
        String userID = cookies.getUserID();
        double money = cookies.getMoney();
        //修改用户余额信息
        boolean b = dBsolver.withdrawMoney(userID, money);
        //返回结果
        if (b)
            resp = new Cookies("success");
        return resp;
    }

    //5.存款
    private static Cookies deposit(Cookies cookies) {

        String userID = cookies.getUserID();
        double money = cookies.getMoney();
        //修改用户余额信息
        boolean b = dBsolver.depositMoney(userID, money);
        //返回结果
        Cookies resp = new Cookies("");
        if (b)
            resp.setMemo("success");
        return resp;
    }

    //6.转账
    private static Cookies transfer(Cookies cookies) {
        String userID = cookies.getUserID();
        String target_cardID = cookies.getCardID();
        double money = cookies.getMoney();
        //转账
        boolean b = dBsolver.transferMoney(userID, target_cardID, money);
        //返回结果
        Cookies resp = null;
        if (b)
            resp = new Cookies("success");
        return resp;
    }

    //7.修改用户个人信息
    private static Cookies reviseUserInf(Cookies cookies) {
        String userID = cookies.getUserID();
        String userName = cookies.getUserName();
        String phone = cookies.getPhone();
        String birthday = cookies.getBirthday();
        char sex = cookies.getSex();
        //修改个人信息
        boolean b = dBsolver.reviseUserInf(userID, userName, phone, birthday, sex);
        //返回结果
        Cookies resp = new Cookies("");
        if (b)
            resp.setMemo("success");
        return resp;

    }

    //8.修改密码
    private static Cookies revisePW(Cookies cookies) {
        String userID = cookies.getUserID();
        String new_pw = cookies.getPassWord();
        //修改密码
        boolean b = dBsolver.revisePW(userID, new_pw);
        Cookies resp = null;
        if (b)
            resp = new Cookies("success");
        return resp;
    }

    //9。销户
    private static Cookies delAccount(Cookies cookies) {
        String userID = cookies.getUserID();
        //销户
        boolean b = dBsolver.delAccount(userID);
        Cookies resp = new Cookies("");
        if (b)
            resp.setMemo("success");
        return resp;
    }

    //查询所有用户信息
    private static Cookies queryAll() {
        ArrayList<Cookies> list = dBsolver.queryAll();
        Cookies resp = new Cookies();
        resp.setList(list);
        return resp;
    }

    //查询总余额
    private static Cookies queryTotalBalance() {
        double totalbalance = -1;
        totalbalance = dBsolver.totalBalance();
        Cookies resp = new Cookies();
        resp.setMoney(totalbalance);
        return resp;
    }

    //查询用户总数
    private static Cookies queryTotalUserNum() {
        int count = -1;
        count = dBsolver.totalUserNum();
        Cookies resp = new Cookies();
        resp.setMemo("" + count);
        return resp;
    }

    //查询今年新开户数
    private static Cookies queryNewUserNum() {
        int count = -1;
        count = dBsolver.queryNewUserNum();
        Cookies resp = new Cookies();
        resp.setMemo("" + count);
        return resp;
    }


    /**
     * 定时任务类（每天0点检查所有用户的年龄，并对满70岁的用户执行强制销户）
     */
    static class TaskThread extends Timer {
        private TaskThread timer = null;

        //时间间隔(一天)
        private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;

        public void start() {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0); //凌晨0点
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date date = calendar.getTime(); //第一次执行定时任务的时间
            //如果第一次执行定时任务的时间 小于当前的时间
            //此时要在 第一次执行定时任务的时间加一天，以便此任务在下个时间点执行。如果不加一天，任务会立即执行。
            if (date.before(new Date())) {
                date = this.addDay(date, 1);
            }
            timer = new TaskThread();
            System.out.println(calendar.getTime());
            //安排指定的任务在指定的时间开始进行重复的固定延迟执行。
            timer.schedule(new TimerTask() {
                @Override
                //检查所有用户的年龄，并对满70岁的用户执行强制销户
                public void run() {
                    for (String userID : dBsolver.queryold()) {
                        dBsolver.delAccount(userID);
                    }
                }
            }, date, PERIOD_DAY);
        }

        // 增加或减少天数
        private Date addDay(Date date, int num) {
            Calendar startDT = Calendar.getInstance();
            startDT.setTime(date);
            startDT.add(Calendar.DAY_OF_MONTH, num);
            return startDT.getTime();
        }
    }
}

