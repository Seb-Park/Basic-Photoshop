public class Vector {

    public double angle, magnitude, xcom, ycom;

    public Vector(double pAngle, double pMagnitude){
        angle = pAngle;
        magnitude = pMagnitude;
        ycom = Math.sin(Math.toRadians(angle)) * magnitude;
        xcom = Math.cos(Math.toRadians(angle)) * magnitude;
    }

    public Vector(double pxcom, double pycom, boolean placeholder){
        xcom = pxcom;
        ycom = pycom;
        magnitude = Math.sqrt(Math.pow(pxcom,2)+Math.pow(pycom,2));
        angle = Math.asin(pycom/magnitude);
    }

    public void reorient(double delta){
        angle += delta;
        if(angle<0){
            angle += 360;
        }
        if(angle>360)
        {
            angle = 360-angle;
        }
        ycom = Math.sin(Math.toRadians(angle)) * magnitude;
        xcom = Math.cos(Math.toRadians(angle)) * magnitude;
    }

    public void recalculate(double pAngle, double pMagnitude){
        angle = pAngle;
        magnitude = pMagnitude;
        ycom = Math.sin(Math.toRadians(angle)) * magnitude;
        xcom = Math.cos(Math.toRadians(angle)) * magnitude;
    }

    public void recalculate(double pxcom, double pycom, boolean placeholder){
        xcom = pxcom;
        ycom = pycom;
        magnitude = Math.sqrt(Math.pow(pxcom,2)+Math.pow(pycom,2));
        angle = Math.asin(pycom/magnitude);
    }

    public void scale(double scale){
        magnitude*=scale;
        ycom*=scale;
        xcom*=scale;
    }

    public void addVector(double pxcom, double pycom){
        xcom+=pxcom;
        ycom+=pycom;
        magnitude = Math.sqrt(Math.pow(pxcom,2)+Math.pow(pycom,2));
        angle = Math.asin(pycom/magnitude);
    }

    public static double[] angleToVector (double angle, double magnitude){
        double ymag = Math.sin(Math.toRadians(angle)) * magnitude;
        double xmag = Math.cos(Math.toRadians(angle)) * magnitude;
        return new double[] {xmag, ymag};
    }

    public static double vectorToAngle(double xmag, double ymag){
        double returnedangle = Math.asin(ymag/Math.sqrt(Math.pow(xmag,2)+Math.pow(ymag,2)));
        return returnedangle;
    }

    public static double[] vectorToAngleAndMagnitude(double xmag, double ymag){
        double mag = Math.sqrt(Math.pow(xmag,2)+Math.pow(ymag,2));
        double returnedangle = Math.asin(ymag/mag);
        return new double[] {returnedangle,mag};
    }

    public void multiplyByMatrix(){

    }

}
