#include <iostream>
#include <iomanip>
#include <sstream>
#include <ctime>
#include "cryptopp/integer.h"
#include "cryptopp/aes.h"
#include "cryptopp/osrng.h"
#include "cryptopp/modes.h"
#include "cryptopp/hex.h"
using namespace std;
using namespace CryptoPP;
string formatHex(string s) {
	return s.insert(4, " ").insert(9, " ").insert(14, " ").insert(19, " ").insert(
			24, " ").insert(29, " ").insert(34, " ");
}
int main(int, char**) {
	unsigned char key[16] = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
			0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01 };
	unsigned char plain[16] = { 0x80, 0x00, 0x70, 0x00, 0x60, 0x00, 0x50, 0x00,
			0x40, 0x00, 0x30, 0x00, 0x20, 0x00, 0x10, 0x00 };
	unsigned char cipher[16];
	unsigned char recovered[16];
	string plainStr, cipherStr, recoveredStr, keyStr;
	int millions = 30;
	int multiplier = 1000000;
	int numOfOperations = millions * multiplier;
	try {
		StringSource(plain, 16, true, new HexEncoder(new StringSink(plainStr)));
		cout << "P: " << formatHex(plainStr) << endl;
		StringSource ss(key, 16, true, new HexEncoder(new StringSink(keyStr)));
		cout << "K: " << formatHex(keyStr) << endl;
		cout << "Encrypting P... " << endl;
		ECB_Mode<AES>::Encryption e;
		e.SetKey(key, 16);
		e.ProcessData((byte*) cipher, (byte*) plain, 16);
		StringSource(cipher, 16, true,
				new HexEncoder(new StringSink(cipherStr)));
		cout << "C: " << formatHex(cipherStr) << endl;
		cout << "Decrypting C... " << endl;
		ECB_Mode<AES>::Decryption d;
		d.SetKey(key, 16);
		d.ProcessData((byte*) recovered, (byte*) cipher, 16);
		StringSource(recovered, 16, true,
				new HexEncoder(new StringSink(recoveredStr)));
		cout << "P: " << formatHex(recoveredStr) << endl << endl;
		cout << "Performing " << millions << " million AES encryptions..."
				<< endl;
		const clock_t enc_begin_time = clock();
		for (int i(0); i < numOfOperations; ++i) {
			cipherStr.clear();
			e.ProcessData((byte*) cipher, (byte*) plain, 16);
			StringSource(cipher, 16, true,
					new HexEncoder(new StringSink(cipherStr)));
		}
		cout << "Done" << endl;
		cout << "Total encryption time = "
				<< float(clock() - enc_begin_time) / CLOCKS_PER_SEC << "
		seconds
		" << endl;
		cout << "Mean encryption time = "
				<< float(clock() - enc_begin_time) / (numOfOperations *
				CLOCKS_PER_SEC) << " seconds" << endl;
		cout << "\nPerforming " << millions << " million AES decryptions..."
				<< endl;
		const clock_t dec_begin_time = clock();
		for (int i(0); i < numOfOperations; ++i) {
			recoveredStr.clear();
			d.ProcessData((byte*) recovered, (byte*) cipher, 16);
			StringSource(recovered, 16, true,
					new HexEncoder(new StringSink(recoveredStr)));
		}
		cout << "Done" << endl;
		cout << "Total decryption time = "
				<< float(clock() - dec_begin_time) / CLOCKS_PER_SEC << "
		seconds
		" << endl;
		cout << "Mean decryption time = "
				<< float(clock() - dec_begin_time) / (numOfOperations *
				CLOCKS_PER_SEC) << " seconds" << endl;
		cout << "Processor: Intel(R) Xeon(R) CPU W3530 @ 2.80GHz" << endl;
	} catch (CryptoPP::Exception& e) {
		cerr << e.what() << endl;
		exit(1);
	}
	return 0;
}
