package team.dl.mapeditor2d;

import javax.swing.*;
import java.awt.*;

/**
 * 画布只有一张，而且需要立即加载出来。
 * 选择使用饿汉式单例模式
 *
 */
public class MapPanelSingleton extends JPanel implements MapConfig,Runnable{
    /**
     * 图片池
     */
    private PicturePool pictures = PicturePool.getInstance();
    /**
     * 加载类时，是天然线程安全的
     * 加载时就获得画布实例
     */
    private static MapPanelSingleton mapPanelSingleton = new MapPanelSingleton();
    /**
     * 私有化构造器
     */
    private MapPanelSingleton() {
        //防止反射漏洞
        if(mapPanelSingleton != null){
            try {
                throw new Exception("画布实例已经存在");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ReadMap.readMapFile(path);
        this.setPreferredSize(new Dimension(MapWidth,MapHeight));
        new Thread(this).start();
    }
    /**
     * 获取单例对象
     * @return 画布类对象
     */
    public static MapPanelSingleton getMapPanelSingleton(){
        return mapPanelSingleton;
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        drawMap(g);
    }
    /**
     * 绘制地图方法
     * @param g  画笔
     */
    private void drawMap(Graphics g){
        for(int i=0;i<MapHeight/elemHeight;i++){
            for(int j=0;j<MapWidth/elemWidth;j++){
                //画底层元素
                drawSingleMap(g, ReadMap.map_bottom,i,j);
                //画玩家层元素
                drawSingleMap(g,ReadMap.map_middle,i,j);
                //画上层元素
                if(pictures.getPicture(ReadMap.map_top[i][j]) != null){
                    g.drawImage(pictures.getPicture(ReadMap.map_top[i][j]).getImage(), getDrawX(j), getDrawY(i), elemWidth, elemHeight, null);
                }
            }
        }
    }
    /**
     * 单层地图绘制，1开头 32x32图片 2开头 64x64图片 3开头 32x64图片
     * @param g 画笔
     * @param map 地图数组
     * @param i 数组坐标
     * @param j 数组坐标
     */
    private void drawSingleMap(Graphics g,int[][] map,int i,int j){
        if(pictures.getPicture(map[i][j]) != null){
            g.drawImage(pictures.getPicture(map[i][j]).getImage(), getDrawX(j), getDrawY(i), elemWidth, elemHeight, null);
        }
    }
    /**
     * 获取绘图坐标x
     * @param x 传入地图坐标x
     * @return 像素坐标x
     */
    private int getDrawX(int x){
        return x<<5;
    }
    /**
     * 获取绘图坐标y
     * @param y 传入地图坐标y
     * @return 像素坐标y
     */
    private int getDrawY(int y){
        return y<<5;
    }

    @Override
    public void run() {
        while (true){
            this.repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
