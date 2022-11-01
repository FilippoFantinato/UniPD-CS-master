.assembly Main
{
    .hash algorithm 0x00008004
    .ver 1:0:0:0
}
.assembly extern Customer {}
.class public Main
{
    .method public hidebysig specialname rtspecialname instance default void '.ctor' () cil managed
    {
        .maxstack 1
        ldarg.0
        call instance void object::'.ctor'()
        ret
    }
    .method public hidebysig instance void 'main'() cil managed
    {
        .maxstack 8
        .locals(class [Customer]Customer 'filippo')
        newobj instance void class [Customer]Customer::'.ctor'()
        stloc.0
        ldloc.0
        ldstr "Filippo" 
        call instance void class [Customer]Customer::'init'(string 'n')
        ldloc.0
        ldc.i4 100
        call instance void class [Customer]Customer::'deposit'(int32 'money')
        ret
    }
    .method public static hidebysig default void start_0 () cil managed
    {
        .maxstack 8
        .entrypoint
        .locals init (object)
        newobj instance void Main::.ctor()
        call instance void class Main::'main'()
        ret
    }
}
