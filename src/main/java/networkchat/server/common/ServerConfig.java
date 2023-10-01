package networkchat.server.common;import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class ServerConfig {
    private String ip;
    private int port;

    public void load(String configFile) throws RuntimeException {
        Yaml yaml = new Yaml();
        try (FileInputStream input = new FileInputStream(configFile)) {
            Map<String, Object> data = (Map<String, Object>) yaml.load(input);

            this.ip = (String) data.get("ip");
            this.port = (Integer) data.get("port");
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

}