package ru.develop.entity;

import ru.develop.my.annotations.Cache;
import ru.develop.my.annotations.Mutator;

public interface Fractionable {
    @Cache
    double doubleValue();
    @Mutator
    void setNum(int num);
    @Mutator
    void setDenum(int denum);
}
