.assembly Customer
{
    .hash algorithm 0x00008004
    .ver 1:0:0:0
}
.assembly extern ATM {}
.class public Customer
{
    .field private string 'name'
    .field private class [ATM]ATM 'atm'
    .method public hidebysig specialname rtspecialname instance default void '.ctor' () cil managed
    {
        .maxstack 1
        ldarg.0
        call instance void object::'.ctor'()
        ret
    }
    .method public hidebysig instance void 'deposit'(int32 'money') cil managed
    {
        .maxstack 8
        .locals(int32 'newBalance', int32 'money')
        ldarg.0
        ldfld class [ATM]ATM Customer::'atm'
        ldarg.1
        call instance bool class [ATM]ATM::'canIDeposit'(int32 'money')
        brfalse IfFalse0
    IfTrue0:
        ldstr "Depositing " 
        call void class [mscorlib]System.Console::Write(string 'printStr')
        ldarg.1
        call void class [mscorlib]System.Console::Write(int32 'printInt')
        ldstr "\n" 
        call void class [mscorlib]System.Console::Write(string 'printStr')
        ldarg.0
        ldfld class [ATM]ATM Customer::'atm'
        ldarg.1
        call instance int32 class [ATM]ATM::'deposit'(int32 'money')
        stloc.0
        ldstr "My new balance is: " 
        call void class [mscorlib]System.Console::Write(string 'printStr')
        ldloc.0
        call void class [mscorlib]System.Console::Write(int32 'printInt')
        ldstr "\n" 
        call void class [mscorlib]System.Console::Write(string 'printStr')
        br EndIf0
    IfFalse0:
        ldstr "Error: the amout of money is negative or equal to 0\n" 
        call void class [mscorlib]System.Console::Write(string 'printStr')
    EndIf0:
        ret
    }
    .method public hidebysig instance void 'init'(string 'n') cil managed
    {
        .maxstack 8
        .locals(string 'n')
        ldarg.0
        newobj instance void class [ATM]ATM::'.ctor'()
        stfld class [ATM]ATM Customer::'atm'
        ldarg.0
        ldfld class [ATM]ATM Customer::'atm'
        call instance void class [ATM]ATM::'init'()
        ldarg.0
        ldarg.1
        stfld string Customer::'name'
        ret
    }
}
