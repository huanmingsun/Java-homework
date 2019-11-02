package homework01.h1_generic_sort;
import java.util.Date;
import java.util.Scanner;
public class Test{
    public static void main(String[] args){
        System.out.print("多少个 Integers 排序？");
        testResultOfIntegersSorted(new Scanner(System.in).nextInt());
        
        System.out.println("------------------------------------------------");
        
        System.out.print("多少个 UserInfo 排序？");
        testResultOfUserInfosSorted(new Scanner(System.in).nextInt());
    }
    
    private static UserInfo[] createRandomUserInfos(int size){
        UserInfo[] userInfos = new UserInfo[size];
        for(int i = 0; i<userInfos.length; i++){
            userInfos[i] = new UserInfo();
            userInfos[i].setUserId(userInfos[i].hashCode());
            userInfos[i].setUserSex(Math.random()>0.5 ? "男" : "女");
            userInfos[i].setUserName(userInfos[i].getUserId() + "号" + userInfos[i].getUserSex());
            userInfos[i].setBirthday(new Date());
        }
        return userInfos;
    }
    private static void show(Integer[] integers){
        for(Integer integer : integers){
            System.out.println(integer);
        }
    }
    private static void show(UserInfo[] userInfos){
        for(UserInfo userInfo : userInfos){
            System.out.println(userInfo.getUserName());
        }
    }
    private static void testResultOfIntegersSorted(int size){
        AbstractSort<Integer> integerAbstractSort = new IntegerSort();
        Integer[]             integers            = new Integer[size];
        for(int i = 0; i<size; i++){
            integers[i] = ((int)(Math.random()*2*size));
        }
        System.out.println("Integers 排序前：");
        show(integers);
        integerAbstractSort.sort(integers);
        System.out.println("Integers 排序后：");
        show(integers);
    }
    private static void testResultOfUserInfosSorted(int size){
        AbstractSort<UserInfo> userInfoAbstractSort = new UserInfoSort();
        UserInfo[]             userInfos            = createRandomUserInfos(size);
        System.out.println("UserInfos 排序前：");
        show(userInfos);
        userInfoAbstractSort.sort(userInfos);
        System.out.println("\nUserInfos 排序后：");
        show(userInfos);
    }
}
