package de.mpg.eva.encryptor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Base64;
import android.util.Log;

public class MsgDigest {
	private String alg;
	private String msg;

	public MsgDigest(String alg, String msg) {
		this.alg = alg;
		this.msg = msg;
	}

	public String getAlg() {
		return alg;
	}

	public String getMsg() {
		return msg;
	}

	public String digest(int flag) {
		String res = "";
		try {
			MessageDigest md = MessageDigest.getInstance(getAlg());
			byte[] digested = md.digest(getMsg().getBytes());
			if (flag == 1)
				res = Base64.encodeToString(digested, Base64.DEFAULT);
			else {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < digested.length; ++i) {
					sb.append(Integer.toHexString((digested[i] & 0xFF) | 0x100)
							.substring(1, 3));
				}
				res = sb.toString();
			}
		} catch (NoSuchAlgorithmException e) {
			Log.e("Error", "Algorithms can not be found");
			e.printStackTrace();
		}
		return res;
	}

	/*
	 * public static void main(String[] args){ String[]
	 * algs={"MD5","SHA-1","SHA-256","SHA-384","SHA-512"}; MsgDigest md= new
	 * MsgDigest("MD5","msg"); for(String alg:algs){ md.setAlg(alg);
	 * System.out.println("Alg: "+alg+"; Res: "+md.digest()); } }
	 */
}
