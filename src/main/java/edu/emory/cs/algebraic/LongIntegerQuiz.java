package edu.emory.cs.algebraic;

import java.util.Arrays;

public class LongIntegerQuiz extends LongInteger {
    public LongIntegerQuiz(LongInteger n) { super(n); }

    public LongIntegerQuiz(String n) { super(n); }

    @Override
    protected void addDifferentSign(LongInteger n) {
        int m = Math.max(digits.length, n.digits.length);
        byte[] result = new byte[m];

        if (this.compareAbs(n) == 0) {
            digits = new byte[] {0};
        } else {
            if (this.compareAbs(n) > 0) {
                System.arraycopy(digits, 0, result, 0, digits.length);
                if (sign == Sign.NEGATIVE) { sign = Sign.NEGATIVE; }
                else { sign = Sign.POSITIVE; }
                // subtract n from result
                for (int i = 0; i < m; i++) {
                    if (i < n.digits.length)
                        result[i] -= n.digits[i];
                    if (result[i] < 0) {
                        result[i] += 10;
                        result[i + 1] -= 1;
                    }
                }
            } else if (this.compareAbs(n) < 0) {
                System.arraycopy(n.digits, 0, result, 0, n.digits.length);
                if (sign == Sign.NEGATIVE) { sign = Sign.POSITIVE; }
                else { sign = Sign.NEGATIVE; }
                // subtract from n to result
                for (int i = 0; i < m; i++) {
                    if (i < digits.length)
                        result[i] = (byte)(n.digits[i] - digits[i]);
                    if (result[i] < 0) {
                        result[i] += 10;
                        result[i + 1] -= 1;
                    }
                }
            }
            // set this.digits
            int i = m - 1;
            while (i > 0 && result[i] == 0) i--;
            digits = Arrays.copyOfRange(result, 0, i + 1);
        }
    }
}
