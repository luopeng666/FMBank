import java.io.Serializable;
import java.util.ArrayList;

/**
 * ��Ϣ�ࣺ��Ϊ�ͻ�����������˵���Ϣ������
 */
public class Cookies implements Serializable {
    private int order;//����
    private String cardID;//����
    private String userName;//�û���
    private String passWord;//����
    private String userID;//���id
    private String phone;//�ֻ�����
    private char sex;//�Ա�
    private String birthday;//��������
    private double money;//���
    private String memo;//��ע
    private boolean rank;//�ȼ�������Ա or ��ͨ�û���
    private ArrayList<Cookies> list;//cookies�б�

    //��¼��
    public Cookies(String userID, String passWord, boolean rank) {
        this.userID = userID;
        this.passWord = passWord;
        this.rank = rank;
    }

    //ע����
    public Cookies(int order, String userName, String userId, String phone, char sex, String birthday, String passWord) {
        this.order = order;
        this.userName = userName;
        this.userID = userId;
        this.phone = phone;
        this.sex = sex;
        this.birthday = birthday;
        this.passWord = passWord;
    }

    //�ظ���¼��ע����Ϣ
    public Cookies(String memo) {
        this.memo = memo;
    }

    //�����û�������
    public Cookies(int order, String userID) {
        this.order = order;
        this.userID = userID;
    }

    //��ѯ�û�������Ϣ
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

    //��ѯ�����
    public Cookies(Double money) {
        this.money = money;
    }

    //�桢ȡ����
    public Cookies(int order, String userID, Double money) {
        this.order = order;
        this.userID = userID;
        this.money = money;
    }

    //ת����
    public Cookies(int order, String userID, String cardID, Double money) {
        this.order = order;
        this.userID = userID;
        this.cardID = cardID;
        this.money = money;
    }

    //�޸��û�������Ϣ��
    public Cookies(int order, String userID, String userName, String phone, char sex, String birthday) {
        this.order = order;
        this.userID = userID;
        this.userName = userName;
        this.phone = phone;
        this.sex = sex;
        this.birthday = birthday;

    }

    //�ղι��캯��
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
