package homework01.h2_reflection_and_MyBatis.model;
public class ParamInfo{
    int paramId;
    int methodId;
    int paramIndex;
    String paramType;
    
    public int getParamId(){ return paramId; }
    public void setParamId(int paramId){ this.paramId = paramId; }
    public int getMethodId(){ return methodId; }
    public void setMethodId(int methodId){ this.methodId = methodId; }
    public int getParamIndex(){ return paramIndex; }
    public void setParamIndex(int paramIndex){ this.paramIndex = paramIndex; }
    public String getParamType(){ return paramType; }
    public void setParamType(String paramType){ this.paramType = paramType; }
}
