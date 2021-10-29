package ba.unsa.etf.rpr.tutorijal02;


public class Interval {
    private double min;
    private double max;
    private boolean poc;
    private boolean kraj;

    public Interval(double min, double max, boolean poc, boolean kraj) throws IllegalArgumentException {
        if (min > max) throw new IllegalArgumentException();
        this.min = min;
        this.max = max;
        this.poc = poc;
        this.kraj = kraj;
    }
    public Interval(){
        this.min = 0;
        this.max = 0;
        this.poc = false;
        this.kraj = false;
    }
    public boolean isNull(){
        return min == 0 && max == 0 && !poc && !kraj;
    }
    public boolean isIn(double t){
        if (t==min && poc) return true;
        if (t==max && kraj) return true;
        if (t>min && t<max) return true;
        return false;
    }

    public Interval intersect(Interval i){

        if(i.max == max && i.min == min){
            boolean poctemp = false, krajtemp = false;
            if (poc && i.poc)
                poctemp = true;
            if (kraj && i.kraj)
                krajtemp = true;
            return new Interval(min, max, poctemp, krajtemp);
        }

        if (i.max > max && i.min < min){
            return new Interval(min, max, true, true);
        }
        if (max > i.max && min < i.min){
            return new Interval(i.min, i.max, true, true);
        }

        if(i.max == max){
            var krajtemp = (i.kraj && kraj);
            if(i.min < min)
                return new Interval(min, max, true, krajtemp);
            if(min < i.min)
                return new Interval(i.min, max, true, krajtemp);
        }

        if(i.min == min){
            var poctemp = (i.poc && poc);
            if(i.max > max)
                return new Interval(min, max, poctemp, true);
            if(max > i.max)
                return new Interval(min, i.max, poctemp, true);
        }

        if(i.min == max && i.max != min){
            if (!(i.poc && kraj))
                return new Interval();
            return new Interval(max, max,true, true);
        }
        if(min == i.max && max != i.min){
            if (!(poc && i.kraj))
                return new Interval();
            return new Interval(i.max, i.max,true, true);
        }

        //       *********  ja
        //   ******* on

        if (max > i.max && min > i.min){
            return new Interval(min, i.max, poc, i.kraj);
        }

        return new Interval(i.min, max, i.poc, kraj);


    }

    public static Interval intersect(Interval i1, Interval i2){
        return i1.intersect(i2);
    }


    public boolean equals(Interval i){
        return max == i.max && min == i.min && poc == i.poc && kraj == i.kraj;
    }

    public String toString(){
        if (isNull())
            return "()";
        var str = new StringBuilder();
        if (poc)
            str.append('[');
        else
            str.append('(');
        str.append(min);
        str.append(',');
        str.append(max);
        if (kraj)
            str.append(']');
        else
            str.append(')');
        return str.toString();
    }



}
