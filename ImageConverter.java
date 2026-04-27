import javax.imageio.ImageIO;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public class ImageConverter {
    public static void main(String[] args) throws IOException {
        File file = new File("nina.png");
        BufferedImage img = ImageIO.read(file);
        int width = img.getWidth();
        int height = img.getHeight();
        int[][] pixels = new int[height][width];
        Raster raster = img.getData();
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                pixels[i][j] = raster.getSample(j, i, 0);
            }
        }

        System.out.println(Arrays.deepToString(pixels));

        double[][] scaledPixels = scalePixels(pixels);
        // It'ssoblank,it'ssolong/Theanswerstendtobesuperficialclichés/Whethermyfingerstrembleornot/I'mcurrentlyatacrossroads/Youwon'tfindonanymap/WhatshouldIusetoguideme?/Peopleoftensaytofollowthebook/SomanywordsIdon'tunderstand/Todayisnodifferentthanthatday/Willfillinginthisblanksolveeverythingsomeday?/Howwouldyoumoveforward?/It'ssoblank,it'ssolong/Theanswerstendtobesuperficialclichés/Whethermyfingerstrembleornot/There'snorightanswer,there'snolosing/I'vebeenmeallmylifeThat'sallthereis/NomatterhowmuchIfight,there'snotomorrow/Blamingitonauselessmap,andnow/I'mataloss,but/EvenifIpretendtobestrong,theresultwillprobablybethesame/Icramsomuchintoitthatit'sabouttooverflow/Lookingsidewaysatotherpeople'sbox/Istillcan'tevenfakeasmileproperly/Iftherewerenoblankslikethis,Youwouldthink/It'swrongtothinkeverythingisfinelikethis/It'sempty,there'snothing/Idon'tknowifit'sgoneorifit'sbegun,but/There'snowaybuttogoforward,right?/What'sthecorrectanswer?There'snovalue/Iwilllivemylife,Ican'tliveasanyoneelse/EvenifIstirthingsupmore,there'snotomorrow,ah/NomatterhowmuchIstruggle,there'snotomorrow
        String repeatText = "Imconfessingmylovetoyou";
        String finalHTML = printHTML(scaledPixels, repeatText);
        String finalCSS = printCSS(scaledPixels, repeatText);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("html.txt"))) {
            writer.write(finalHTML);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("css.txt"))) {
            writer.write(finalCSS);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static double[][] scalePixels(int[][] arr) {
        double[][] scaled = new double[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                scaled[i][j] = Math.round((arr[i][j] / 255.0) * 10000.0) / 10000.0;
                // round to nearest ten-thousandths place
            }
        }
        return scaled;
    }

    public static String printHTML(double[][] pixels, String text) {
        String html = "";
        int textIndex = 0;
        int classIndex = 0;
        for (int i = 0; i < pixels.length; i++) {
            html += "<div class=\"row\">";
            for (int j = 0; j < pixels[i].length; j++) {
                String line = "<p class=\"" + "t" + classIndex + "\">" + text.charAt(textIndex)
                        + "</p>";
                textIndex++;
                if (textIndex == text.length()) {
                    textIndex = 0;
                }
                classIndex++;
                html += line;
            }
            html += "</div>";
        }
        return html;
    }

    public static String printCSS(double[][] pixels, String text) {
        String css = "";
        int classIndex = 0;
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                int brightness = (int) (pixels[i][j] * 255);
                css += ".t" + classIndex + " {color: rgb(" + brightness + "," + brightness + "," + brightness
                        + ");}";
                classIndex++;
            }
        }
        return css;
    }

}
