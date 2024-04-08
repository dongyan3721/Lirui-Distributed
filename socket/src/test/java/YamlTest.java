import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Santa Antilles
 * @description
 * @date 2024/4/6-15:13:32
 */
public class YamlTest {
    public static void main(String[] args) {
        // 指定YAML文件路径
//        System.out.println(System.getProperty("user.dir"));
        String yamlFile = "properties.yml";

        // 创建一个HashMap来存储YAML文件中的内容
        HashMap<String, Object> data = new HashMap<>();

        try {
            // 读取YAML文件
            Yaml yaml = new Yaml();
            FileInputStream inputStream = new FileInputStream(yamlFile);
            // 将YAML文件内容加载到HashMap中
            data = yaml.load(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        HashMap<String, Object> vehicles = (HashMap<String, Object>) data.get("vehicles");
        HashMap<String, Object> bus = (HashMap<String, Object>) vehicles.get("Bus");
        System.out.println(bus.get("chargePropertiesList") instanceof ArrayList);
        // 打印HashMap内容
        System.out.println(data);
    }
}
