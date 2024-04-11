import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SockLine {

    /** Write single line with LF */

    public static void writeLine(Socket sock, String txt)
            throws IOException
    {
        txt = txt + "\r\n";
        sock.getOutputStream().write(txt.getBytes(StandardCharsets.US_ASCII));
    }

    /** Read single line terminated by \n, or null if closed. */

    public static String readLine(Socket sock)
            throws IOException
    {
        // Read as bytes. Only convert to UTF-8 when we have entire line.
        // A memory mapped output stream is the easiest way I know
        // to store a varying length sequence of bytes.
        ByteArrayOutputStream inData = new ByteArrayOutputStream();
        int     ch;
        String  txt;

        while (true) {
            ch = sock.getInputStream().read();
            if (ch < 0) {
                // Socket closed. If we have any data it is an incomplete
                // line, otherwise immediately return null
                if (inData.size() > 0)
                    break;
                else
                    return null;
            }
            inData.write((byte)ch);
            // This comparison always works with UTF-8 because high bytes
            // of multi byte characters have at least bit 7 set
            if (ch == (int)'\n')
                break;
        }
        txt = new String(inData.toByteArray(), 0, inData.size(), "UTF-8");
        return txt;
    }

}
