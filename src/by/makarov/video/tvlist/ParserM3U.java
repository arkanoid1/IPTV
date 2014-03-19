package by.makarov.video.tvlist;

import by.makarov.video.entitys.ChanelM3U;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class ParserM3U {
    private ChanelM3U chanel;
    private ArrayList<ChanelM3U> list;

    public ParserM3U() {
        list = new ArrayList<ChanelM3U>();
    }

    public ArrayList pars(URL url) throws IOException {

        String stream = (readProgram(url, "UTF8")).replaceAll("#EXTM3U", "")
                .trim();
        // String[] arr = stream.split("#EXTINF.*,");
        StringBuilder st;
        Scanner scan = new Scanner(stream);

        while (scan.hasNext()) {
            chanel = new ChanelM3U();
            String s = scan.nextLine();
            if (s.length() < 3)
                continue;

            //tvg canal name
            int firstPos = s.indexOf("tvg-name=");
            if (firstPos != -1) {
                st = new StringBuilder();
                for (int i = firstPos + 10; i < s.length(); i++) {
                    if (String.valueOf(s.charAt(i)).equals("\"")) {
                        break;
                    }
                    st.append(s.charAt(i));
                }
                chanel.setTvgName(st.toString());
            }

            //canal name
            firstPos = s.lastIndexOf(",");
            if (firstPos != -1) {
                st = new StringBuilder();
                for (int i = firstPos + 1; i < s.length(); i++) {
                    st.append(s.charAt(i));
                }
                chanel.setCanalName(st.toString());
            }


            // tvg time shift
            firstPos = s.indexOf("tvg-shift=");
            if (firstPos != -1) {
                st = new StringBuilder();
                for (int i = firstPos + 10; i < firstPos + 13; i++) {
                    if (!String.valueOf(s.charAt(i)).equals(",")) {
                        st.append(s.charAt(i));
                    }
                }
                chanel.setTvgShift(st.toString());
            }

            //canal address link
            if (scan.hasNext()) {
                s = scan.nextLine();
                firstPos = s.indexOf("http:");
                if (firstPos == -1) {
                    firstPos = s.indexOf("rtp:");
                }
                if (firstPos == -1) {
                    firstPos = s.indexOf("udp:");
                }
                if (firstPos == -1) {
                    firstPos = s.indexOf("rtsp:");
                }
                if (firstPos == -1) {
                    firstPos = s.indexOf("mms:");
                }

                st = new StringBuilder();
                if (firstPos != -1) {
                    for (int i = firstPos; i < s.length(); i++) {
                        if (String.valueOf(s.charAt(i)).equals("@"))
                            continue; // remove "@"
                        st.append(s.charAt(i));
                    }
                    chanel.setUrlCanal(st.toString());
                }
            }

            list.add(chanel);

        }
        return list;
    }

    private static String readProgram(URL url, String encode)
            throws IOException {
        StringBuffer data = new StringBuffer();

        InputStream dataStream = url.openConnection().getInputStream();
        InputStreamReader isr = new InputStreamReader(dataStream, encode);

        int c;
        while ((c = isr.read()) != -1) {
            data.append((char) c);
        }
        return new String(data.toString());
    }

}


