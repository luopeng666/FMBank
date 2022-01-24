import java.io.Serializable;
import java.util.ArrayList;

/**
 * 信息类：作为客户端与服务器端的信息传递类
 */
public class Cookies implements Serializable {
    private int order;//命令
    private String cardID;//卡号
    private String userName;//用户名
    private String passWord;//密码
    private String userID;//身份id
    private String phone;//手机号码
    private char sex;//性别
    private String birthday;//出生日期
    private double money;//余额
    private String memo;//备注
    private boolean rank;//等级（管理员 or 普通用户）
    private ArrayList<Cookies> list;//cookies列表

    //登录用
    public Cookies(String userID, String passWord, boolean rank) {
        this.userID = userID;
        this.passWord = passWord;
        this.rank = rank;
    }

    //注册用
    public Cookies(int order, String userName, String userId, String phone, char sex, String birthday, String passWord) {
        this.order = order;
        this.userName = userName;
        this.userID = userId;
        this.phone = phone;
        this.sex = sex;
        this.birthday = birthday;
        this.passWord = passWord;
    }

    //回复登录、注册信息
    public Cookies(String memo) {
        this.memo = memo;
    }

    //发送用户操作用
    public Cookies(int order, String userID) {
        this.order = order;
        this.userID = userID;
    }

    //查询用户个人信息
    public Cookies(String userName, String cardID, String userId, String phone, char sex, String birthday, double money, String memo) {
        this.userName = userName;
        this.cardID = cardID;
        this.userID = userId;
        this.phone = phone;
        this.sex = sex;
        this.birthday = birthday;
        this.money = money;
        this.memo = memo;
    }

    //查询余额用
    public Cookies(Double money) {
        this.money = money;
    }

    //存、取款用
    public Cookies(int order, String userID, Double money) {
        this.order = order;
        this.userID = userID;
        this.money = money;
    }

    //转账用
    public Cookies(int order, String userID, String cardID, Double money) {
        this.order = order;
        this.userID = userID;
        this.cardID = cardID;
        this.money = money;
    }

    //修改用户个人信息用
    public Cookies(int order, String userID, String userName, String phone, char sex, String birthday) {
        this.order = order;
        this.userID = userID;
        this.userName = userName;
        this.phone = phone;
        this.sex = sex;
        this.birthday = birthday;

    }

    //空参构造函数
    public Cookies() {
    }

    //Getter AND Setter
    public int getOrder() {
        return order;
    }

    public Cookies setOrder(int order) {
        this.order = order;
        return null;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public boolean isRank() {
        return rank;
    }

    public void setRank(boolean rank) {
        this.rank = rank;
    }

    public ArrayList<Cookies> getList() {
        return list;
    }

    public void setList(ArrayList<Cookies> list) {
        this.list = list;
    }
}
