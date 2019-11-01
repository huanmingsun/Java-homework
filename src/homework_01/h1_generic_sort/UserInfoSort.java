package homework_01.h1_generic_sort;
public class UserInfoSort extends AbstractSort<UserInfo>{
    @Override
    protected int compare(UserInfo o1, UserInfo o2){
        return o1.getUserId()-o2.getUserId();
    }
}
