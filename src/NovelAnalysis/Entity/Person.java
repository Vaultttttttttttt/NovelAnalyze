package NovelAnalysis.Entity;

import java.util.*;

public class Person {
    //姓名，包括姓，名
    public List<String> name=new ArrayList<>();
    //来判断是否既有姓又有名
    public boolean flag;
    //出现次数
    public int times;
    //第一次出现的行
    public int firstTime;
    //最后一次出现的行
    public int lastTime;
    //篇幅跨度
    public int span;
    //交往关系
    public Map<Person,Double> contact=new HashMap<>();
    //哈希表对应的映射数组
    public Person[] relation=new Person[9];
    public double[] connection=new double[9];
    public Person(){
        firstTime=Integer.MAX_VALUE;
        lastTime=Integer.MIN_VALUE;
    }
}
