package team.dl.mapeditor2d;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataOutputStream;
import java.io.FileOutputStream;

/**
 * 响应类
 * @author Deng
 */
public class DrawMap implements MapConfig {
    /**
     * 创建命令匹配字符串
     */
    private String creation = "create";
    /**
     * 创建按钮监听对象
     */
    private ButtonListener buttonListener;
    /**
     * 鼠标位置监听对象
     */
    private PanelListener panelListener;
    /**
     * 创建按钮对象
     */
    private JButton create;
    /**
     * 素材下拉框对象
     */
    private JComboBox box;
    /**
     * 地图层数下拉框对象
     */
    private JComboBox boxType;

    /**
     * 监听类构造器
     * @param create 创建按钮对象
     * @param box 素材下拉框对象
     * @param boxType 地图层数下拉框对象
     */
    public DrawMap(JButton create,JComboBox box,JComboBox boxType){
        this.box = box;
        this.boxType = boxType;
        this.create =create;
        this.panelListener = new PanelListener();
        //画布中添加监听
        MapPanelSingleton.getMapPanelSingleton().addMouseListener(panelListener);
        this.buttonListener = new ButtonListener();
        //创建按钮中添加监听
        this.create.addActionListener(buttonListener);
    }
    /**
     * 内部类，创建按钮监听
     */
    private class ButtonListener implements ActionListener,MapConfig{
        @Override
        public void actionPerformed(ActionEvent e) {
            //如果按下了创建按钮，就保存当前设置好的3个二维数组
            if(creation.equals(e.getActionCommand())){
                try{
                    System.out.println("开始保存");
                    //得到文件输出流
                    FileOutputStream fos = new FileOutputStream(path);
                    //将文件输出流包装成基本数据输出流
                    DataOutputStream dos = new DataOutputStream(fos);
                    //先数组的大小写入文件
                    dos.writeInt(MapHeight/elemHeight);
                    dos.writeInt(MapWidth/elemWidth);
                    //按顺序将三个二维数组写入文件，记住这里的写入方式，后面游戏读取地图的时候也要按这种顺序读回来
                    for(int i=0;i<MapHeight/elemHeight;i++){
                        for(int j=0;j<MapWidth/elemWidth;j++){
                            dos.writeInt(ReadMap.map_bottom[i][j]);
                            dos.writeInt(ReadMap.map_middle[i][j]);
                            dos.writeInt(ReadMap.map_top[i][j]);
                        }
                    }
                    //刷新内存，强制输出
                    dos.flush();
                    //关闭输出流
                    dos.close();
                    System.out.println("保存完成");
                }catch(Exception ef){
                    ef.printStackTrace();
                }
            }
        }
    }
    /**
     * 内部类，画板鼠标位置监听
     */
    private class PanelListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            //得到该位置对应的数组下标
            int j = e.getX() >> 5;
            int i = e.getY() >> 5;
            System.out.println(i + "<>" + j);
            //得到选择框中的图片
            ImageIcon icon = (ImageIcon) box.getSelectedItem();
            //得到该图片的编号
            int num = Integer.parseInt(icon.toString().substring(6,9));
            System.out.println(icon.toString().substring(6,9));
            //修改数组中的值
            if ((int) boxType.getSelectedItem() == 1) {
                ReadMap.map_bottom[i][j] = num;
            }
            else if ((int) boxType.getSelectedItem() == 2) {
                ReadMap.map_middle[i][j] = num;
            }
            else if ((int) boxType.getSelectedItem() == 3) {
                ReadMap.map_top[i][j] = num;
            }
        }
    }
}
