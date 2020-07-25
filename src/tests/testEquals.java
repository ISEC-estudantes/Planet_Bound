package tests;

import java.util.Objects;

public class testEquals {
    static class Numero{
        private int numero;
        Numero(int num){
            numero = num;
        }

        public int getNum(){
            return numero;
        }

    }


    public static void main(String[] args) {
        Numero num = new Numero(5);
        Numero num2 = num;
        System.out.println("num == num2: "+ String.valueOf(num == num2));
        System.out.println("num.equals(num2): "+ num.equals(num2));
        System.out.println("this hash"+ Objects.hash(num));
        System.out.println(num.hashCode());
        try{
            return;
        }finally {
            System.out.println("cona");
        }
    }


}
