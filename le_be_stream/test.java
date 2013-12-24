import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.Exception;
import java.io.FileNotFoundException;

public class test {
	public static String IFileName = "input.txt";
	public static String OFileName = "output.txt";

	public class EndianDataInputStream extends DataInputStream {
		EndianDataInputStream(InputStream is) {
			super.DataInputStream(is);
		}
	}

	public static void main(String[] str) {
		System.out.println("Test of new le/be stream");
		try {
		DataInputStream dis = new DataInputStream(new FileInputStream(IFileName));
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(OFileName));
		} catch (FileNotFoundException e) {
			System.out.println("Files were not found! Terminating.");
			return;
		}
	}
}

