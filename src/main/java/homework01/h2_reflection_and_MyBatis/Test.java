package homework01.h2_reflection_and_MyBatis;
import homework01.h2_reflection_and_MyBatis.mapper.ClassInfoMapper;
import homework01.h2_reflection_and_MyBatis.model.ClassInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
public class Test{
    public static void main(String[] args) throws IOException{
        InputStream       inputStream       = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession        session           = sqlSessionFactory.openSession();
        
        ClassInfoMapper mapper = session.getMapper(ClassInfoMapper.class);
        ClassInfo[]     objs   = mapper.findAll();
        for(ClassInfo obj : objs){
            System.out.println(obj.getClassId() + ":" + obj.getClassName());
        }
        session.close();
    }
}
