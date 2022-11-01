package atm.ATM.statechans.A.ioifaces;

public interface Succ_In_C_Res_Boolean {

	default Branch_A_C_Deposit__C_Quit__C_Transfer<?, ?, ?> to(Branch_A_C_Deposit__C_Quit__C_Transfer<?, ?, ?> cast) {
		return (Branch_A_C_Deposit__C_Quit__C_Transfer<?, ?, ?>) this;
	}
}