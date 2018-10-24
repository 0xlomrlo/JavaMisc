import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 *
 * @author WALEED
 */

public class CG {

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter Image PATH: ");
        String ImgPath = input.next();
        System.out.print("Enter TXT file output PATH: ");
        String OutputImgPath = input.next();

        getImgRGB(ImgPath, OutputImgPath);
    }

    public static double[] RGBtoHSV(double r, double g, double b) {
        r = r / 255.0;
        g = g / 255.0;
        b = b / 255.0;

        double h, s, v;
        double min, max, delta;

        min = Math.min(Math.min(r, g), b);
        max = Math.max(Math.max(r, g), b);
        delta = max - min;

        if (r == max) {
            h = (60 * ((g - b) / delta) + 360) % 360;
        } else if (g == max) {
            h = (60 * ((b - r) / delta) + 120) % 360;
        } else {
            h = (60 * ((r - g) / delta) + 240) % 360;
        }

        if (max != 0) {
            s = (delta / max) * 100;
        } else {
            s = 0;
        }
        v = max * 100;

        return new double[]{h, s, v};
    }

    public static void getImgRGB(String ImgPath, String OutputImgPath) throws IOException {
        try (FileWriter file = new FileWriter(OutputImgPath)) {
            BufferedImage bi = ImageIO.read(new File(ImgPath));

            for (int x = 0; x < bi.getWidth(); x++) {
                for (int y = 0; y < bi.getHeight(); y++) {
                    int pixel = bi.getRGB(x, y);
                    int red = (pixel >> 16) & 0xff;
                    int green = (pixel >> 8) & 0xff;
                    int blue = (pixel) & 0xff;
                    double[] t = RGBtoHSV(red, green, blue);
                    file.write("H: " + t[0] + ", S: " + t[1] + ", V: " + t[2]);
                    file.write(System.getProperty("line.separator"));

                }
            }
        }
        System.out.println("Done...");
    }
}
