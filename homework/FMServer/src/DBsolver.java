
import javax.swing.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * ���ݿ�ͨ���ࣺ���ڽ��з���������MySQL���ݿ�֮�����Ϣ����
 */
public class DBsolver {
    //�����������ݿ�����Ҫ�Ķ���
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private Connection conn = null;

    //������ݿ������
    private void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver"); //����mysq����
        } catch (ClassNotFoundException e) {
            System.out.println("�������ش���");
            e.printStackTrace();//��ӡ������ϸ��Ϣ
        }
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fmbank", "root", "lp666");
        } catch (SQLException e) {
            System.out.println("���ݿ����Ӵ���");
            e.printStackTrace();
        }
    }

    //�޲ι��캯��
    public DBsolver() {
        this.init();
    }

    /**
     * ��¼
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
     * ����
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
            //��ȡϵͳʱ��
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String current_time = sdf.format(new Date());
            String cur_year = current_time.substring(0, 4);
            ps.setString(9, cur_year);
            if (ps.executeUpdate() != 1) {    //ִ��sql���
                b = false;
            }
        } catch (SQLException e) {
            b = false;
            e.printStackTrace();
        }
        return b;
    }

    /**
     * ����
     */
    public boolean delAccount(String userID) {
        boolean b = true;
        try {
            ps = conn.prepareStatement("delete from userinfo where userid = ?");
            ps.setString(1, userID);
            if (ps.executeUpdate() != 1) {    //ִ��sql���
                b = false;
            }
        } catch (SQLException e) {
            b = false;
            e.printStackTrace();
        }
        return b;
    }


    /**
     * ��ѯ�˻���Ϣ
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
     * ��ѯ�˻����
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
     * ȡǮ
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
     * ��Ǯ
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
     * ת��
     */
    public boolean transferMoney(String userID, String target_cardID, double money) {
        try {
            //����ת�˷����
            double user_new_money = Querymoney(userID) - money;
            ps = conn.prepareStatement("update userinfo set money= ? where userid = ?");
            ps.setDouble(1, user_new_money);
            ps.setString(2, userID);
            if (ps.executeUpdate() != 1) {
                return false;
            }
            //��ѯ���շ����
            ps = conn.prepareStatement("select money from userinfo where cardid = ? ");
            ps.setInt(1, Integer.parseInt(target_cardID));
            rs = ps.executeQuery();
            double target_total_money = -1;
            if (rs.next()) {
                target_total_money = rs.getDouble(1);
            } else {
                return false;
            }
            //���½��շ����
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
     * �޸��˻���Ϣ
     */
    public boolean reviseUserInf(String userID, String userName, String phone, String birth, char sex) {
        boolean b = true;
        //�޸��û���
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

        //�޸ĵ绰
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
        //�޸��Ա�
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
        //�޸ĳ�������
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
     * �޸�����
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
     * ��ȡ�����û���Ϣ
     */
    public ArrayList<Cookies> queryAll() {
        ArrayList<Cookies> list = new ArrayList<Cookies>(0);
        try {
            String sql = "select * from userinfo";
            //ִ�в�ѯ���
            ps = conn.prepareStatement(sql);
            //��ȡ�����
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
     * �������ձ���
     */
    //1.�ܿ����û���
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

    //2.�����
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
     * ��ѯ����ﵽ70����û���
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
                //��ȡϵͳʱ��
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String current_time = sdf.format(new Date());
                //�ж�ע���������Ƿ�ﵽ70��
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
     * ��ȡ����������������
     */
    public int queryNewUserNum() {
        int count = -1;
        try {
            //��ȡϵͳʱ��
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
     * �ر����ݿ���Դ
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


