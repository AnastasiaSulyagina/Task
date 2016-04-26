package com.sulyagina.intern_task;

import java.io.InputStreamReader;
import java.util.*;
import java.io.BufferedReader;

public class ATM {
    private Integer total;
    private final List<Integer> values = Arrays.asList(5000, 1000, 500, 100, 50, 25, 10, 5, 3, 1);
    private Map<Integer, Integer> amounts = new HashMap<>();
    private Map<Integer, Integer> best = new HashMap<>();
    private Integer minLeft = 0;

    public ATM() {
        total = 0;
        for (Integer v : values) {
            amounts.put(v, 0);
            best.put(v, 0);
        }
    }

    public void put(Integer d, Integer count) {
        try {
            if (!values.contains(d)) {
                throw new Exception(String.format("Nominal %d is not accepted.", d));
            }
            if (count < 0) {
                throw new Exception("Negative values not accepted.");
            }
            total += count * d;
            amounts.put(d, amounts.get(d) + count);
            System.out.println(String.format("Total %d", total));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void findOptimalNominals(int curInd, int sumLeft, HashMap<Integer, Integer> used) {
        int current = values.get(curInd);

        for (int i = Math.min(amounts.get(current), sumLeft / current); i >= 0; --i) {
            Integer currentLeft = sumLeft - i * current;
            used.put(current, i);
            if (curInd + 1 < values.size()) {
                findOptimalNominals(curInd + 1, currentLeft, used);
            } else {
                if (currentLeft < minLeft) {
                    best.putAll(used);
                    minLeft = currentLeft;
                }
            }
        }
    }

    public void get(Integer amount) {
        minLeft = amount;
        best.clear();
        StringBuilder s = new StringBuilder();
        findOptimalNominals(0, amount, new HashMap<>());

        for (Integer v : values) {
            if (best.get(v) != null && best.get(v) != 0) {
                total -= best.get(v) * v;
                amounts.put(v, amounts.get(v) - best.get(v));
                s.append(String.format("%d=%d,", v, best.get(v)));
            }
        }
        s.append(String.format("Total %d", amount - minLeft));
        if (minLeft != 0) {
            s.append(String.format("\nLeft %d", minLeft));
        }
        System.out.println(s);
    }

    public void state() {
        System.out.println(String.format("Total %d", total));
    }

    public void dump() {
        StringBuilder s = new StringBuilder();
        for (Integer v : values) {
            s.append(String.format("%d=%d\n", v, amounts.get(v)));
        }
        System.out.print(s);
    }

    public static void main(String[] args) {
        ATM atm = new ATM();
        try {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine().toLowerCase();

            while (!input.equals("quit")) {
                String[] arguments = input.split(" ");
                switch (arguments[0]) {
                    case "put":
                        atm.put(Integer.valueOf(arguments[1]), Integer.valueOf(arguments[2]));
                        break;
                    case "get":
                        atm.get(Integer.valueOf(arguments[1]));
                        break;
                    case "state":
                        atm.state();
                        break;
                    case "dump":
                        atm.dump();
                        break;
                    default:
                        System.out.println("Invalid input. Use 'state', 'put', 'dump' or 'get'.");
                }
                input = br.readLine().toLowerCase();
            }
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }
}
