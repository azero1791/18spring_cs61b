public class NBody {
    public static double readRadius (String fileName) {
        In in = new In(fileName);
        int N = in.readInt();
        double r = in.readDouble();

        return r;
    }

    public static Planet[] readPlanets (String fileName) {
        In in = new In(fileName);
        int numPlanet = in.readInt();
        Planet[] planets = new Planet[numPlanet];

        double r = in.readDouble();
        int i = 0;
        while (i < numPlanet) {
            double xP = in.readDouble();
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            planets[i] = new Planet(xP, yP, xV, yV, m, img);
            i += 1;
        }
        return planets;
        
    }

    public static void main (String[] args) {
        double T = Double.parseDouble(args[0]); 
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);

        StdDraw.enableDoubleBuffering();

        /** set the scale of the universe pictrue and complement the background picture */
        StdDraw.setScale(-radius, radius);
        
        int numPlanet = planets.length;
        double t = 0;
        while (t < T) {
            double[] xForce = new double[numPlanet];
            double[] yForce = new double[numPlanet];
            for (int i = 0; i < numPlanet; i++) {
                xForce[i] = planets[i].calcNetForceExertedByX(planets);
                yForce[i] = planets[i].calcNetForceExertedByY(planets);
            }

            for (int i = 0; i < numPlanet; i++) {
                planets[i].update(dt, xForce[i], yForce[i]);
            }

            /** draw background picture */
            StdDraw.picture(0, 0, "images/starfield.jpg"); 
             
            for (Planet p : planets) {
                p.draw();
            }
            StdDraw.show();

            StdDraw.pause(10);

            t += dt;
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
            planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
        }

    }
		
}


