package team.dl.mapeditor2d;

import java.io.*;

/**
 * 读取地图文件类
 * @author Deng
 */
public class ReadMap {
    /**
     * 底层地图数组
     */
    public static int[][] map_bottom = new int[200][200];
    /**
     * 中层地图数组
     */
    public static int[][] map_middle = new int[200][200];
    /**
     * 上层地图数组
     */
    public static int[][] map_top = new int[200][200];

    /**
     * 从地图文件读取三个地图数组
     * @param path 地图文件的路径
     */
    public static void readMapFile(String path){
        File mapFile = new File(path);
        if(!mapFile.exists()){
            try {
                mapFile.createNewFile();
                System.out.println("创建了一个新地图");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileInputStream fis = new FileInputStream(mapFile);
            DataInputStream dis = new DataInputStream(fis);
            //地图大小
            int width = dis.readInt();
            int height = dis.readInt();

            for(int i=0;i<width;i++){
                for(int j=0;j<height;j++){
                    map_bottom[i][j] = dis.readInt();
                    map_middle[i][j] = dis.readInt();
                    map_top[i][j] = dis.readInt();
                }
            }

            dis.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
