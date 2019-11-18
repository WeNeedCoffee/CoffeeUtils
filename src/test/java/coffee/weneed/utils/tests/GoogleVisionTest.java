package coffee.weneed.utils.tests;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import coffee.weneed.utils.google.vision.GoogleVisionException;
import coffee.weneed.utils.google.vision.GoogleVisionUtil;

public class GoogleVisionTest {
	public static void main(String[] args) {
		List<String> images = new ArrayList<>();
		images.add("https://i.imgur.com/7Q9B209.png");
		for (String s : images) {
			try {
				System.out.println(GoogleVisionUtil.detectImage(new URL(s)).toJSON().toString());
			} catch (IOException | GoogleVisionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
}
