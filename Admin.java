public class Admin extends Person{

    public Admin(int id,
                 String first,
                 String last,
                 String dob,
                 String address,
                 String phone,
                 String username,
                 String password){

        super(id,first,last,dob,address,
                phone,username,password,"Admin");

    }

}