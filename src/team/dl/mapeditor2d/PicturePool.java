package team.dl.mapeditor2d;

import javax.swing.*;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;

/**
 * 素材池
 * @author Deng
 */
public class PicturePool {
    /**
     * 素材开始编号
     */
    private final int startIndex = 101;
    /**
     * 素材结束编号
     */
    private final int endIndex = 119;
    /**
     * 存放素材的集合
     */
    private HashMap<Integer,ImageIcon> pictures;
    private static PicturePool picturePool = new PicturePool();

    /**
     * 素材池构造器
     */
    private PicturePool(){
        pictures = new HashMap<>();
        for(int i = startIndex;i < endIndex ; i++) {
            pictures.put(i,new ImageIcon("image"+ File.separator + i +".png"));
        }
    }
    /**
     * 获得素材池对象
     * @return 素材池对象
     */
    public static PicturePool getInstance(){
        return picturePool;
    }
    /**
     * 获取素材id对应素材方法
     * @param imageId 素材id
     * @return 素材id对应的素材图片
     */
    public ImageIcon getPicture(int imageId){
        return pictures.get(imageId);
    }
    /**
     * 获得所有素材方法
     * @return 素材池
     */
    public Collection<ImageIcon> getAllPictures(){
        return pictures.values();
    }
    /**
     * 素材池单元测试
     */
    public static void main(String[] args) {
        for(int i =101;i<119;i++){
            System.out.println(picturePool.getPicture(i));
        }
        //返回null
        System.out.println(picturePool.getPicture(200));
    }

}
