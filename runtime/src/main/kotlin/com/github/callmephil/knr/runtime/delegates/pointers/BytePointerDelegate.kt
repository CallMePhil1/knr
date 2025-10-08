package com.github.callmephil.knr.runtime.delegates.pointers

import com.github.callmephil.knr.runtime.delegates.NullablePointerFieldDelegate
import com.github.callmephil.knr.runtime.delegates.PointerFieldDelegate
import com.github.callmephil.knr.runtime.memory.ARC
import com.github.callmephil.knr.runtime.typing.pointer.BytePointer
import com.github.callmephil.knr.runtime.typing.pointer.NullableBytePointer
import com.github.callmephil.knr.runtime.typing.pointer.NullableUBytePointer
import com.github.callmephil.knr.runtime.typing.pointer.UBytePointer
import com.github.callmephil.knr.runtime.typing.pointer.bytePointerOf
import com.github.callmephil.knr.runtime.typing.pointer.nullableBytePointerOf
import com.github.callmephil.knr.runtime.typing.pointer.nullableUBytePointerOf
import com.github.callmephil.knr.runtime.typing.pointer.ubytePointerOf

class BytePointerDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    initialValue: Byte
) : PointerFieldDelegate<Byte, BytePointer>(ownerArc, offset) {

    override var pointer: BytePointer = bytePointerOf(initialValue, onArcUpdate = ::updateOwnersArc)

    init {
        updateOwnersArc()
    }
}

class NullableBytePointerDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    initialValue: Byte?
) : NullablePointerFieldDelegate<Byte, NullableBytePointer>(ownerArc, offset) {

    override var pointer: NullableBytePointer = nullableBytePointerOf(initialValue, ::updateOwnersArc)

    init {
        updateOwnersArc()
    }
}

class UBytePointerDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    initialValue: UByte
) : PointerFieldDelegate<UByte, UBytePointer>(ownerArc, offset) {

    override var pointer: UBytePointer = ubytePointerOf(initialValue, onArcUpdate = ::updateOwnersArc)

    init {
        updateOwnersArc()
    }
}

class NullableUBytePointerDelegate internal constructor(
    ownerArc: ARC,
    offset: Long,
    initialValue: UByte?
) : NullablePointerFieldDelegate<UByte, NullableUBytePointer>(ownerArc, offset) {

    override var pointer: NullableUBytePointer = nullableUBytePointerOf(initialValue, ::updateOwnersArc)

    init {
        updateOwnersArc()
    }
}