package structs

import knr.annotations.*
import knr.runtime.typing.pointer.NativePointer

@Library("src/test/libraries/build/Debug/structlib", naming = NamingConvention.CAMELCASE)
interface StructTestLibrary {

    fun disposeStruct(@ByRef struct: TestStruct)

    @Method("disposeStruct")
    fun disposeStructPtr(ptr: NativePointer<TestStruct>)

    fun passByPointer(pointer: NativePointer<TestStruct>, value: Int)
    fun passByRef(@ByRef struct: TestStruct, value: Int)

    @ByRef
    fun returnByRef(value: Int): TestStruct

    @ByRef
    @Disposer(StructTestLibrary::class, "disposeStruct")
    @Method("returnByRef")
    fun returnStructWithDispose(value: Int): TestStruct

    @Disposer(StructTestLibrary::class, "disposeStructPtr")
    @Method("returnByRef")
    fun returnPtrWithDispose(value: Int): NativePointer<TestStruct>

    fun returnByValue(value: Int): TestStruct

    fun returnByPointer(value: Int): NativePointer<TestStruct>

    fun getStructInArray(index: Int): NativePointer<TestStruct>
    fun setStructInArray(index: Int, struct: TestStruct)

    fun setValueInArray(index: Int, value: Int)
}