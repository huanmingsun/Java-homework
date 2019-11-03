package homework01.h2_reflection_and_MyBatis;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import homework01.h2_reflection_and_MyBatis.mapper.ClassInfoMapper;
import homework01.h2_reflection_and_MyBatis.mapper.MethodInfoMapper;
import homework01.h2_reflection_and_MyBatis.mapper.ParamInfoMapper;
import homework01.h2_reflection_and_MyBatis.model.ClassInfo;
import homework01.h2_reflection_and_MyBatis.model.MethodInfo;
import homework01.h2_reflection_and_MyBatis.model.ParamInfo;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Scanner;

public class Test{
    public static void main(String[] args) throws IOException{
        //从终端获取类名
        String classNameFromInput = new Scanner(System.in).next();
        try{
            Class classFromInput = Class.forName(classNameFromInput);
            showMethods(classFromInput);
            
            System.out.println("\n正在添加至数据库。。。");
            addToClassInfo(classFromInput);
            Method[] methods = classFromInput.getMethods();
            
            SqlSession       session          = MyBatisUtils.getSqlSessionAutoCommit();
            ClassInfoMapper  classInfoMapper  = session.getMapper(ClassInfoMapper.class);
            MethodInfoMapper methodInfoMapper = session.getMapper(MethodInfoMapper.class);
            
            int classId = classInfoMapper.selectClassIdByClassName(classFromInput.getName());
            for(Method method : methods){
                addToMethodInfo(classId, method);
                addToParamInfo(methodInfoMapper.selectMaxMethodId(), method.getParameters());
            }
            session.close();
            System.out.println("添加完毕！");
        } catch(ClassNotFoundException e){
            System.out.println("装载失败！");
            e.printStackTrace();
        }
        
        exportToJSON();
    }
    
    private static void showMethods(Class classFromInput){
        Method[] methods = classFromInput.getMethods();
        System.out.println(classFromInput.getName() + " 装载成功，所含方法如下：\n");
        for(Method method : methods){ System.out.println(method);}
    }
    private static void addToClassInfo(Class cls){
        SqlSession      session         = MyBatisUtils.getSqlSessionAutoCommit();
        ClassInfoMapper classInfoMapper = session.getMapper(ClassInfoMapper.class);
        classInfoMapper.insertClassName(cls.getName());
        session.close();
    }
    private static void addToMethodInfo(int classId, Method method){
        MethodInfo       methodInfo       = new MethodInfo();
        SqlSession       session          = MyBatisUtils.getSqlSessionAutoCommit();
        MethodInfoMapper methodInfoMapper = session.getMapper(MethodInfoMapper.class);
        
        methodInfo.setClassId(classId);
        methodInfo.setMethodName(method.getName());
        methodInfo.setReturnType(method.getReturnType().toString());
        methodInfoMapper.insertMethodInfo(methodInfo);
        
        session.close();
    }
    private static void addToParamInfo(int methodId, Parameter[] parameters){
        ParamInfo       paramInfo       = new ParamInfo();
        SqlSession      session         = MyBatisUtils.getSqlSessionAutoCommit();
        ParamInfoMapper paramInfoMapper = session.getMapper(ParamInfoMapper.class);
        
        for(int i = 0, count = 1; i<parameters.length; i++, count++){
            Parameter parameter = parameters[i];
            paramInfo.setMethodId(methodId);
            paramInfo.setParamIndex(count);
            paramInfo.setParamType(parameter.getType().toString());
            paramInfoMapper.insertParamInfo(paramInfo);
        }
        session.close();
    }
    
    private static void exportToJSON(){
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Gson gson = builder.create();
        
        SqlSession      session         = MyBatisUtils.getSqlSession();
        ClassInfoMapper classInfoMapper = session.getMapper(ClassInfoMapper.class);
        
        ClassInfo[] classInfos = classInfoMapper.findAllClassInfo();
        
        String string = gson.toJson(classInfos[0]);
        System.out.println(string);
    }
    private static void exportToXML(){}
}
