import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.util.*;

/**
 * Graph for storing all of the intersection (vertex) and road (edge) information.
 * Uses your GraphBuildingHandler to convert the XML files into a graph. Your
 * code must include the vertices, adjacent, distance, closest, lat, and lon
 * methods. You'll also need to include instance variables and methods for
 * modifying the graph (e.g. addNode and addEdge).
 *
 * @author Alan Yao, Josh Hug
 */
public class GraphDB {
    /** Your instance variables for storing the graph. You should consider
     * creating helper classes, e.g. Node, Edge, etc. */
    private Map<Long, Node> nodes;
    private Set<Way> ways;
    /**
     * Example constructor shows how to create and start an XML parser.
     * You do not need to modify this constructor, but you're welcome to do so.
     * @param dbPath Path to the XML file to be parsed.
     */
    public GraphDB(String dbPath) {
        nodes = new HashMap<>();
        ways = new HashSet<>();
        try {
            File inputFile = new File(dbPath);
            FileInputStream inputStream = new FileInputStream(inputFile);
            // GZIPInputStream stream = new GZIPInputStream(inputStream);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GraphBuildingHandler gbh = new GraphBuildingHandler(this);
            saxParser.parse(inputStream, gbh);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    public int numVertices() {
        return nodes.size();
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
        // TODO: Your code here.
        Set<Node> remove = new HashSet<>();
        for (Node node : nodes.values()) {
            Set<Long> adj = (Set<Long>) adjacent(node.id);
            if (adj.isEmpty()) {
                remove.add(node);
            }
        }
        for (Node node : remove) {
            removeNode(node);
        }
    }
    /**
     * remove some node from nodes of graph
     */
    private void removeNode(Node node) {
        nodes.remove(node.id);
    }

    /**
     * return given id node
     */
    public Node findNode(Long id) {
        return nodes.get(id);
    }
    /**
     * add some node to nodes of graph
     */
    public void addVertice(Node node) {
        nodes.put(node.id, node);
    }


    /**
     * Returns an iterable of all vertex IDs in the graph.
     * @return An iterable of id's of all vertices in the graph.
     */
    Iterable<Long> vertices() {
        //YOUR CODE HERE, this currently returns only an empty list.
        return nodes.keySet();
    }

    /**
     * Returns ids of all vertices adjacent to v.
     * @param v The id of the vertex we are looking adjacent to.
     * @return An iterable of the ids of the neighbors of v.
     */
    Iterable<Long> adjacent(long v) {
        return nodes.get(v).adjacent;
    }

    /**
     * Returns the great-circle distance between vertices v and w in miles.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The great-circle distance between the two locations from the graph.
     */
    double distance(long v, long w) {
        return distance(lon(v), lat(v), lon(w), lat(w));
    }

    static double distance(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double dphi = Math.toRadians(latW - latV);
        double dlambda = Math.toRadians(lonW - lonV);

        double a = Math.sin(dphi / 2.0) * Math.sin(dphi / 2.0);
        a += Math.cos(phi1) * Math.cos(phi2) * Math.sin(dlambda / 2.0) * Math.sin(dlambda / 2.0);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 3963 * c;
    }

    /**
     * Returns the initial bearing (angle) between vertices v and w in degrees.
     * The initial bearing is the angle that, if followed in a straight line
     * along a great-circle arc from the starting point, would take you to the
     * end point.
     * Assumes the lon/lat methods are implemented properly.
     * <a href="https://www.movable-type.co.uk/scripts/latlong.html">Source</a>.
     * @param v The id of the first vertex.
     * @param w The id of the second vertex.
     * @return The initial bearing between the vertices.
     */
    double bearing(long v, long w) {
        return bearing(lon(v), lat(v), lon(w), lat(w));
    }

    static double bearing(double lonV, double latV, double lonW, double latW) {
        double phi1 = Math.toRadians(latV);
        double phi2 = Math.toRadians(latW);
        double lambda1 = Math.toRadians(lonV);
        double lambda2 = Math.toRadians(lonW);

        double y = Math.sin(lambda2 - lambda1) * Math.cos(phi2);
        double x = Math.cos(phi1) * Math.sin(phi2);
        x -= Math.sin(phi1) * Math.cos(phi2) * Math.cos(lambda2 - lambda1);
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    long closest(double lon, double lat) {
        double closest = Long.MAX_VALUE;
        Node closestNode = null;
        for (Node node : nodes.values()) {
            double curDistance = distance(node.lon, node.lat, lon, lat);
            if (curDistance < closest) {
                closestNode = node;
                closest = curDistance;
            }
        }
        return closestNode.id;
    }

    /**
     * Gets the longitude of a vertex.
     * @param v The id of the vertex.
     * @return The longitude of the vertex.
     */
    double lon(long v) {
        return nodes.get(v).lon;
    }

    /**
     * Gets the latitude of a vertex.
     * @param v The id of the vertex.
     * @return The latitude of the vertex.
     */
    double lat(long v) {
        return nodes.get(v).lat;
    }

    public void addWay(Way way) {
        ways.add(way);
    }

    static class Node{
        private long id;
        private double lon;
        private double lat;
        private String name;
        private Set<Long> adjacent;
        private double disTo;
        private double priority;

        public Node(String id, String lat, String lon) {
            this.id = Long.parseLong(id);
            this.lon = Double.parseDouble(lon);
            this.lat = Double.parseDouble(lat);
            disTo = Double.MAX_VALUE;
            adjacent = new HashSet<>();
            priority = Double.MAX_VALUE;
        }
        public void addName(String name) {
            this.name = name;
        }
        public void addAdjV(Long id) {
            adjacent.add(id);
        }

    }
    public static class Way {
        private List<Long> adjacentV;
        private String highway;
        private String name;
        public Way() {
            adjacentV = new ArrayList<>();
        }
        public void addAdjV(String id) {
            adjacentV.add(Long.parseLong(id));
        }

        public void setHighway(String v) {
            highway = v;
        }
        public void setName(String name) {
            this.name = name;
        }
        public List<Long> adjacentV() {
            return adjacentV;
        }
    }

    public Comparator<Node> getPComparator() {
        return new PComparator();
    }
    public class PComparator implements Comparator<Node> {
        @Override
        public int compare(Node node1, Node node2) {
            return (int) (node1.priority - node2.priority);
        }
    }
    public class LComparator implements Comparator<Long> {
        @Override
        public int compare(Long node1, Long node2) {
            return Double.compare(nodes.get(node1).priority, nodes.get(node2).priority);
        }
    }
    public Comparator<Long> getLComparator() {
        return new LComparator();
    }
    public double getDisTo(long node) {
        return nodes.get(node).disTo;
    }
    public void changeDisTo(long node, double disTo) {
        nodes.get(node).disTo = disTo;
    }
    public void changePriority(long node, double priority) {
        nodes.get(node).priority = priority;
    }
    public double getPriority(long node) {
        return nodes.get(node).priority;
    }
}
