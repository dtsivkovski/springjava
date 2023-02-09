package com.nighthawk.spring_portfolio.mvc.biology.hwcalc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

import javax.persistence.*;

@Data  // Annotations to simplify writing code (ie constructors, setters)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HWObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String owner;
    private String popname;

    private double recent_pval;
    private double recent_qval;

    private boolean isHardyWeinberg;

    private ArrayList<String> history = new ArrayList<String>();

    HWObject(String owner, String popname, double pval, double qval) {
        this.owner = owner;
        this.popname = popname;
        this.recent_pval = pval;
        this.recent_qval = qval;

        calculateIfHW(pval, qval);
    }

    public void calculateIfHW(double pval, double qval) {
        //assume false unless both conditions are met
        history.add("pval: " + pval + " qval: " + qval);

        // System.out.println("" + this.current_pval + pval + this.current_qval + qval);
        if (this.recent_pval != pval || this.recent_qval != qval) {
            this.isHardyWeinberg = false;
            this.recent_pval = pval;
            this.recent_qval = qval;
            return;
        }

        if (pval + qval == 1) {
            if ((pval * pval) + (2 * pval * qval) + (qval * qval) == 1.00) {
                isHardyWeinberg = true;
            }
        } 

        return;

    }

    public static void main(String[] args) {
        // System.out.println(hw.isHardyWeinberg);

        HWObject hw2 = new HWObject("123901239031923013man", "lizards", 0.6, 0.4);
        System.out.println(hw2);
        hw2.calculateIfHW(0.5, 0.5);
        System.out.println(hw2);
        hw2.calculateIfHW(0.5, 0.5);
        System.out.println(hw2);

        // System.out.println(hw3.isHardyWeinberg);
    }

    
}
