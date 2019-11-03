package homework01.h2_reflection_and_MyBatis;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtils{
    private static final String            FILE_CONFIG = "mybatis-config.xml";
    private static       SqlSessionFactory sqlSessionFactory;
    private MyBatisUtils(){}
    static{
        InputStream inputStream;
        try{
            inputStream = Resources.getResourceAsStream(FILE_CONFIG);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    
    //获取会话的静态方法
    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();
    }
    
    public static SqlSession getSqlSessionAutoCommit(){
        return sqlSessionFactory.openSession(true);
    }
}
