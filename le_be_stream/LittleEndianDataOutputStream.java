package lestreams;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class LittleEndianDataOutputStream extends FilterOutputStream implements
		DataOutput {
	
	public LittleEndianDataOutputStream(OutputStream os) {
		super(new DataOutputStream(os));
	}

	@Override
	public final void writeBoolean(boolean val) throws IOException {
		((DataOutputStream) out).writeBoolean(val);
	}

	@Override
	public void writeByte(int val) throws IOException {
		((DataOutputStream)out).writeByte(val);		
	}

	@Override
	public void writeBytes(String str) throws IOException {
		((DataOutputStream)out).writeBytes(str);		
	}

	@Override
	public void writeChar(int val) throws IOException {
		writeShort(val);		
	}

	@Override
	public void writeChars(String str) throws IOException {
		for (int i = 0; i < str.length(); i++) {
			writeChar(str.charAt(i));
		}
	}

	@Override
	public void writeDouble(double val) throws IOException {
		writeLong(Double.doubleToLongBits(val));
		
	}

	@Override
	public void writeFloat(float val) throws IOException {
		writeInt(Float.floatToIntBits(val));
	}

	@Override
	public void writeInt(int val) throws IOException {
		out.write(0xff & val);
		out.write(0xff & (val >> 8));
		out.write(0xff & (val >> 16));
		out.write(0xff & (val >> 24));
	}

	@Override
	public void writeLong(long val) throws IOException {
		byte[] bytes = ByteBuffer.allocate(8).putLong(Long.reverseBytes(val)).array();
		write(bytes, 0, bytes.length);
	}

	@Override
	public void writeShort(int val) throws IOException {
		out.write(val & 0xff);
		out.write((val >> 8) & 0xff);
	}

	@Override
	public void writeUTF(String str) throws IOException {
		((DataOutputStream) out).writeUTF(str);
		
	}

	@Override
	public void write(int oneByte) throws IOException {
		((DataOutputStream) out).write(oneByte);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		out.write(b, off, len);
	}
}
