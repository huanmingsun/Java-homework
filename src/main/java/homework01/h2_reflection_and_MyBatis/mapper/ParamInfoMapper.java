package homework01.h2_reflection_and_MyBatis.mapper;
import homework01.h2_reflection_and_MyBatis.model.ParamInfo;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface ParamInfoMapper{
    void insertParamInfo(ParamInfo paramInfo);
}
