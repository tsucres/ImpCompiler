package Utils;
public class StringUtils {
	static public String repeatString(String str, int n) {
		return new String(new char[n]).replace("\0", str);
	}
}