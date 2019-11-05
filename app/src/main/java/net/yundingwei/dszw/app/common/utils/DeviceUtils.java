package net.yundingwei.dszw.app.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import net.yundingwei.dszw.app.common.constants.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;



public class DeviceUtils {

	private static final String INSTALLATION = "install";
	
	/**
	 * 获取设备唯一识别码
	 * @return
	 */
	public synchronized static String getDeviceId(Context context) {
		try {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			if(tm != null) {
				Map<String, String> map = new HashMap<String, String>();
				//返回当前移动终端的唯一标识
				String imei = tm.getDeviceId();
				if(!TextUtils.isEmpty(imei)) {
					return imei;
				}
			}

			String imei = "";
			File file = FileUtils.createNewFile(Config.DEVICE_PATH, "device.txt");
			if (file.exists()) {
				FileInputStream fis = new FileInputStream(file);
				BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
				String content = "";
				while ((content = reader.readLine()) != null) {
					imei += content;
				}
				reader.close();
				if (StringUtils.isEmpty(imei)) {
					imei = UUID.randomUUID().toString();
					FileUtils.write(imei.getBytes(), Config.DEVICE_PATH, "device.txt", false);
				}
			} else {
				imei = UUID.randomUUID().toString();
				FileUtils.write(imei.getBytes(), Config.DEVICE_PATH, "device.txt", false);
			}

			return imei;
		}catch (Exception e) {
			String imei = "";
			try {
				File file = FileUtils.createNewFile(Config.DEVICE_PATH, "device.txt");
				if (file.exists()) {
					FileInputStream fis = new FileInputStream(file);
					BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
					String content = "";
					while ((content = reader.readLine()) != null) {
						imei += content;
					}
					reader.close();
					if (StringUtils.isEmpty(imei)) {
						imei = UUID.randomUUID().toString();
						FileUtils.write(imei.getBytes(), Config.DEVICE_PATH, "device.txt", false);
					}
				} else {
					imei = UUID.randomUUID().toString();
					FileUtils.write(imei.getBytes(), Config.DEVICE_PATH, "device.txt", false);
				}
			}catch (Exception ex) {
				String error = ex.getMessage();
			}finally {
				return imei;
			}
		}
	}


	/**
	 * 获取设备唯一识别码
	 * @return
	 */
	public synchronized static Map<String,String> getDeviceInfo(Context context) {

		try {
			TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			if(tm != null) {
				Map<String,String> map = new HashMap<String,String>();
				//返回当前移动终端的唯一标识
				String imei = tm.getDeviceId();
				if(StringUtils.isEmpty(imei)){
					imei = "";
					File file = FileUtils.createNewFile(Config.DEVICE_PATH, "device.txt");
					if(file.exists()){
						FileInputStream fis = new FileInputStream(file);
						BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
						String content = "";
						while((content = reader.readLine()) != null){
							imei += content;
						}
						reader.close();
						if(StringUtils.isEmpty(imei)){
							imei = UUID.randomUUID().toString();
							FileUtils.write(imei.getBytes(), Config.DEVICE_PATH, "device.txt", false);
						}
					}else{
						imei = UUID.randomUUID().toString();
						FileUtils.write(imei.getBytes(), Config.DEVICE_PATH, "device.txt", false);
					}


				}
				//国际移动用户识别码
				String imsi = tm.getSubscriberId();
				//返回手机号码
				String mobile = tm.getLine1Number();
				//返回ISO标准的国家码，即国际长途区号
				String networkCountryIso = tm.getNetworkCountryIso();
				//返回移动网络运营商的名字(SPN)
				String networkOperatorName = tm.getNetworkOperatorName();
				/**
				 * 手机制式
				 * PHONE_TYPE_CDMA 手机制式为CDMA，电信
				 * PHONE_TYPE_GSM 手机制式为GSM，移动和联通
				 * PHONE_TYPE_NONE 手机制式未知
				 */
				int type = tm.getPhoneType();
				String phoneType = "NONE";
				switch (type){
					case TelephonyManager.PHONE_TYPE_CDMA:
						phoneType = "CDMA";
						break;
					case TelephonyManager.PHONE_TYPE_GSM:
						phoneType = "GSM";
						break;
					case TelephonyManager.PHONE_TYPE_SIP:
						phoneType = "SIP";
						break;
					case TelephonyManager.PHONE_TYPE_NONE:
						phoneType = "NONE";
						break;
				}

				//返回SIM卡提供商的国家代码
				String simCountryIso = tm.getSimCountryIso();
				//返回Sim卡运营商的名字
				String simOperatorName = tm.getSimOperatorName();
				//返回SIM卡的序列号(IMEI)
				String simSerialNumber = tm.getSimSerialNumber();

				map.put("imei", imei);
				map.put("imsi", imsi);
				map.put("mobile", mobile);
				map.put("networkCountryIso", networkCountryIso);
				map.put("networkOperatorName", networkOperatorName);
				map.put("phoneType", phoneType);
				map.put("simCountryIso", simCountryIso);
				map.put("simOperatorName", simOperatorName);
				map.put("simSerialNumber", simSerialNumber);
				return map;

			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 得到当前的手机网络类型
	 * NETWORK_TYPE_GPRS：          常量值：1         网络类型：GPRS （联通2g）
	 * NETWORK_TYPE_EDGE：          常量值：2       网络类型：EDGE（移动2g）
	 * NETWORK_TYPE_CDMA ：         常量值：4       网络类型： CDMA （电信2g）
	 * NETWORK_TYPE_1xRTT：         常量值：7        网络类型：1xRTT
	 * NETWORK_TYPE_UMTS：          常量值：3    网络类型：UMTS（联通3g）
	 * NETWORK_TYPE_EVDO_0：      常量值：5     网络类型：EVDO  版本0.（电信3g）
	 * NETWORK_TYPE_EVDO_A：      常量值：6     网络类型：EVDO   版本A （电信3g）
	 * NETWORK_TYPE_EVDO_B：      常量值：12   网络类型：EVDO  版本B（电信3g）
	 * NETWORK_TYPE_HSDPA：        常量值：8      网络类型：HSDPA（联通3g）
	 * NETWORK_TYPE_EHRPD：      常量值：14    网络类型：eHRPD 3g
	 * NETWORK_TYPE_HSPA：         常量值：10     网络类型：HSPA 3g
	 * NETWORK_TYPE_IDEN：          常量值：11      网络类型：iDen 3g
	 * NETWORK_TYPE_HSUPA：       常量值：9     网络类型：HSUPA 3G
	 * NETWORK_TYPE_HSPAP：       常量值：15   网络类型：HSPA+ 4g
	 * NETWORK_TYPE_LTE：             常量值：13       4G
	 * NETWORK_TYPE_UNKNOWN：常量值：0  网络类型：未知
	 * @param context
	 * @return
	 */
	public static String getCurrentNetType(Context context) {
		String type = "";
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info == null) {
			type = null;
		} else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
			type = "WIFI";
		} else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
			int subType = info.getSubtype();

			switch (subType){
				case TelephonyManager.NETWORK_TYPE_GPRS:
				case TelephonyManager.NETWORK_TYPE_EDGE:
				case TelephonyManager.NETWORK_TYPE_CDMA:
				case TelephonyManager.NETWORK_TYPE_1xRTT:
					type = "2G";
					break;
				case TelephonyManager.NETWORK_TYPE_EVDO_0:
				case TelephonyManager.NETWORK_TYPE_EVDO_B:
				case TelephonyManager.NETWORK_TYPE_HSDPA:
				case TelephonyManager.NETWORK_TYPE_EHRPD:
				case TelephonyManager.NETWORK_TYPE_HSPA:
				case TelephonyManager.NETWORK_TYPE_IDEN:
				case TelephonyManager.NETWORK_TYPE_HSUPA:
					type = "3G";
					break;
				case TelephonyManager.NETWORK_TYPE_HSPAP:
				case TelephonyManager.NETWORK_TYPE_LTE:
					type = "4G";
					break;
				case TelephonyManager.NETWORK_TYPE_UNKNOWN:
					type = "未知";
					break;
			}
		}
		return type;
	}

	/**
	 * 判断是否联网
	 * 
	 * @return
	 */
	public static boolean connected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager != null) {
			// 获取网络连接管理的对象
			NetworkInfo info = connectivityManager.getActiveNetworkInfo();
			if (info != null && info.isConnected()) {
				// 判断当前网络是否已经连接
				if (info.getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * 判断是否安卓某个应用
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean isInstall(Context context, String packageName) {
		PackageManager packageManager = context.getPackageManager();
		// 获取所有已安装程序的包信息
		List<PackageInfo> packages = packageManager.getInstalledPackages(0);
		for (int i = 0; i < packages.size(); i++) {
			if (packages.get(i).packageName.equalsIgnoreCase(packageName)){
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否是WiFi联网
	 * @param context
	 * @return
	 */
	public static boolean isWiFiNet(Context context){
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
	}
	
	/**
	 * 取得app版本
	 * @return
	 */
	public static String getVersion(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 取得app版本
	 * @return
	 */
	public static Bundle getVersionInfo(Context context) {
		try {
			Bundle bundle = new Bundle();
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),0);
			if(info != null) {
				bundle.putString("name", info.versionName);
				bundle.putInt("code", info.versionCode);
				return bundle;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 隐藏软键盘
	 * @param context
	 * @param view
	 */
	public static void showSoftKeyboard(Context context, View view){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
		
	}
	
	
	/**
	 * 隐藏软键盘
	 * @param context
	 * @param view
	 */
	public static void hideSoftKeyboard(Context context, View view){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);//隐藏软键盘
		
	}
	
	/**
	 * 隐藏软键盘
	 * @param context
	 * @param binder
	 */
	public static void hideSoftKeyboard(Context context, IBinder binder){
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(binder, 0);//隐藏软键盘
	}


	public static void startCropActivity(Activity activity, Uri uri, int aspectX, int aspectY, int outputX, int outputY, int requestCode){
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		//裁剪比例
		intent.putExtra("aspectX", aspectX);
		intent.putExtra("aspectY", aspectY);
		//裁剪像素大小
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);

		activity.startActivityForResult(intent, requestCode);
	}

}
