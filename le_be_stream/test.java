import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.Exception;
import java.io.FileNotFoundException;
import java.io.IOException;

public class test {
	public static String IFileName = "input.txt";
	public static String OFileName = "output.txt";

	public class EndianDataInputStream extends DataInputStream {
		EndianDataInputStream(InputStream is) {
			super(is);
		}
	}

	public static void main(String[] str) {
		System.out.println("Test of new le/be stream");
		DataInputStream dis;
		DataOutputStream dos;
		try {
			dis = new DataInputStream(new FileInputStream(IFileName));
			dos = new DataOutputStream(new FileOutputStream(OFileName));
		} catch (FileNotFoundException e) {
			System.out.println("Files were not found! Terminating.");
			return;
		}
		try {
			dis.close();
			dos.close();
		} catch (IOException e) {
			if (dis == null) {
				System.err.println("Cannot close DataOutputStream!");
			} else
			if (dos == null) {
				System.err.println("Some error occured while closing streams: " + e);
				e.printStackTrace();
			} else {
				System.err.println("Cannot close DataInputStream!");
			}
		}
	}
}

