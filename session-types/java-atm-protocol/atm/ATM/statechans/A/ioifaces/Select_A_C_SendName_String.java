package atm.ATM.statechans.A.ioifaces;

import atm.ATM.roles.*;
import atm.ATM.statechans.A.*;

public interface Select_A_C_SendName_String<__Succ1 extends Succ_Out_C_SendName_String> extends Out_C_SendName_String<__Succ1> {
	public static final Select_A_C_SendName_String<?> cast = null;

	default ATM_A_1 to(ATM_A_1 cast) {
		return (ATM_A_1) this;
	}
}