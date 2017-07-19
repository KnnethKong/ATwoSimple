package com.kxf.atwosimple;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;

/**
 * 
 * @ClassName: MyProperUtil
 * @Description: [功能描述]
 * @author kxf
 * @date 2016-3-24 下午4:42:06
 * @since JDK 1.6
 */
public class MyProperUtil {
	private static Properties urlProps;
	public static String one_dock = "";
	public static String two_dock = "";
	public static String thre_dock = "";
	public static String four_dock = "";

	public static Properties getProperties(Context c) {
		Properties props = new Properties();
		InputStream in;
		try {
			in = c.getAssets().open("appConfig.properties");
			props.load(in);
			urlProps = props;
			in.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return urlProps;
	}

	public static void writeProperties(Context c, String p_name, String p_vlaue) {
		Properties props = new Properties();
		try {
			InputStream in = c.getAssets().open("appConfig.properties");
			props.setProperty(p_name, p_vlaue);
			urlProps = props;
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
