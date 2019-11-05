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
//        int[] keys = new int[2];
//        List<Integer> primes = retrievePrimes();
//        
//        System.out.println("Please choose your keys from the list above:");
//        keys[0] = keyb.nextInt();
//        keys[1] = keyb.nextInt();
//        System.out.println("Keys chosen = " + keys[0] + ", " + keys[1]);
//        
//        int n = keys[0] * keys[1];
//        int phi = (keys[0] - 1) * (keys[1] - 1); //phi = (p-1)*(q-1)
//        int e = 0;
//        boolean coprime = false;
//        
//        while(coprime == false)
//        {
//            e = 1 + rando.nextInt(phi - 1);
//            
//            if(coprimeCheck(phi, e) && coprimeCheck(n, e))
//        {
//            System.out.println("e is confirmed coprime with n and fi");
//            coprime = true;
//        }   
//        }
//        
//        int d = 0;
//        
//        while(d == 0)
//        {
//            for(int i = 2; i < n; i++)
//            {
//                if((e * i) % phi == 1) //Reiterate d*e(mod phi) until it is equal to 1
//                {
//                    d = i; //When found, d will finally be given its value
//                    break;
//                }
//            }
//        }
        
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
        
//        while(coprime == false)
//        {     
//            if(coprimeCheck(e, phi))
//            {
//                coprime = true;
//            }
//            else
//            {
//                e = new BigInteger(512, rando);
//            }
//        }
        
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
    
//    public static int createKeys(List<Integer> list)
//    {
//        List<Integer> primes = list;
//        Random rando = new Random();
//        List<Integer> keys = new ArrayList<>();
//        for(int i = 0; i <= keys.size(); i++)
//        {
//            Integer a = 0 + rando.nextInt(primes.size());
//            keys.add(primes.get(a));
//        }
//        return keys;
//    }
    
//    public static boolean gcd(int a, int b)
//    {
//        boolean gcd = true;
//        
//        for (int i = 2; i <= a && i <= b; i++)
//        {
//            if(a % i == 0 && b % i == 0)
//            {
//                gcd = false;
//            }
//        }
//        
//        return gcd;
//    }
    
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
    
//    public static boolean coprimeCheck(int a, int b) 
//    {
//        
//        boolean check = false;
//        BigInteger b1 = BigInteger.valueOf(a);
//        BigInteger b2 = BigInteger.valueOf(b);
//        BigInteger gcd = b1.gcd(b2);
//    
//        if(gcd.intValue() == 1)
//        {
//            check = true;
//        }
//        return check;
//    }
    
    public static BigInteger encrypt(BigInteger e, BigInteger n, BigInteger message)
    {
//        BigInteger b1 = BigInteger.valueOf(e);
//        BigInteger b2 = BigInteger.valueOf(n);
        
        BigInteger cipherText = message.modPow(e, n);
        
//        BigInteger text = BigInteger.valueOf(message);
//        BigInteger mod = BigInteger.valueOf(n);
//        BigInteger cipherText = text.pow(d);
//        cipherText = cipherText.mod(mod);
        
//        for(int i = 1; i <= p; i++)
//        {
//            cipherText = cipherText * text;
//        }`
//        
//        while (cipherText > n)
//        {
//            cipherText = cipherText - n;
//        }
        
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
