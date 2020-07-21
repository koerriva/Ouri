package utils;

import com.alibaba.fastjson.JSON;
import engine.graph.Mesh;
import engine.scene.Scene;
import engine.scene.gltf.GLTF;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

public class ResourceLoader {
    private static final HashMap<String,byte[]> data = new HashMap<>();

    public static void init() throws IOException {
        File root = new File("data");
        readFile(root);
    }

    private static void readFile(File root) throws IOException {
        File[] files = root.listFiles();
        for (File f : files){
            System.out.println(f.getPath());
            if(f.isDirectory()){
                readFile(f);
            }else {
                loadFile(f.getPath());
            }
        }
    }

    public static String[] loadShaderFile(String name) throws IOException {
        String[] data = new String[2];
        String path1 = "data/shader/"+name+".vert";
        String path2 = "data/shader/"+name+".frag";
        data[0] = new String(loadFile(path1),Charset.defaultCharset());
        data[1] = new String(loadFile(path2),Charset.defaultCharset());
        return data;
    }

    public static Scene loadScene(String name) throws IOException{
        byte[] data = loadFile("data/model/"+name+".gltf");
        GLTF gltf = JSON.parseObject(data,GLTF.class);
        return new Scene(gltf);
    }

    public static byte[] loadFile(String filename) throws IOException{
        if(data.containsKey(filename))return data.get(filename);
        File file = new File(filename);
        int size = (int) file.length();
        byte[] buff = new byte[size];
        FileInputStream inputStream = new FileInputStream(file);
        int r = inputStream.read(buff);
        System.out.println("read byte "+r);
        inputStream.close();
        data.put(filename,buff);
        return buff;
    }

    public static void cleanup(){
        data.clear();
    }
}
