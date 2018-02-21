package team.dl.mapeditor2d;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;

/**
 * 地图编辑器窗口
 * @author Deng
 */
public class MapFrame extends JFrame implements MapConfig{
    /**
     * 用于选择素材的下拉表
     */
    private JComboBox<ImageIcon> box;
    /**
     * 用于选择素材层数的下拉表
     */
    private JComboBox<Integer> boxType;
    /**
     * 画布对象
     */
    private MapPanelSingleton panel;
    /**
     * 素材池
     */
    private PicturePool pictures = PicturePool.getInstance();
    /**
     * 窗口构造器
     */
    public MapFrame(){
        this.setTitle("地图数组生成");
        //窗口大小
        this.setSize(1440, 900);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //窗口布局
        this.setLayout(new FlowLayout());
        //获取画布
        panel = MapPanelSingleton.getMapPanelSingleton();
        //滚动条
        JScrollPane jScrollPane = new JScrollPane(panel);
        jScrollPane.setPreferredSize(new Dimension(1280,720));
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


        boxType = new JComboBox<Integer>();
        boxType.addItem(1);
        boxType.addItem(2);
        boxType.addItem(3);

        box = new JComboBox<>();

        setBox(box,pictures.getAllPictures());

        JButton create = new JButton("创建");
        create.setActionCommand("create");

        this.add(jScrollPane);
        this.add(box);
        this.add(boxType);
        this.add(create);
        this.setVisible(true);

        new DrawMap(create,box,boxType);
    }
    /**
     * 设置地图中的素材下拉表
     */
    private void setBox(JComboBox box, Collection<ImageIcon> pictures){
        for(ImageIcon img : pictures){
            box.addItem(img);
        }
    }

}
