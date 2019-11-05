//Please note that all commented code is a downgraded version
//of the application using int. The app now uses Big Integer

package rsa_encryption;

import java.math.BigInteger;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class RSA_Encryption {

    public static void main(String[] args) {
        
        Random rando = new Random();
        Scanner keyb = new Scanner(System.in);
        
        BigInteger p = BigInteger.probablePrime(512, rando);    //Generate P using BigInteger.probabePrime()
        System.out.println("P generated");
        BigInteger q = BigInteger.probablePrime(512, rando);    //Generate Q using BigInteger.probabePrime()
        System.out.println("Q generated");
        BigInteger n = p.multiply(q);   //Generate N = P * Q;
        System.out.println("N generated");
        BigInteger phi = createPhi(p, q);   //Generate Phi(n) = (P - 1) * (Q - 1)
        System.out.println("PHI generated");
        BigInteger e = genE(phi);   //Generate E WHERE (1 < E < Phi(n)) and e is coprime with Phi (GCD(e, phi) == 1)
        System.out.println("E genrated");
        BigInteger d = extendedEuclid(e, phi)[1];   //Generate D = D * E == 1(mod Phi(n)) D is the inverse
        System.out.println("D generated");
        
        System.out.println("p = " + p);
        System.out.println("q = " + q);
        System.out.println("n = " + n);
        System.out.println("phi = " + phi);
        System.out.println("e = " + e);
        System.out.println("d = " + d);
        
        System.out.println("Please enter the message you wish to encrypt & decrypt:");
        String text = keyb.nextLine();
        System.out.println("Original message = " + text);
        BigInteger message = stringToCipher(text);
        System.out.println("Ciphered message = " + message);
        BigInteger ciphertext = encrypt(e, n, message);
        System.out.println("Encrypted message = " +ciphertext);
        BigInteger decryptedText = decrypt(d, n, ciphertext);
        System.out.println("Decrypted ciphertext = " + decryptedText);
        String ogMessage = cipherToString(decryptedText);
        System.out.println("Decrypted & restored ciphertext = " + ogMessage);
    }
    
    public static BigInteger gcd(BigInteger a, BigInteger b)
    {
        if(b.equals(BigInteger.ZERO))
        {
            return a;
        }
        else
        {
            return gcd(b, b.mod(a));
        }
    }
    
    public static List<Integer> retrievePrimes()
    {
        List<Integer> primes = new ArrayList<>();
        int limit = 100;
        for(int i = 1; i < limit; i++)
        {
            boolean isPrime = true;
            
            for(int j = 2; j < i; j++)
            {
                if(i % j == 0)
                {
                    isPrime = false;
                    break;
                }
            }
            
            if(isPrime == true)
            {
                System.out.print(i + ", ");
                primes.add(i);
            }
        }
        
        return primes;
    }
    
    public static boolean coprimeCheck(BigInteger a, BigInteger b) 
    {
        
        boolean check = false;
        BigInteger gcd = a.gcd(b);
    
        if(gcd.intValue() == 1)
        {
            check = true;
        }
        return check;
    }
    
    public static BigInteger encrypt(BigInteger e, BigInteger n, BigInteger message)
    {
        
        BigInteger cipherText = message.modPow(e, n);
        
        return cipherText;
    }
    
    public static BigInteger decrypt(BigInteger d, BigInteger n, BigInteger cipherText)
    {
//        BigInteger b1 = BigInteger.valueOf(d);
//        BigInteger b2 = BigInteger.valueOf(n);
        BigInteger text = cipherText.modPow(d, n);
        
//        BigInteger mod = BigInteger.valueOf(n);
//        BigInteger text = ciphertext.pow(e);
//        text = text.mod(mod);
        return text;
    }
    
    public static BigInteger stringToCipher(String text) 
    {
	text = text.toUpperCase(); //Change message to uppercase in order to find the char value of each character
	String stringCipher = "";
                
	for (int i = 0; i < text.length(); i++) 
        {
            int ch = (int) text.charAt(i);  //Transalte individual character to a char value
            stringCipher = stringCipher + ch;   //Add individual char value to stringCipher until at the end of the String
	}
                
	BigInteger bigCipher = new BigInteger(String.valueOf(stringCipher));    //Translate char value of message into BigInteger in order to encrypt
	return bigCipher;
    }
    
    public static String cipherToString(BigInteger text)
    {
        String stringCipher = text.toString();
        String restoredText = "";
        
        for (int i = 0; i < stringCipher.length(); i = i + 2)
        {
            int temp = Integer.parseInt(stringCipher.substring(i, i+2));    //Seperate the char value to translate them back to String
            char ch = (char) temp;  //Passing the seperated values and changing them to String
            restoredText = restoredText + ch; //Adding the seperated characters together to recreate the original message
        }
        return restoredText;
    }
    
    public static BigInteger createPhi(BigInteger p, BigInteger q)
    {
        p = p.subtract(BigInteger.ONE); //p = p-1;
        q = q.subtract(BigInteger.ONE); //q = q-1;
        BigInteger phi = p.multiply(q); //phi = (p-1)*(q-1);
        return phi;
    }
    
    public static BigInteger[] extendedEuclid(BigInteger a, BigInteger b) 
    {
		if (b.equals(BigInteger.ZERO)) return new BigInteger[] 
                {
			a, BigInteger.ONE, BigInteger.ZERO //[a, 1, 0]
		}; // { a, 1, 0 }
		BigInteger[] vals = extendedEuclid(b, a.mod(b));
		BigInteger d = vals[0];
		BigInteger p = vals[2];
		BigInteger q = vals[1].subtract(a.divide(b).multiply(vals[2]));
		return new BigInteger[] {
			d, p, q
		};
    }
    
    public static BigInteger genE(BigInteger phi) 
    {
		Random rando = new Random();
		BigInteger e = new BigInteger(1024, rando);
			while (e.min(phi).equals(phi) && !gcd(e, phi).equals(BigInteger.ONE)) // while phi is smaller than e and gcd(e, phi) == 1, look for a new e
                        { 
				e = new BigInteger(1024, rando);
			} 
		return e;
	}
    
}
