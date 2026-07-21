import java.time.LocalDateTime;

/**
 *
 * A single deposit or withdrawal recorded against an account, used to
 * build transaction history / reports.
 */
public class Transaction {

    public enum Type {
        DEPOSIT, WITHDRAW
    }

    private final Type type;
    private final double amount;
    private final LocalDateTime date;
    private final String note;

    public Transaction(Type type, double amount, String note) {
        this.type = type;
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.note = note;
    }

    public Type getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getNote() {
        return note;
    }

    @Override
    public String toString() {
        String formatted = date + " | " + type + " | $" + String.format("%.2f", amount);
        return (note == null || note.isEmpty()) ? formatted : formatted + " | " + note;
    }

}
