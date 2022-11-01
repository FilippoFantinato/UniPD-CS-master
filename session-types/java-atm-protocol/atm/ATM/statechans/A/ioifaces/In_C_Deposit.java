package atm.ATM.statechans.A.ioifaces;

import java.io.IOException;
import atm.ATM.*;
import atm.ATM.roles.*;
import atm.ATM.ops.*;

public interface In_C_Deposit<__Succ extends Succ_In_C_Deposit> {

	abstract public __Succ receive(C role, Deposit op) throws org.scribble.main.ScribRuntimeException, java.io.IOException, ClassNotFoundException;
}