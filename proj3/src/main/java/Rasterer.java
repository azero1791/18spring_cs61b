import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {
    private class Position {
        int x;
        int y;
        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    private Map<String, Double> query;
    private final int MINDEPTH;
    private final int MAXDEPTH;
    private double[] LonDPP;
    private String[][] render_gird;
    private double raster_ul_lon;
    private double raster_lr_lon;
    private double raster_ul_lat;
    private double raster_lr_lat;
    private boolean raster_success;
    private int depth;
    private double disPerImgY;
    private double disPerImgX;

    public Rasterer() {
        // YOUR CODE HERE
        MINDEPTH = 0;
        MAXDEPTH = 7;
        LonDPP = new double[8];
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     *
     *     The grid of images must obey the following properties, where image in the
     *     grid is referred to as a "tile".
     *     <ul>
     *         <li>The tiles collected must cover the most longitudinal distance per pixel
     *         (LonDPP) possible, while still covering less than or equal to the amount of
     *         longitudinal distance per pixel in the query box for the user viewport size. </li>
     *         <li>Contains all tiles that intersect the query bounding box that fulfill the
     *         above condition.</li>
     *         <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     *     </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     *
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     *                    forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        query = params;
        Map<String, Object> results = new HashMap<>();
        Map<String, Position> resultCorners = new HashMap<>();
        determineDepth();
        determineGrid(resultCorners);
        calRPosition(resultCorners);
        fillWithResults(resultCorners, results);
        return results;
    }
    /**
     * calculate the real position of corners displayed onto viewers
     */
    public void calRPosition(Map<String, Position> resultCorners) {
        raster_ul_lon = MapServer.ROOT_ULLON + resultCorners.get("ul").x * disPerImgX;
        raster_ul_lat = MapServer.ROOT_ULLAT - resultCorners.get("ul").y * disPerImgY;
        raster_lr_lon = MapServer.ROOT_ULLON + (resultCorners.get("lr").x + 1) * disPerImgX;
        raster_lr_lat = MapServer.ROOT_ULLAT - (resultCorners.get("lr").y + 1) * disPerImgY;
    }
    /**
     * collect aspects of result
     */
    public void fillWithResults(Map<String, Position> resultCorners, Map<String, Object> results) {
        results.put("render_grid", render_gird);
        results.put("raster_ul_lon", raster_ul_lon);
        results.put("raster_ul_lat", raster_ul_lat);
        results.put("raster_lr_lon", raster_lr_lon);
        results.put("raster_lr_lat", raster_lr_lat);
        results.put("depth", depth);
        results.put("query_success", raster_success);
    }

    /**
     * get the depth of query box in the whole graph
     */
    private void determineDepth() {
        calStdLonDPP();
        raster_success = true;
        double queryLonDPP = calQLonDPP();
        for (int depth = MINDEPTH; depth <= MAXDEPTH; ++depth) {
            if (LonDPP[depth] <= queryLonDPP) {
                this.depth = depth;
                return;
            }
        }
        depth = 7;

    }

    /**
     * calculate LonDPP of all depth levels
     */
    private void calStdLonDPP() {
        for (int depth = MINDEPTH; depth <= MAXDEPTH; ++depth) {
            LonDPP[depth] = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / (MapServer.TILE_SIZE * Math.pow(2, depth));
        }
    }

    /**
     * calculate LonDPP of query
     */
    private double calQLonDPP() {
        return (query.get("lrlon") - query.get("ullon")) / query.get("w");
    }

    /**
     * determine files needed to be filled
     */
    private void determineGrid(Map<String, Position> resultCorners) {
        getRCorners(resultCorners);
        render_gird = new String[resultCorners.get("lr").y - resultCorners.get("ul").y + 1][resultCorners.get("lr").x - resultCorners.get("ul").x + 1];
        fillWithGrid(resultCorners);
    }

    /**
     * get the corners of rresult graph
     */
    private void getRCorners(Map<String, Position> resultCorners) {
        int numImgX = (int) Math.pow(2, depth);
        int numImgY = (int) Math.pow(2, depth);
        disPerImgX = (MapServer.ROOT_LRLON - MapServer.ROOT_ULLON) / numImgX;
        disPerImgY = (MapServer.ROOT_ULLAT - MapServer.ROOT_LRLAT) / numImgY;
        boolean ul_x = false;
        boolean lr_y = false;
        boolean ul_y = false;
        boolean lr_x = false;
        int ulX = Integer.MIN_VALUE;
        int ulY = Integer.MIN_VALUE;
        int lrX = Integer.MIN_VALUE;
        int lrY = Integer.MIN_VALUE;
        for (int x = 0; x <= numImgX; ++x) {
            double absX = MapServer.ROOT_ULLON + disPerImgX * x;
            if (absX > query.get("ullon")) {
                if (!ul_x) {
                    ulX = x - 1;
                    ul_x = true;
                }
                if (!lr_x && absX > query.get("lrlon")) {
                    lrX = x - 1;
                    lr_x = true;
                }
                for (int y = 0; y <= numImgY; ++y) {
                    double absY = MapServer.ROOT_ULLAT - disPerImgY * y;
                    if (absY < query.get("ullat")) {
                        if (!ul_y) {
                            ulY = y - 1;
                            ul_y = true;
                        }
                        if (!lr_y && absY < query.get("lrlat")) {
                            lrY = y - 1;
                            lr_y = true;
                        }
                    }
                }
            }
        }
        if (!ul_x) {
            ulX = 0;
        }
        if (!ul_y) {
            ulY = 0;
        }
        if (!lr_x) {
            lrX = numImgX;
        }
        if (!lr_y) {
            lrY = numImgY;
        }
        resultCorners.put("ul", new Position(ulX, ulY));
        resultCorners.put("lr", new Position(lrX, lrY));
    }

    /**
     * fill files path string with grid
     */
    private void fillWithGrid(Map<String, Position> resultCorners) {
        int startX = resultCorners.get("ul").x;
        int endX = resultCorners.get("lr").x;
        int startY = resultCorners.get("ul").y;
        int endY = resultCorners.get("lr").y;
        for (int y = 0; y < render_gird.length; ++y) {
            for (int x = 0; x < render_gird[0].length; x++) {
                render_gird[y][x] = String.format("d%d_x%d_y%d.png", depth, startX + x, startY + y);
            }
        }
    }


}
