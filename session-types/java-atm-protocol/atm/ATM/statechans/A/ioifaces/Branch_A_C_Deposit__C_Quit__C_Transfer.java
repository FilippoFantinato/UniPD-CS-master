package atm.ATM.statechans.A.ioifaces;

import atm.ATM.roles.*;
import atm.ATM.statechans.A.*;

public interface Branch_A_C_Deposit__C_Quit__C_Transfer<__Succ1 extends Succ_In_C_Deposit, __Succ2 extends Succ_In_C_Quit, __Succ3 extends Succ_In_C_Transfer> extends Succ_Out_C_SendName_String, Succ_In_C_Res_Boolean {
	public static final Branch_A_C_Deposit__C_Quit__C_Transfer<?, ?, ?> cast = null;

	abstract Case_A_C_Deposit__C_Quit__C_Transfer<__Succ1, __Succ2, __Succ3> branch(C role) throws org.scribble.main.ScribRuntimeException, java.io.IOException, ClassNotFoundException;

	abstract void branch(C role, Handle_A_C_Deposit__C_Quit__C_Transfer<__Succ1, __Succ2, __Succ3> handler) throws org.scribble.main.ScribRuntimeException, java.io.IOException, ClassNotFoundException;

	abstract void handle(C role, Handle_A_C_Deposit__C_Quit__C_Transfer<Succ_In_C_Deposit, Succ_In_C_Quit, Succ_In_C_Transfer> handler) throws org.scribble.main.ScribRuntimeException, java.io.IOException, ClassNotFoundException;

	@Override
	default Branch_A_C_Deposit__C_Quit__C_Transfer<?, ?, ?> to(Branch_A_C_Deposit__C_Quit__C_Transfer<?, ?, ?> cast) {
		return (Branch_A_C_Deposit__C_Quit__C_Transfer<?, ?, ?>) this;
	}

	default ATM_A_2 to(ATM_A_2 cast) {
		return (ATM_A_2) this;
	}

public enum Branch_A_C_Deposit__C_Quit__C_Transfer_Enum implements org.scribble.runtime.session.OpEnum {
	Quit, Deposit, Transfer
}
}