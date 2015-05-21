package utils;

import java.nio.ByteBuffer;

public class TypeConverter
{

    public static short getUnsignedByte(ByteBuffer bb)
    {
        return ((short) (bb.get() & 0xff));
    }

    public static short getUnsignedByte(ByteBuffer bb, int position)
    {
        return ((short) (bb.get(position) & (short) 0xff));
    }

    // ---------------------------------------------------------------
    public static int getUnsignedShort(ByteBuffer bb)
    {
        return (bb.getShort() & 0xffff);
    }

    public static int getUnsignedShort(ByteBuffer bb, int position)
    {
        return (bb.getShort(position) & 0xffff);
    }

    // ---------------------------------------------------------------
    public static long getUnsignedInt(ByteBuffer bb)
    {
        return ((long) bb.getInt() & 0xffffffffL);
    }

    public static long getUnsignedInt(ByteBuffer bb, int position)
    {
        return ((long) bb.getInt(position) & 0xffffffffL);
    }
}
