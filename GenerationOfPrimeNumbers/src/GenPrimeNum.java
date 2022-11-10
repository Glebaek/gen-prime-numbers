import java.lang.Math;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;


public class GenPrimeNum {

    public static int arrayLength(long length){
        int arrayLength = 1;
        while(length >= 17) {
            length = length/2;
            arrayLength++;
        }
        return arrayLength;
    }

//2222
    public static int[] sequenceT(int length, int arrayLength){
        int[] sequence;
        sequence = new int[arrayLength];

        for(int i = 0; i < arrayLength; i++) {
            sequence[i] = length;
            length = length/2;
        }
        return sequence;
    }

    public static boolean isPrime(long number) {
        int sqrt = (int) Math.sqrt(number) + 1;
        for (int i = 2; i < sqrt; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

//3333
    public static long minPrime(int amountOfBit){
        int border1 = (int) Math.pow(2,amountOfBit - 1);
        int border2 = (int) Math.pow(2,amountOfBit) - 1;
        long minPrime = border1;

        if(minPrime % 2 != 1)
            minPrime++;

        while(!isPrime(minPrime)){
            minPrime = minPrime + 2;
        }

        return minPrime;
    }

    public static BigInteger checkRange(BigInteger n, int amountOfBit){
        BigInteger N = n;
        BigInteger border1 =  BigInteger.TWO.pow(amountOfBit - 1);

        while(N.compareTo(border1) == -1)
            N = N.pow(2);

        return N;
    }

//6666
    public static long[] sequenceY(long[] sequenceY, long c, int rm){
        if(rm == 0)
            return null;

        sequenceY[0] = ((19381 * sequenceY[0]) + c) % (int) Math.pow(2,16);
        for(int i = 1; i < rm; i++){
            sequenceY[i] = ((19381 * sequenceY[i-1]) + c) % (int) Math.pow(2,16);
        }

        return sequenceY;
    }

//7777
    public static BigInteger sumY(long[] sequenceY, int rm){
        BigInteger sumY = BigInteger.valueOf(0);
        BigInteger two = BigInteger.TWO;
        BigInteger sequence = null;

        for(int i = 0; i < rm; i++){
            sequence = sequence.valueOf(sequenceY[i]);
            sumY = sumY.add(sequence.multiply(two.pow(161)));
        }

        return sumY;
    }


    public static BigInteger calculateN(int m, BigInteger sumY, BigInteger pRight, int[] sequenceT, int rm) {
        BigInteger result = null;
        BigDecimal first = null;
        BigDecimal second = null;
        BigDecimal pR = new BigDecimal(pRight);
        BigDecimal Y = new BigDecimal(sumY);

        first = BigDecimal.valueOf(2).pow(sequenceT[m-1]-1).divide(pR, RoundingMode.CEILING);
        second = Y.multiply(BigDecimal.valueOf(2).pow(sequenceT[m-1]-1));
        second = second.divide(pR.multiply(BigDecimal.valueOf(2).pow(16*rm)), RoundingMode.FLOOR);
        first = first.add(second);

        result = first.toBigInteger();
        if(!(result.mod(new BigInteger("2")).equals(BigInteger.ZERO)))
            result = result.add(BigInteger.valueOf(1));

        return result;
    }

//13
    public static boolean checkIfIsPrime(BigInteger n, int k, BigInteger pRight, BigInteger pLeft){
        boolean check = true;

        BigInteger result = BigInteger.TWO.modPow(pRight.multiply(n.add(BigInteger.valueOf(k))), pLeft);
        if(result.compareTo(BigInteger.ONE) != 0)
            check = false;
        result = BigInteger.TWO.modPow(n.add(BigInteger.valueOf(k)), pLeft);
        if(result.compareTo(BigInteger.ONE) == 0)
            check = false;

        return check;
    }

    public static void main(String[] args) {
        long xCongruent = 24265;
        long cCongruent = 7341;
        int tLength = 2048;
        int lengthOfSeq = arrayLength(tLength);
        int s = lengthOfSeq;
        int m = s - 1;
        int rm;
        int k = 0;
        int[] sequenceT;
        long[] sequenceY;
        boolean check = true;
        boolean enter = true;
        BigInteger sumY = null;
        BigInteger n = null;
        BigInteger pLeft = null;
        BigInteger pRight = null;

        sequenceT = new int[lengthOfSeq];
        sequenceT = sequenceT(tLength, lengthOfSeq);

        pRight = BigInteger.valueOf(minPrime(sequenceT[s-1]));
        sequenceY = new long[tLength/16];
        sequenceY[0] = xCongruent;
        do {
            rm = (int)Math.ceil((double)sequenceT[m]/16);
            do {
                while (check) {
                    if (enter) {
                        sequenceY = sequenceY(sequenceY, cCongruent, rm);
                        sumY = sumY(sequenceY, rm);
                        sequenceY[0] = sequenceY[rm - 1];
                        n = calculateN(m, sumY, pRight, sequenceT, rm);
                        k = 0;
                    }
                    n = checkRange(n, sequenceT[m]);
                    pLeft = (pRight.multiply(n.add(BigInteger.valueOf(k)))).add(BigInteger.ONE);
                    if (pLeft.compareTo(BigInteger.TWO.pow(sequenceT[m-1])) != 1) {
                        check = false;
                        enter = false;
                    } else {
                        check = true;
                        enter = true;
                    }
                }
                if (!checkIfIsPrime(n, k, pRight, pLeft)) {
                    k = k + 2;
                    check = true;
                }
            } while (!checkIfIsPrime(n, k, pRight, pLeft));
            m = m - 1;
            pRight = pLeft;
        }while (m >= 1);

        System.out.println(pLeft);
        if(pLeft.isProbablePrime(1))
            System.out.println("is prime");
    }
}


/*          МОЖНО СДЕЛАТЬ UNSIGNED LONG
    Long l1 = Long.parseUnsignedLong("18446744073709551615");
    String l1Str = Long.toUnsignedString(l1);
    System.out.println(l1Str);

 */