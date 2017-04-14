package com.yjx.service;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReptileService {

	public static void main(String[] args) {
		ReptileService rs = new ReptileService();

		// 在你发起Http请求之前设置一下属性
		System.setProperty("http.proxyHost", "proxy.cmcc");
		System.setProperty("http.proxyPort", "8080");

		rs.getPicture();
	}

	public void getPicture() {
		URL url = null;
		URLConnection urlconn = null;
		InputStream is = null;
		OutputStream os = null;
		byte[] buffer = new byte[100];
		int temp = 0;

		DecimalFormat df = new DecimalFormat("000");
		try {
			for (int i = 1; i < 1000; i++) {
				String num = df.format(i);
				// 头像
				url = new URL("http://file.fgowiki.591mogu.com/fgo/head/" + num + ".jpg");
				// url = new URL("http://fgowiki.com/fgo/equip/" + num +
				// ".jpg");
				urlconn = url.openConnection();
				is = urlconn.getInputStream();
				os = new FileOutputStream("E:/my/FGO/head/" + num + ".jpg");
				// os = new FileOutputStream("E:/my/FGO/craft/" + num + ".jpg");
				while (true) {
					temp = is.read(buffer, 0, buffer.length);
					if (temp == -1) {
						break;
					}
					os.write(buffer, 0, temp);
				}
				System.out.println(num + ".jpg 获取成功！");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void getUrls() {
		URL url = null;
		URLConnection urlconn = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		String regex = "http://[\\w+\\.?/?]+\\.[A-Za-z]+";
		Pattern p = Pattern.compile(regex);
		try {
			url = new URL("http://fgowiki.com/");
			urlconn = url.openConnection();
			pw = new PrintWriter(new FileWriter("E:/my/FGO/url.txt"), true);// 这里我们把收集到的链接存储在了E盘底下的一个叫做url的txt文件中
			br = new BufferedReader(new InputStreamReader(urlconn.getInputStream()));
			String buf = null;
			while ((buf = br.readLine()) != null) {
				Matcher buf_m = p.matcher(buf);
				while (buf_m.find()) {
					pw.println(buf_m.group());
				}
			}
			System.out.println("获取成功！");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			pw.close();
		}
	}

}
