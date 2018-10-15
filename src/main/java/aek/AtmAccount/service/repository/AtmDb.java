package aek.AtmAccount.service.repository;

import aek.AtmAccount.domain.Banknote;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * The class that is representative of atm with certain amount of banknotes.
 *
 * @author Atila Ekimci
 */
@Repository
public class AtmDb {
    public AtmDb() {
        banknotes = fillUpBankAtm();
    }

    private Map<Integer, Banknote> banknotes = new HashMap<>();

    public Map<Integer, Banknote> getBanknotes() {
        return banknotes;
    }

    public void updateAtmBanknotes(final int type, final int amount) {
        Banknote banknote = getBanknotes().get(type);
        getBanknotes().put(type, new Banknote(type, banknote.getAmount() + amount));
    }

    /**
     * Fills up atm to maximum level by constructing a map.
     * Key is the banknote type as @{@link Integer}.
     * Value is @{@link Banknote}
     *
     * @return a map of banknotes.
     * @author Atila Ekimci
     */
    public static Map<Integer, Banknote> fillUpBankAtm() {
        return new HashMap<Integer, Banknote>() {
            {
                put(5, new Banknote(5, 5));
                put(10, new Banknote(10, 5));
                put(20, new Banknote(20, 5));
                put(50, new Banknote(50, 5));
            }
        };
    }

    @Override
    public String toString() {
        return Arrays.asList(banknotes.values()).toString();
    }
}
