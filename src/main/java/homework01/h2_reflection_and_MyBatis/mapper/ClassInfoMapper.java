package homework01.h2_reflection_and_MyBatis.mapper;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface ClassInfoMapper{
    int selectClassIdByClassName(String ClassName);
    void insertClassName(String className);
}
