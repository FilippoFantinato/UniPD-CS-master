package atm.ATM.statechans.A.ioifaces;

import atm.ATM.roles.*;
import atm.ATM.ops.*;
import atm.ATM.statechans.A.*;

public interface Receive_A_C_Res_Boolean<__Succ1 extends Succ_In_C_Res_Boolean> extends In_C_Res_Boolean<__Succ1>, Succ_Out_C_SendMoney_Int {
	public static final Receive_A_C_Res_Boolean<?> cast = null;

	abstract public __Succ1 async(C role, Res op) throws org.scribble.main.ScribRuntimeException;

	@Override
	default Receive_A_C_Res_Boolean<?> to(Receive_A_C_Res_Boolean<?> cast) {
		return (Receive_A_C_Res_Boolean<?>) this;
	}

	default ATM_A_4 to(ATM_A_4 cast) {
		return (ATM_A_4) this;
	}

	default ATM_A_7 to(ATM_A_7 cast) {
		return (ATM_A_7) this;
	}
}