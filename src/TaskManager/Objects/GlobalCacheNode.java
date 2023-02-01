package TaskManager.Objects;

public class GlobalCacheNode {
    public enum GlobalCacheType {
        STRING, NUMBER
    }
    public String name;
    public GlobalCacheType type;
    public String value;
}
