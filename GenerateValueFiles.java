package cn.guige.com;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class GenerateValueFiles {

	 private int baseW;

	    private String dirStr = "./res";

	    private final static String WTemplate = "<dimen name=\"x{0}\">{1}dp</dimen>\n";


	    private final static String VALUE_TEMPLATE = "values-sw{0}dp";

	    private static final String supportStr = "300;320;340;360;400;480;520;600;720;";

	    public GenerateValueFiles(int baseW) {
	        this.baseW = baseW;
	        File dir = new File(dirStr);
	        if (!dir.exists()) {
	            dir.mkdir();
	        }
	    }


	    public void generate() {
	        String[] vals = supportStr.split(";");
	        for(int i=0;i<vals.length;i++){
	        	 generateXmlFile(Integer.parseInt(vals[i]));
	        }
//	        for(String val : vals) {
//	            generateXmlFile(Integer.parseInt(val));
//	        }

	    }

	    private void generateXmlFile(int w) {

	        StringBuffer sbForWidth = new StringBuffer();
	        sbForWidth.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
	        sbForWidth.append("<resources>");
	        float cellw = w *2.0f / baseW;//2表示屏幕密度xhdpi

	        for (int i = 1; i < baseW; i++) {
	            sbForWidth.append(WTemplate.replace("{0}", i + "").replace("{1}",
	                    change(cellw * i/2) + ""));
	        }
	        sbForWidth.append(WTemplate.replace("{0}", baseW + "").replace("{1}",
	                w + ""));
	        sbForWidth.append("</resources>");

	        File fileDir = new File(dirStr + File.separator
	                + VALUE_TEMPLATE.replace("{0}", w + ""));
	        fileDir.mkdir();

	        File layxFile = new File(fileDir.getAbsolutePath(), "dimens.xml");
	        try {
	            PrintWriter pw = new PrintWriter(new FileOutputStream(layxFile));
	            pw.print(sbForWidth.toString());
	            pw.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
	    }

	    public static float change(float a) {
	        int temp = (int) (a * 100);
	        return temp / 100f;
	    }

	    public static void main(String[] args) {
	      //基准的屏幕最小宽度，可以根据实际情况制定。
	        int baseW = 720;//720*1280(xhdpi)屏幕宽度为360dp
	        new GenerateValueFiles(baseW).generate();
	    }
}
