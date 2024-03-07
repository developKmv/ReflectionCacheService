package ru.develop.entity;

import lombok.extern.slf4j.Slf4j;
import ru.develop.my.annotations.Cache;
import ru.develop.my.annotations.Mutator;
@Slf4j
public class Fraction implements Fractionable{
    private int num;
    private int denum;

    public Fraction(int num,int denum){
        this.num = num;
        this.denum = denum;
    }

    @Override
    @Mutator
    public void setNum(int num) {
        if (num==0)throw new IllegalArgumentException();
        this.num = num;
    }

    @Override
    @Mutator
    public void setDenum(int denum) {
        this.denum = denum;
    }
    @Override
    @Cache
    public double doubleValue() {
        log.info("invoke");
        System.out.println("i am original method");
        double returnVal = (double) num/denum;
        log.info(String.format("my value: %f \n",returnVal));
        return returnVal;
    }
}
