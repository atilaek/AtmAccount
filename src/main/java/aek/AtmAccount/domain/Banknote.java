package aek.AtmAccount.domain;

/**
 * Banknote DAO represents a block of banknotes for a single banknote type.
 *
 * @author Atila Ekimci
 */
public class Banknote {

    private int type;
    private int amount;

    public Banknote(int type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(type+"="+amount)
                .toString();
    }
}
