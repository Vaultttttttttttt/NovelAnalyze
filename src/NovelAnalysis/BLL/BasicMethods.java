package NovelAnalysis.BLL;

import NovelAnalysis.Entity.Person;

import java.util.Map;

import static NovelAnalysis.BLL.Methods3.quickSortByContact;

public class BasicMethods {
    //寻找人物索引
    public static int getPos(Person[] person, String name){
        int pos=-1;
        for(int i=0;i<10;++i){
            if(person[i].name.contains(name)){
                pos=i;
                break;
            }
        }
        return pos;
    }
    //初始化人物
    public static void SetPerson(Person[] person){
        person[0].name.add("瓦格雷夫");
        person[0].name.add("劳伦斯");

        person[1].name.add("维拉");
        person[1].name.add("克莱索恩");
        person[1].name.add("维拉· 克莱索恩");
        person[1].flag=true;

        person[2].name.add("隆巴德");
        person[2].name.add("菲利普");
        person[2].name.add("菲利普·隆巴德");
        person[2].flag=true;

        person[3].name.add("布伦特");
        person[3].name.add("埃米莉");
        person[3].name.add("埃米莉·布伦特");
        person[3].flag=true;

        person[4].name.add("麦克阿瑟");

        person[5].name.add("阿姆斯特朗");

        person[6].name.add("马斯顿");
        person[6].name.add("安东尼");
        person[6].name.add("安东尼·马斯顿");
        person[6].flag=true;

        person[7].name.add("布洛尔");

        person[8].name.add("弗雷德");
        person[8].name.add("纳拉科特");
        person[8].name.add("弗雷德·纳拉科特");
        person[8].flag=true;

        person[9].name.add("欧文");

        //初始化紧密程度
        for(Person people1:person){
            for(Person people2:person){
                if(people1==people2) continue;
                people1.contact.put(people2,0.0);
            }
        }
    }
    //消除空格
    public static String prim(String s){
        char[] arr=s.toCharArray();
        StringBuilder sb=new StringBuilder();
        for(char ch:arr){
            if(ch!=' '){
                sb.append(ch);
            }
        }
        return sb.toString();
    }
    //设置每个人的映射数组
    public static void set(Person[] person){
        for(Person people:person) {
            int n=0;
            for (Map.Entry<Person, Double> entry : people.contact.entrySet()) {
                people.relation[n] = entry.getKey();
                people.connection[n] = entry.getValue();
                n++;
            }
            quickSortByContact(people.relation,people.connection,0,8);
        }
    }
    //kmp算法
    public static int strStr(String string1,String string2){
        if(string1==null || string2==null || string1.length()==1 || string1.length()<string2.length()){
            return -1;
        }
        char[] str1=string1.toCharArray();
        char[] str2=string2.toCharArray();
        int i1=0;
        int i2=0;
        int[] next=getNextArray(str2);
        while(i1<str1.length && i2<str2.length){
            if(str1[i1]==str2[i2]){
                i1++;
                i2++;
            }else if(next[i2]==-1){
                i1++;
            }else{
                i2=next[i2];
            }
        }
        return i2==str2.length?i1-i2:-1;
    }
    public static int[] getNextArray(char[] ms){
        if(ms.length==1){
            return new int[]{-1};
        }
        int[] next=new int[ms.length];
        next[0]=-1;
        next[1]=0;
        int i=2;
        int cn=0;
        while(i<ms.length){
            if(ms[i-1]==ms[cn]){
                next[i++]=++cn;
            }else if(cn==0){
                next[i++]=0;
            }else{
                cn=next[cn];
            }
        }
        return next;
    }
}
