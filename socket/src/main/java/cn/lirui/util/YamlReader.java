package cn.lirui.util;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

/**
 * @author Santa Antilles
 * @description
 * @date 2024/4/6-16:26:24
 */
public class YamlReader {

    public static HashMap<String, Object> read(String filename){
        HashMap<String, Object> data = new HashMap<>();

        try {
            // 读取YAML文件
            Yaml yaml = new Yaml();
            FileInputStream inputStream = new FileInputStream(filename);
            // 将YAML文件内容加载到HashMap中
            return yaml.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }
}
