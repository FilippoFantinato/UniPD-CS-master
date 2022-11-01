package atm.ATM.statechans.A.ioifaces;

import java.io.IOException;
import atm.ATM.*;
import atm.ATM.roles.*;
import atm.ATM.ops.*;

public interface Out_C_SendBankAddress_String<__Succ extends Succ_Out_C_SendBankAddress_String> {

	abstract public __Succ send(C role, SendBankAddress op, java.lang.String arg0) throws org.scribble.main.ScribRuntimeException, IOException;
}