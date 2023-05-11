package NovelAnalysis.BLL;

import NovelAnalysis.Entity.Person;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static NovelAnalysis.BLL.BasicMethods.*;

public class Methods1 {
    //记录某个人物的出现次数
    public static Person getTimes(Person[] person,String name) throws IOException {
        int pos=getPos(person,name);
        if(person[pos].times!=0) return person[pos];
        for(int i=0;i<person[pos].name.size();++i){
            int flag=1;             //用来处理姓名重复的现象
            if(i==person[pos].name.size()-1 && person[pos].flag){
                flag=-1;
            }
            //根据数据源创建输入流对象
            BufferedReader br=new BufferedReader(new FileReader("C:\\Users\\wxj27\\Desktop\\无人生还.txt"));
            //读取并处理数据
            String line1 = br.readLine();
            if(line1!=null) {
                String line2 = br.readLine();
                String prev_name = person[pos].name.get(i);   //选取一个名字
                int len_name=prev_name.length();
                while (line1 != null) {
                    String prev_line1=prim(line1);
                    if(line2!=null){
                        String prev_line2=prim(line2);
                        String prev_line=prev_line1+prev_line2;
                        prev_line=prev_line.substring(0,Math.min(prev_line1.length()+len_name-2,prev_line.length()));
                        line1 = getTimesOneRow(person, pos, flag, line2, prev_name, len_name, prev_line);
                        line2= br.readLine();
                    }else{
                        line1 = getTimesOneRow(person, pos, flag, line2, prev_name, len_name, prev_line1);
                    }
                }
            }
            br.close();
        }
        return person[pos];
    }
    //用kmp算法统计一行里的出现次数
    public static String getTimesOneRow(Person[] person, int pos, int flag, String line2, String prev_name, int len_name, String prev_line) {
        String line1;
        int prev_pos=strStr(prev_line,prev_name);
        while(prev_pos!=-1){
            person[pos].times+=flag;
            prev_line=prev_line.substring(prev_pos+len_name,prev_line.length());
            prev_pos=strStr(prev_line,prev_name);
        }
        line1=line2;
        return line1;
    }
}
