
import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 数据库通信类：用于进行服务器端与MySQL数据库之间的信息交互
 */
public class DBsolver {
    //定义连接数据库所需要的对象
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private Connection conn = null;

    //获得数据库的连接
    private void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver"); //加载mysq驱动
        } catch (ClassNotFoundException e) {
            System.out.println("驱动加载错误");
            e.printStackTrace();//打印出错详细信息
        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fmbank", "root", "lp666");
        } catch (SQLException e) {
            System.out.println("数据库链接错误");
            e.printStackTrace();
        }
    }

    //无参构造函数
    public DBsolver() {
        this.init();
    }

    /**
     * 登录
     */
    public Cookies login(String userID) {
        Cookies cookies = new Cookies();
        try {
            ps = conn.prepareStatement("select userpassword , memo from userinfo where userid = ? ");
            ps.setString(1, userID);
            rs = ps.executeQuery();
            if (rs.next()) {
                cookies.setPassWord(rs.getString(1));
                cookies.setMemo(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cookies;
    }

    /**
     * 开户
     */
    public boolean AddAccount(Cookies user) {
        boolean b = true;
        try {
            ps = conn.prepareStatement("insert into Userinfo(cardid,username,userpassword,userid,phonenum,sex,birthday,money,memo,registertime) values(?,?,?,?,?,?,?,2000,?,?)");
            ps.setInt(1, 0);
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPassWord());
            ps.setString(4, user.getUserID());
            ps.setString(5, user.getPhone());
            ps.setString(6, String.valueOf(user.getSex()));
            ps.setString(7, user.getBirthday());
            ps.setString(8, user.getMemo());
            //获取系统时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String current_time = sdf.format(new Date());
            String cur_year = current_time.substring(0, 4);
            ps.setString(9, cur_year);
            if (ps.executeUpdate() != 1) {    //执行sql语句
                b = false;
            }
        } catch (SQLException e) {
            b = false;
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 销户
     */
    public boolean delAccount(String userID) {
        boolean b = true;
        try {
            ps = conn.prepareStatement("delete from userinfo where userid = ?");
            ps.setString(1, userID);
            if (ps.executeUpdate() != 1) {    //执行sql语句
                b = false;
            }
        } catch (SQLException e) {
            b = false;
            e.printStackTrace();
        }
        return b;
    }


    /**
     * 查询账户信息
     */
    public Cookies QueryAll(String userID) {
        Cookies user = null;
        try {
            ps = conn.prepareStatement("select * from userinfo where userid = ? ");
            ps.setString(1, userID);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new Cookies();
                user.setCardID(rs.getString(1));
                user.setUserName(rs.getString(2));
                user.setPassWord(rs.getString(3));
                user.setUserID(rs.getString(4));
                user.setPhone(rs.getString(5));
                user.setSex(rs.getString(6).charAt(0));
                user.setBirthday(rs.getString(7));
                user.setMoney(rs.getDouble(8));
                user.setMemo(rs.getString(9));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 查询账户余额
     */
    public double Querymoney(String userID) {
        double money = -1;
        try {
            ps = conn.prepareStatement("select money from userinfo where userid = ? ");
            ps.setString(1, userID);
            rs = ps.executeQuery();
            if (rs.next()) {
                money = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return money;
    }

    /**
     * 取钱
     */
    public boolean withdrawMoney(String userID, double getmoney) {
        try {
            double new_money = Querymoney(userID) - getmoney;
            ps = conn.prepareStatement("update userinfo set money = ? where userid = ?");
            ps.setDouble(1, new_money);
            ps.setString(2, userID);
            if (ps.executeUpdate() != 1) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 存钱
     */
    public boolean depositMoney(String userID, double money) {
        try {
            double new_money = Querymoney(userID) + money;
            ps = conn.prepareStatement("update userinfo set money = ? where userid = ?");
            ps.setDouble(1, new_money);
            ps.setString(2, userID);
            if (ps.executeUpdate() != 1) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 转账
     */
    public boolean transferMoney(String userID, String target_cardID, double money) {
        try {
            //更新转账方余额
            double user_new_money = Querymoney(userID) - money;
            ps = conn.prepareStatement("update userinfo set money= ? where userid = ?");
            ps.setDouble(1, user_new_money);
            ps.setString(2, userID);
            if (ps.executeUpdate() != 1) {
                return false;
            }
            //查询接收方余额
            ps = conn.prepareStatement("select money from userinfo where cardid = ? ");
            ps.setInt(1, Integer.parseInt(target_cardID));
            rs = ps.executeQuery();
            double target_total_money = -1;
            if (rs.next()) {
                target_total_money = rs.getDouble(1);
            } else {
                return false;
            }
            //更新接收方余额
            double target_new_money = target_total_money + money;
            ps = conn.prepareStatement("update userinfo set money= ? where cardid = ?");
            ps.setDouble(1, target_new_money);
            ps.setString(2, target_cardID);
            if (ps.executeUpdate() != 1) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 修改账户信息
     */
    public boolean reviseUserInf(String userID, String userName, String phone, String birth, char sex) {
        boolean b = true;
        //修改用户名
        try {
            ps = conn.prepareStatement("update userinfo set username= ? where userid = ?");
            ps.setString(1, userName);
            ps.setString(2, userID);
            if (ps.executeUpdate() != 1)
                b = false;
        } catch (SQLException e) {
            b = false;
            e.printStackTrace();
        }

        //修改电话
        try {
            ps = conn.prepareStatement("update userinfo set phonenum = ? where userid = ?");
            ps.setString(1, phone);
            ps.setString(2, userID);
            if (ps.executeUpdate() != 1)
                b = false;
        } catch (SQLException e) {
            b = false;
            e.printStackTrace();
        }
        //修改性别
        try {
            ps = conn.prepareStatement("update userinfo set sex = ? where userid = ?");
            ps.setString(1, sex + "");
            ps.setString(2, userID);
            if (ps.executeUpdate() != 1)
                b = false;
        } catch (SQLException e) {
            b = false;
            e.printStackTrace();
        }
        //修改出生日期
        try {
            ps = conn.prepareStatement("update userinfo set birthday = ? where userid = ?");
            ps.setString(1, birth);
            ps.setString(2, userID);
            if (ps.executeUpdate() != 1)
                b = false;
        } catch (SQLException e) {
            b = false;
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 修改密码
     */
    public boolean revisePW(String userID, String new_pw) {
        boolean b = true;
        try {
            ps = conn.prepareStatement("update userinfo set userpassword= ? where userid = ?");
            ps.setString(1, new_pw);
            ps.setString(2, userID);
            if (ps.executeUpdate() != 1)
                b = false;
        } catch (SQLException e) {
            b = false;
            e.printStackTrace();
        }
        return b;
    }

    /**
     * 获取所有用户信息
     */
    public ArrayList<Cookies> queryAll() {
        ArrayList<Cookies> list = new ArrayList<Cookies>(0);
        try {
            String sql = "select * from userinfo";
            //执行查询语句
            ps = conn.prepareStatement(sql);
            //获取结果集
            rs = ps.executeQuery();
            while (rs.next()) {
                Cookies user = new Cookies();
                user.setCardID(rs.getString(1));
                user.setUserName(rs.getString(2));
                user.setPassWord(rs.getString(3));
                user.setUserID(rs.getString(4));
                user.setPhone(rs.getString(5));
                user.setSex(rs.getString(6).charAt(0));
                user.setBirthday(rs.getString(7));
                user.setMoney(rs.getDouble(8));
                user.setMemo(rs.getString(9));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 生成年终报告
     */
    //1.总开户用户数
    public int totalUserNum() {
        int count = -1;
        try {
            ps = conn.prepareStatement("select count(*) from userinfo");
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    //2.总余额
    public double totalBalance() {
        double money = -1;
        try {
            ps = conn.prepareStatement("select sum(money) as totalbalance from userinfo");
            rs = ps.executeQuery();
            if (rs.next()) {
                money = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return money;
    }

    /**
     * 查询年龄达到70岁的用户数
     */
    public ArrayList<String> queryold() {
        ArrayList<String> list = new ArrayList<>();
        try {
            ps = conn.prepareStatement("select userid,birthday from userinfo");
            rs = ps.executeQuery();
            while (rs.next()) {
                String userid = rs.getString(1);
                String birth = rs.getString(2);
                int year = Integer.parseInt(birth.substring(0, 4));
                int month = Integer.parseInt(birth.substring(5, 7));
                int day = Integer.parseInt(birth.substring(8, 10));
                //获取系统时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String current_time = sdf.format(new Date());
                //判断注册人年龄是否达到70岁
                int cur_year = Integer.parseInt(current_time.substring(0, 4));
                int cur_month = Integer.parseInt(current_time.substring(5, 7));
                int cur_day = Integer.parseInt(current_time.substring(8, 10));
                if (!(cur_year - year < 70 || (cur_year - year == 70 && cur_month - month < 0) || (cur_year - year == 70 && cur_month - month == 0 && cur_day - day < 0))) {
                    list.add(userid);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取今年新增开户人数
     */
    public int queryNewUserNum() {
        int count = -1;
        try {
            //获取系统时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String current_time = sdf.format(new Date());
            String cur_year = current_time.substring(0, 4);
            String sql = "SELECT COUNT(registertime) FROM userinfo WHERE registertime=" + cur_year;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 关闭数据库资源
     */
    public void close() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


