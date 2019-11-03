package homework01.h2_reflection_and_MyBatis.model;
public class MethodInfo{
    int methodId;
    int classId;
    String methodName;
    String returnType;
    
    public int getMethodId(){ return methodId; }
    public void setMethodId(int methodId){ this.methodId = methodId; }
    public int getClassId(){ return classId; }
    public void setClassId(int classId){ this.classId = classId; }
    public String getMethodName(){ return methodName; }
    public void setMethodName(String methodName){ this.methodName = methodName; }
    public String getReturnType(){ return returnType; }
    public void setReturnType(String returnType){ this.returnType = returnType; }
}
