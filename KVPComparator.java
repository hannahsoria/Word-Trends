
import java.util.Comparator;

public class KVPComparator implements Comparator <KeyValuePair<String,Integer>>{

    public int compare(KeyValuePair<String, Integer> o1, KeyValuePair<String, Integer> o2) {
        return o1.getValue() - o2.getValue();
    }}
