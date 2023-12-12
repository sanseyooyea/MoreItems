import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FixFileName {
    static Map<String, String> fixFileNames = new HashMap<>();
    public static void main(String[] args) {
        fixFileNames.put("铁弓", "iron_bow");
        fixFileNames.put("金弓", "golden_bow");
        fixFileNames.put("绿宝石弓", "emerald_bow");
        fixFileNames.put("钻石弓", "diamond_bow");
        fixFileNames.put("黑曜石弓", "obsidian_bow");

        File folder = new File("D:\\MC\\MinecraftMod\\MoreItems\\src\\main\\resources\\assets\\moreitems\\models\\item");
        File[] files = folder.listFiles();
        Arrays.stream(files).forEach(file -> {
            String name = file.getName();
            fixFileNames.forEach((k, v) -> {
                if (name.contains(k)) {
                    file.renameTo(new File(file.getParent() + "\\" + name.replace(k, v)));
                }
            });
        });
    }
}
