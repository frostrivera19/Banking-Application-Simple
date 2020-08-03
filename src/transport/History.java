package transport;

import java.util.ArrayList;

public class History {
    private final ArrayList<Operation> ops;
    private final ArrayList<Double> transacted;
    private int firstIndex, availableIndex;

    public History() {
        ops = new ArrayList<>();
        transacted = new ArrayList<>();
        firstIndex = 0;
        availableIndex = 0;
    }

    void insert(Operation op, double amt) {
        if (ops.size() == Constants.HISTORY_LIMIT) {
            ops.remove(firstIndex);
            transacted.remove(firstIndex);
            firstIndex += 1;
            firstIndex %= Constants.HISTORY_LIMIT;
        }

        ops.add(availableIndex, op);
        transacted.add(availableIndex, amt);

        availableIndex += 1;
        availableIndex %= Constants.HISTORY_LIMIT;

    }

    String listAllHistory() {
        if (ops.size() == 0) {
            return "History is clear.\n";
        }

        StringBuilder history = new StringBuilder();

        if (availableIndex > firstIndex) {
            for (int i = firstIndex; i < availableIndex; i++) {
                history.append(ops.get(i).toString())
                        .append(" ")
                        .append(transacted.get(i).toString()).append("\n");
            }
        } else {
            for (int i = firstIndex; i < Constants.HISTORY_LIMIT; i++) {
                history.append(ops.get(i).toString())
                        .append(" ")
                        .append(transacted.get(i).toString()).append("\n");
            }

            for (int i = 0; i < firstIndex; i++) {
                history.append(ops.get(i).toString())
                        .append(" ")
                        .append(transacted.get(i).toString()).append("\n");
            }
        }

        return history.toString();
    }

    String getLatestTransaction() {
        int lastIndex = (availableIndex + Constants.HISTORY_LIMIT - 1)
                % Constants.HISTORY_LIMIT;
        return ops.get(lastIndex) + " " +
                transacted.get(lastIndex) + "\n";
    }

}
