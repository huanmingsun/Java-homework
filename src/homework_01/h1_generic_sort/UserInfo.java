package homework_01.h1_generic_sort;
import java.util.Date;
public class UserInfo{
    private Integer userId;
    private String  userName;
    private String  userSex;
    private Date    birthday;
    
    public UserInfo(){}
    public Integer getUserId(){ return userId; }
    public void setUserId(Integer userId){ this.userId = userId; }
    public String getUserName(){ return userName; }
    public void setUserName(String userName){ this.userName = userName; }
    public String getUserSex(){ return userSex; }
    public void setUserSex(String userSex){ this.userSex = userSex; }
    public Date getBirthday(){ return birthday; }
    public void setBirthday(Date birthday){ this.birthday = birthday; }
}
