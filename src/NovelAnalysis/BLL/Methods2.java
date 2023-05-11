package NovelAnalysis.BLL;

import NovelAnalysis.Entity.Person;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static NovelAnalysis.BLL.BasicMethods.prim;
import static NovelAnalysis.BLL.BasicMethods.strStr;

public class Methods2 {
    //计算篇幅跨度
    public static void getSpanOfSpace(Person[] person) throws IOException {
        for(Person people:person){
            for(String prev_name:people.name){
                int row=1;              //记录行数
                //根据数据源创建输入流对象
                BufferedReader br=new BufferedReader(new FileReader("C:\\Users\\wxj27\\Desktop\\无人生还.txt"));
                //读取并处理数据
                String line1 = br.readLine();
                if(line1!=null){
                    String line2=br.readLine();
                    int len_name=prev_name.length();
                    while(line1!=null){
                        String prev_line1=prim(line1);
                        if(line2!=null){
                            String prev_line2=prim(line2);
                            String prev_line=prev_line1+prev_line2;
                            String line=prev_line.substring(0,Math.min(prev_line1.length()+len_name-2,prev_line.length()));
                            if(strStr(line,prev_name)!=-1){
                                people.lastTime=Math.max(people.lastTime,row);
                                people.firstTime=Math.min(people.firstTime,row);
                            }
                            line1=line2;
                            line2= br.readLine();
                        }else{
                            if(strStr(prev_line1,prev_name)!=-1){
                                people.lastTime=Math.max(people.lastTime,row);
                                people.firstTime=Math.min(people.firstTime,row);
                            }
                            line1=line2;
                        }
                        row++;
                    }
                }
                br.close();
            }
            people.span=people.lastTime-people.firstTime;
        }
    }
    //篇幅跨度排序,这里选择快速排序
    public static void sortBySpan(Person[] person,String[] name,int left,int right){
        if(left>=right) return;
        int count_45=left;
        int sign_45=person[left].span;
        while(left!=right){
            if(sign_45==person[left].span && sign_45>person[right].span){
                Person temp1=person[left];
                person[left]=person[right];
                person[right]=temp1;
                String temp2=name[left];
                name[left]=name[right];
                name[right]=temp2;
                left++;
            }else if(sign_45==person[left].span && sign_45<=person[right].span){
                right--;
            }else if(sign_45==person[right].span && sign_45<person[left].span){
                Person temp1=person[right];
                person[right]=person[left];
                person[left]=temp1;
                String temp2=name[right];
                name[right]=name[left];
                name[left]=temp2;
                right--;
            }else if(sign_45==person[right].span && sign_45>=person[left].span){
                left++;
            }
        }
        sortBySpan(person,name,count_45,left-1);
        sortBySpan(person,name,left+1,person.length-1);
    }
}
