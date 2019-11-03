package homework01.h2_reflection_and_MyBatis.mapper;
import homework01.h2_reflection_and_MyBatis.model.MethodInfo;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface MethodInfoMapper{
    void insertMethodInfo(MethodInfo methodInfo);
    MethodInfo[] selectAllMethod();
    int selectMaxMethodId();
}
