package com.tronservice.lestreams;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LittleEndianDataInputStream extends FilterInputStream implements DataInput {
	
	public LittleEndianDataInputStream(InputStream is) {
		super(new DataInputStream(is));
	}

	@Override
	public final boolean readBoolean() throws IOException {
		return ((DataInputStream) in).readBoolean();
	}

	@Override
	public final byte readByte() throws IOException {
		return ((DataInputStream) in).readByte();
	}

	@Override
	public final char readChar() throws IOException {
		byte[] bytes = new byte[2];
		((DataInputStream) in).readFully(bytes, 0, 2);
		return (char)(((bytes[1] & 0xff) << 8) | (bytes[0] & 0xff));
	}

	@Override
	public final double readDouble() throws IOException {
		return Double.longBitsToDouble(readLong());
	}

	@Override
	public final float readFloat() throws IOException {
		return Float.intBitsToFloat(readInt());
	}

	@Override
	public final void readFully(byte[] dst) throws IOException {
		((DataInputStream) in).readFully(dst);
	}

	@Override
	public final void readFully(byte[] dst, int offset, int byteCount)
			throws IOException {
		((DataInputStream) in).readFully(dst, offset, byteCount);
	}

	@Override
	public final int readInt() throws IOException {
		byte[] b = new byte[2];
		((DataInputStream) in).readFully(b, 0, 4);
		System.out.println(b);
		return ((b[3] & 0xff) << 24) |
					 ((b[2] & 0xff) << 16) |
					 ((b[1] & 0xff) <<  8) |
					 ((b[0] & 0xff) <<  0);
	}

	@Override
	@Deprecated
	public final String readLine() throws IOException {
		return ((DataInputStream) in).readLine();
	}

	@Override
	public final long readLong() throws IOException {
		byte[] b = new byte[8];
		((DataInputStream) in).readFully(b, 0, 8);
		return  ((b[7] & 0xff) << 56) |
					  ((b[6] & 0xff) << 48) |
					  ((b[5] & 0xff) << 40) |
					  ((b[4] & 0xff) << 32) |
					  ((b[3] & 0xff) << 24) |
					  ((b[2] & 0xff) << 16) |
					  ((b[1] & 0xff) <<  8) |
					  ((b[0] & 0xff) <<  0);
	}

	@Override
	public final short readShort() throws IOException {
		byte[] b = new byte[2];
		((DataInputStream) in).readFully(b, 0, 2);
		return (short)(((b[1] & 0xff) << 8) | (b[0] & 0xff));
	}

	@Override
	public final String readUTF() throws IOException {
		return ((DataInputStream) in).readUTF();
	}

	@Override
	public final int readUnsignedByte() throws IOException {
		((DataInputStream) in).readUnsignedByte();
		return 0;
	}

	@Override
	public final int readUnsignedShort() throws IOException {
		int us = ((DataInputStream) in).readUnsignedShort();
		return ((us << 8) & 0xff00) | ((us >> 8) | 0xff);
	}

	@Override
	public final int skipBytes(int count) throws IOException {
		return ((DataInputStream) in).skipBytes(count);
	}

	public final int read() throws IOException {
		return ((DataInputStream) in).read();
	}
	
	public final int read(byte b[], int off, int len) throws IOException {
		return ((DataInputStream) in).read(b, off, len);
	}
}
