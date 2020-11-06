package ru.innopolis.university.stc27.helpers;

public class Sum {

    String name;

    Double Sum;

    public Sum() {
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public Double getSum() {
        return Sum;
    }

    public void setSum(Double sum) {
        Sum = sum;
    }

    @Override
    public String toString() {
        return "Sum{" +
                "name='" + name + '\'' +
                ", Sum=" + Sum +
                '}';
    }
}
