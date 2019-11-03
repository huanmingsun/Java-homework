package homework01.h2_reflection_and_MyBatis;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import homework01.h2_reflection_and_MyBatis.mapper.ClassInfoMapper;
import homework01.h2_reflection_and_MyBatis.mapper.MethodInfoMapper;
import homework01.h2_reflection_and_MyBatis.mapper.ParamInfoMapper;
import homework01.h2_reflection_and_MyBatis.model.ClassInfo;
import homework01.h2_reflection_and_MyBatis.model.MethodInfo;
import homework01.h2_reflection_and_MyBatis.model.ParamInfo;
import org.apache.ibatis.session.SqlSession;

import java.io.BufferedWriter;
import java.io.FileWriter;
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
            System.out.println("添加完毕！\n");
        } catch(ClassNotFoundException e){
            System.out.println("装载失败！");
            e.printStackTrace();
        }
        
        System.out.println("导出 json 文件中。。。");
        exportToJSON();
        System.out.println("\n导出 xml 文件中。。。");
        exportToXML();
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
        GsonBuilder   builder = new GsonBuilder();
        Gson          gson    = builder.create();
        StringBuilder string  = new StringBuilder();
        string.append("[");
        
        SqlSession       session          = MyBatisUtils.getSqlSession();
        ClassInfoMapper  classInfoMapper  = session.getMapper(ClassInfoMapper.class);
        MethodInfoMapper methodInfoMapper = session.getMapper(MethodInfoMapper.class);
        ParamInfoMapper  paramInfoMapper  = session.getMapper(ParamInfoMapper.class);
        
        ClassInfo[]  classInfos  = classInfoMapper.selectAllClassInfo();
        MethodInfo[] methodInfos = methodInfoMapper.selectAllMethodInfo();
        ParamInfo[]  paramInfos  = paramInfoMapper.selectAllParamInfo();
        
        for(ClassInfo classInfo : classInfos){ string.append(gson.toJson(classInfo)).append(","); }
        for(MethodInfo methodInfo : methodInfos){ string.append(gson.toJson(methodInfo)).append(","); }
        for(ParamInfo paramInfo : paramInfos){ string.append(gson.toJson(paramInfo)).append(","); }
        string.deleteCharAt(string.lastIndexOf(",")).append("]");
        
        writeFile(string,"json");

        session.close();
    }
    private static void exportToXML(){
        StringBuilder string = new StringBuilder();
        string.append("<xml>");
        
        SqlSession       session          = MyBatisUtils.getSqlSession();
        XStream          xStream          = new XStream();
        ClassInfoMapper  classInfoMapper  = session.getMapper(ClassInfoMapper.class);
        MethodInfoMapper methodInfoMapper = session.getMapper(MethodInfoMapper.class);
        ParamInfoMapper  paramInfoMapper  = session.getMapper(ParamInfoMapper.class);
        
        ClassInfo[]  classInfos  = classInfoMapper.selectAllClassInfo();
        MethodInfo[] methodInfos = methodInfoMapper.selectAllMethodInfo();
        ParamInfo[]  paramInfos  = paramInfoMapper.selectAllParamInfo();
        
        string.append(xStream.toXML(classInfos)).append(xStream.toXML(methodInfos)).append(xStream.toXML(paramInfos));
        string.append("</xml>");
        
        writeFile(string, "xml");
        
        session.close();
    }
    
    private static void writeFile(StringBuilder content, String fileExtension){
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter("out." + fileExtension));
            out.write(content.toString());
            out.close();
            System.out.println("成功导出至 out." + fileExtension);
        } catch(IOException e){
            System.out.println(fileExtension + " 导出失败");
            e.printStackTrace();
        }
    }
}
