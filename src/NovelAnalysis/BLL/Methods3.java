package NovelAnalysis.BLL;

import NovelAnalysis.Entity.Person;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static NovelAnalysis.BLL.BasicMethods.*;

public class Methods3 {
    //计算每个人的紧密程度
    public static void getContact(Person[] person) throws IOException {
        for(Person people:person){
            for(String prev_name:people.name){
                //根据数据源创建输入流对象
                BufferedReader br=new BufferedReader(new FileReader("C:\\Users\\wxj27\\Desktop\\无人生还.txt"));
                //读取并处理数据
                String line1 = br.readLine();
                if(line1!=null){
                    String line2=br.readLine();
                    while(line1!=null){
                        String prev_line1=prim(line1);
                        if(line2!=null) {
                            String prev_line2 = prim(line2);
                            String line = prev_line1 + prev_line2;
                            int pos1=strStr(line,prev_name);
                            while(pos1!=-1){
                                for(Person other_people:person){
                                    if(other_people.name.contains(prev_name)) continue;
                                    for(String other_name: other_people.name){
                                        int pos2=strStr(line,other_name);
                                        if(pos2!=-1 && Math.abs(pos2-pos1)<=20){
                                            double rate=100.0/Math.abs(pos2-pos1);
                                            rate = Double.parseDouble(String.format("%.1f", rate ));
                                            people.contact.put(other_people,Math.max(people.contact.getOrDefault(other_people,0.0),rate));
                                            other_people.contact.put(people,Math.max(other_people.contact.getOrDefault(people,0.0),rate));
                                        }
                                    }
                                }
                                line=line.substring(pos1+prev_name.length(),line.length());
                                pos1=strStr(line,prev_name);
                            }
                            line1=line2;
                            line2=br.readLine();
                        }else{
                            int pos1=strStr(prev_line1,prev_name);
                            while(pos1!=-1){
                                for(Person other_people:person){
                                    if(other_people.name.contains(prev_name)) continue;
                                    for(String other_name: other_people.name){
                                        int pos2=strStr(prev_line1,other_name);
                                        if(pos2!=-1 && Math.abs(pos2-pos1)<=20){
                                            double rate=100.0/Math.abs(pos2-pos1);
                                            rate = Double.parseDouble(String.format("%.1f", rate ));
                                            people.contact.put(other_people,Math.max(people.contact.getOrDefault(other_people,0.0),rate));
                                            other_people.contact.put(people,Math.max(other_people.contact.getOrDefault(other_people,0.0),rate));
                                        }
                                    }
                                }
                                prev_line1=prev_line1.substring(pos1+prev_name.length(),prev_line1.length());
                                pos1=strStr(prev_line1,prev_name);
                            }
                            line1=line2;
                        }
                    }
                }
            }
        }
    }
    //每个人的紧密程度排序
    public static void sortByContact(Person[] person,String name){
        int pos=getPos(person,name);
        int n=0;
        for(Map.Entry<Person,Double> entry:person[pos].contact.entrySet()){
            person[pos].relation[n]=entry.getKey();
            person[pos].connection[n]=entry.getValue();
            n++;
        }
        quickSortByContact(person[pos].relation,person[pos].connection,0,8);
    }
    //紧密程度的快速排序
    public static void quickSortByContact(Person[] relation,double[] contact,int left,int right){
        if(left>=right) return;
        int count_45=left;
        double sign_45=contact[left];
        while(left!=right){
            if(sign_45==contact[left] && sign_45>contact[right]){
                Person temp1=relation[left];
                relation[left]=relation[right];
                relation[right]=temp1;
                double temp2=contact[left];
                contact[left]=contact[right];
                contact[right]=temp2;
                left++;
            }else if(sign_45==contact[left] && sign_45<=contact[right]){
                right--;
            }else if(sign_45==contact[right] && sign_45<contact[left]){
                Person temp1=relation[right];
                relation[right]=relation[left];
                relation[left]=temp1;
                double temp2=contact[right];
                contact[right]=contact[left];
                contact[left]=temp2;
                right--;
            }else if(sign_45==contact[right] && sign_45>=contact[left]){
                left++;
            }
        }
        quickSortByContact(relation,contact,count_45,left-1);
        quickSortByContact(relation,contact,left+1,relation.length-1);
    }
    //查找关系最不密切和最密切的二人
    public static void find(Person[] person, List<String> max, List<String> min, double[] nums){
        //nums[0]为最大值，nums[1]为最小值
        for(Person people:person){
            if(people.connection[0]<nums[1]){
                min.clear();
                min.add(people.name.get(0)+" "+people.relation[0].name.get(0));
                nums[1]=people.connection[0];
            }
            if(people.connection[0]==nums[1]){
                if(!min.contains(people.name.get(0)+" "+people.relation[0].name.get(0)) && !min.contains(people.relation[0].name.get(0)+" "+people.name.get(0))){
                    min.add(people.name.get(0)+" "+people.relation[0].name.get(0));
                }
            }
            if(people.connection[8]>nums[0]){
                max.clear();
                max.add(people.name.get(0)+" "+people.relation[8].name.get(0));
                nums[0]=people.connection[8];
            }
            if(people.connection[8]==nums[0]){
                if(!max.contains(people.name.get(0)+" "+people.relation[8].name.get(0)) && !max.contains(people.relation[8].name.get(0)+" "+people.name.get(0))){
                    max.add(people.name.get(0)+" "+people.relation[8].name.get(0));
                }
            }
        }
    }
}
