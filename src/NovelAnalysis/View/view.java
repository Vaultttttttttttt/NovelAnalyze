package NovelAnalysis.View;

import NovelAnalysis.BLL.BasicMethods;
import NovelAnalysis.BLL.Methods1;
import NovelAnalysis.BLL.Methods2;
import NovelAnalysis.BLL.Methods3;
import NovelAnalysis.Entity.Person;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class view {
    public view(){
        JFrame jFrame=new JFrame("无人生还");
        JLabel jLabel=new JLabel("请选择姓名：");

        //映射:name----person
        String[] name={"瓦格雷夫","维拉","隆巴德","布伦特","麦克阿瑟","阿姆斯特朗","马斯顿","布洛尔","弗雷德","欧文"};
        Person p1=new Person();
        Person p2=new Person();
        Person p3=new Person();
        Person p4=new Person();
        Person p5=new Person();
        Person p6=new Person();
        Person p7=new Person();
        Person p8=new Person();
        Person p9=new Person();
        Person p10=new Person();
        Person[] person={p1,p2,p3,p4,p5,p6,p7,p8,p9,p10};
        //设置基本信息
        BasicMethods.SetPerson(person);

        JComboBox<String> jComboBox=new JComboBox<>(name);
        JButton jb1=new JButton("显示出现次数");
        JButton jb2=new JButton("篇幅跨度排序");
        JButton jb3=new JButton("紧密和不紧密");
        JButton jb4=new JButton("紧密程度排序");
        JTextArea jTextArea=new JTextArea();
        jTextArea.setLineWrap(true);

        //布局
        Box box1 = Box.createHorizontalBox();
        box1.add(jLabel);
        box1.add(jComboBox);

        Box box2=Box.createVerticalBox();
        box2.add(box1);
        box2.add(Box.createVerticalStrut(30));
        box2.add(jb1);
        box2.add(Box.createVerticalStrut(30));
        box2.add(jb2);
        box2.add(Box.createVerticalStrut(30));
        box2.add(jb3);
        box2.add(Box.createVerticalStrut(30));
        box2.add(jb4);
        box2.add(Box.createVerticalStrut(10));

        JSplitPane jSplitPane=new JSplitPane();
        jSplitPane.setLeftComponent(box2);
        jSplitPane.setRightComponent(jTextArea);
        jSplitPane.setOneTouchExpandable(true);
        jSplitPane.setContinuousLayout(true);

        jFrame.setContentPane(jSplitPane);
        jFrame.setSize(500,350);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        //显示出现次数的事件
        jb1.addActionListener(e -> {
            String name_now= (String) jComboBox.getSelectedItem();
            try {
                Person people= Methods1.getTimes(person,name_now);
                jTextArea.setText("出现的次数是:"+people.times);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //篇幅跨度排序的事件
        jb2.addActionListener(e -> {
            try {
                Methods2.getSpanOfSpace(person);
                Methods2.sortBySpan(person,name,0,9);
                jTextArea.setText("篇幅跨度排序为：\n");
                for(int i=9;i>=0;--i){
                    jTextArea.append("第"+(10-i)+"是"+name[i]+",篇幅跨度为："+person[i].span+"\n");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //紧密和不紧密的事件
        jb3.addActionListener(e -> {
            try {
                //先求出每个人对应的紧密程度
                Methods3.getContact(person);
                BasicMethods.set(person);
                double[] nums={Double.MIN_VALUE,Double.MAX_VALUE};
                List<String> min=new ArrayList<>();
                List<String> max=new ArrayList<>();
                Methods3.find(person,max,min,nums);
                jTextArea.setText("关系最紧密的值为"+nums[0]+",配对见下：\n");
                for(String s:max){
                    jTextArea.append(s+"\n");
                }
                jTextArea.append("关系最不紧密的值为"+nums[1]+",配对见下：\n");
                for(String s:min){
                    jTextArea.append(s+"\n");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        //紧密程度排序的事件
        jb4.addActionListener(e->{
            try {
                Methods3.getContact(person);
                String name_now= (String) jComboBox.getSelectedItem();
                Methods3.sortByContact(person,name_now);
                int pos= BasicMethods.getPos(person,name_now);
                jTextArea.setText(name_now+"的紧密程度排序为：\n");
                for(int i=8;i>=0;--i){
                    jTextArea.append("第"+(9-i)+"是"+name_now+"和"+person[pos].relation[i].name.get(0)+",紧密程度为："+person[pos].connection[i]+"\n");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
