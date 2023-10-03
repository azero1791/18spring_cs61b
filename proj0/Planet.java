public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static double G = 6.67e-11;

    public Planet (double xP, double yP, 
                    double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet (Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    public double calcDistance (Planet p) {
        // double rAns = 0;
        // double rSquare = (xxPos - p.xxPos) * (xxPos - p.xxPos)
        //                  + (yyPos - p.yyPos) * (yyPos - p.yyPos);
        // while (rAns * rAns != rSquare) {
        //     rAns += 1;
        // }
        // return rAns;
        return Math.sqrt((xxPos - p.xxPos)*(xxPos - p.xxPos)
						+ (yyPos - p.yyPos)*(yyPos - p.yyPos));
    }

    public double calcForceExertedBy (Planet p) {
        return G * mass * p.mass / (calcDistance(p) * calcDistance(p));
    }

    public double calcForceExertedByX (Planet p) {
        return calcForceExertedBy(p) * (p.xxPos - xxPos) / calcDistance(p);
    }

    public double calcForceExertedByY (Planet p) {
        return calcForceExertedBy(p) * (p.yyPos - yyPos) / calcDistance(p);
    }

    public double calcNetForceExertedByX (Planet[] allPlanets) {
        double NetForceExertedX = 0;
        for (Planet p : allPlanets) {
            if (!equals(p)) {
                NetForceExertedX += calcForceExertedByX(p);
            }
        }

        return NetForceExertedX;
    }

    public double calcNetForceExertedByY (Planet[] allPlanets) {
        double NetForceExertedY = 0;
        for (Planet p : allPlanets) {
            if (!equals(p)) {
                NetForceExertedY += calcForceExertedByY(p);
            }
        }

        return NetForceExertedY;
    }

    public void update (double dt, double fX, double fY) {
        double accelerateX = fX / mass;
        double accelerateY = fY / mass;
        xxVel += accelerateX * dt;
        yyVel += accelerateY * dt;
        xxPos += xxVel * dt;
        yyPos += yyVel * dt;
    }

    public void draw(){
		StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
	}
}
