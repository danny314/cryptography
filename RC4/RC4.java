package test.rc4;

import java.util.ArrayList;
import java.util.List;

public class RC4 {
	private static int S[] = new int[256];
	private static int K[] = new int[256];
	private static int prga_i = 0;
	private static int prga_j = 0;
	private static List<Integer> prgn = new ArrayList<Integer>();

	public static void main(String[] args) {
		int key[] = new int[] { 0x1, 0x2, 0x3, 0x4, 0x5 };
		System.out.println("Input bytes = " + args[0]);
		String inputStr = args[0].replaceAll(" ", "");
		// Initialize state array S
		for (int i = 0; i < 256; i++) {
			S[i] = i;
		}
		// Initialize key array K
		for (int i = 0; i < 256; i++) {
			K[i] = key[i % key.length];
		}
		// Complete initialization
		int j = 0;
		for (int i = 0; i < 256; i++) {
			j = (j + S[i] + K[i]) % 256;
			int temp = S[i];
			S[i] = S[j];
			S[j] = temp;
		}
		xor(inputStr);
		System.out.println("\n\nKey Stream used...");
		for (int rn : prgn) {
			System.out.print(rn + ", ");
		}
	}

	private static int getX() {
		prga_i = (prga_i + 1) % 256;
		prga_j = (prga_j + S[prga_i]) % 256;
		int temp = S[prga_i];
		S[prga_i] = S[prga_j];
		S[prga_j] = temp;
		int m = (S[prga_i] + S[prga_j]) % 256;
		int X = S[m];
		prgn.add(X);
		return X;
	}

	private static void xor(String inputStr) {
		int[] inputBytes = new int[inputStr.length() / 2];
		for (int m = 0; m < inputStr.length(); m = m + 2) {
			String hexValue = inputStr.substring(m, m + 2);
			inputBytes[m / 2] = Integer.parseInt(hexValue, 16);
		}
		System.out.print("\n\nOutput bytes = ");
		for (int k = 0; k < inputBytes.length; k++) {
			System.out.print(Integer.toHexString((inputBytes[k] ^ getX()))
					.toUpperCase() + " ");
		}
	}
}
