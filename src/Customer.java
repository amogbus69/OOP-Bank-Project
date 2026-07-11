public class Customer extends Person {

    private Saving saving;
    private Checking checking;
    private Credit credit;

    private int creditScore;

    public Customer(int id,
                    String first,
                    String last,
                    String dob,
                    String address,
                    String phone,
                    String username,
                    String password,
                    int creditScore,
                    Saving saving,
                    Checking checking,
                    Credit credit) {

        super(id,first,last,dob,address,
                phone,username,password,"Customer");

        this.creditScore = creditScore;
        this.saving = saving;
        this.checking = checking;
        this.credit = credit;

    }

    public Saving getSaving(){

        return saving;

    }

    public Checking getChecking(){

        return checking;

    }

    public Credit getCredit(){

        return credit;

    }

}
