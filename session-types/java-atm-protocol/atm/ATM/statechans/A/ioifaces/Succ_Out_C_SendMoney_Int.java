package atm.ATM.statechans.A.ioifaces;

public interface Succ_Out_C_SendMoney_Int {

	default Receive_A_C_Res_Boolean<?> to(Receive_A_C_Res_Boolean<?> cast) {
		return (Receive_A_C_Res_Boolean<?>) this;
	}
}