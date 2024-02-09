package _2threadcordination;

import java.math.BigInteger;

public class ComplexCalculation {
    public static void main(String[] args) {
        BigInteger base1 = new BigInteger("10");
        BigInteger power1=new BigInteger("2");
        BigInteger base2=new BigInteger("5");
        BigInteger power2=new BigInteger("3");

        ComplexCalculation calculation = new ComplexCalculation();
        BigInteger result = calculation.calculateResult(base1,power1,base2,power2);
        System.out.println("Result: "+result);
    }
    public BigInteger calculateResult(BigInteger base1,
                                      BigInteger power1,
                                      BigInteger base2,
                                      BigInteger power2){
        BigInteger result = BigInteger.ZERO;
        PowerCalculatingThread thread1 = new PowerCalculatingThread(base1,power1);
        PowerCalculatingThread thread2 = new PowerCalculatingThread(base2,power2);

        thread1.start();
        thread2.start();

        try{
            thread1.join();
            thread2.join();
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        result = result.add(thread1.getResult());
        result = result.add(thread2.getResult());

        return result;
    }

    private static class PowerCalculatingThread extends Thread{
        private BigInteger result = BigInteger.ONE;
        private final BigInteger base;
        private final BigInteger power;

        private PowerCalculatingThread(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run(){
            this.result = pow(base,power);
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;
            for(BigInteger i=BigInteger.ZERO;
            i.compareTo(power)!=0;i=i.add(BigInteger.ONE)){
                result = result.multiply(base);
            }
            return result;
        }

        public BigInteger getResult(){
            return result;
        }
    }
}
