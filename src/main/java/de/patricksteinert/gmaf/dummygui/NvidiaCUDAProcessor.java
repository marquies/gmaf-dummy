package de.patricksteinert.gmaf.dummygui;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Map;
import java.util.Vector;

/**
 * gcsim connector and query input output processor
 */
public class NvidiaCUDAProcessor extends CollectionProcessor {

    /**
     * Connection host string.
     */
    private String host = "cudaknecht.fritz.box";

    /**
     * Connection port number.
     */
    private int port = 4711;

    /**
     * Result string buffer.
     */
    private StringBuffer result = null;

    /**
     * Query Graph Code.
     */
    private GraphCodeMeta queryGc;

    /**
     * Collection to generate metric values to.
     */
    private Vector<GraphCodeMeta> collection;


    /**
     * Execution of the query.
     */
    public void execute() {
        try {
            Socket socket = new Socket(host, 4711);
            PrintStream out = new PrintStream(socket.getOutputStream());

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.print("Query by Example: " + queryGc.getFileName());
            result = new StringBuffer();
            String line = in.readLine();
            while (!line.equals("metrics complete")) {
                line = in.readLine();
                result.append(line);
            }
            // Close our streams
            in.close();
            out.close();
            socket.close();

            String strJson = result.toString().replace("metrics complete", "");

            Gson json = new Gson();
            Vector<Object> elements = json.fromJson(strJson, Vector.class);
            for (Object o : elements) {
                Map<String, Object> m = (Map<String, Object>) o;
                String name = ((String) m.get("gc_filename"));
                Float similarity = ((Float) m.get("similarity"));
                Float recommendation = ((Float) m.get("recommendation"));
                Float inferencing = ((Float) m.get("inferencing"));

                for (GraphCodeMeta gcm : collection) {
                    if (gcm.getFileName().equals(name)) {
                        float[] metric = {similarity, recommendation, inferencing};
                        gcm.setMetric(metric);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Socket connection failed");

        }
    }

    @Override
    public void setQueryObject(GraphCodeMeta graphCodeMeta) {
        queryGc = graphCodeMeta;
    }

    @Override
    public Vector<GraphCodeMeta> getResultList() {
        if (result != null) {
            return collection;
        }
        return null;
    }

    @Override
    public void preloadIndex(Vector<GraphCodeMeta> collection) {
        this.collection = collection;

    }

    /**
     * Setter for the host string.
     * @param s
     */
    public void setHost(String s) {
        this.host = s;
    }

    /**
     * Setter for the port string.
     * @param port
     */
    public void setPort(int port) {
        this.port = port;
    }
}

