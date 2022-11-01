package atm.ATM.statechans.A.ioifaces;

import java.io.IOException;
import atm.ATM.*;
import atm.ATM.roles.*;
import atm.ATM.ops.*;

public interface Out_C_SendMoney_Int<__Succ extends Succ_Out_C_SendMoney_Int> {

	abstract public __Succ send(C role, SendMoney op, java.lang.Integer arg0) throws org.scribble.main.ScribRuntimeException, IOException;
}