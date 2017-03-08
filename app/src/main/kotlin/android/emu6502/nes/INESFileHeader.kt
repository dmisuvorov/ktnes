package android.emu6502.nes

import android.emu6502.nes.INESFileParser.Companion.INES_FILE_MAGIC
import android.emu6502.nes.INESFileParser.Companion.PADDING
import java.util.*

// http://wiki.nesdev.com/w/index.php/INES
// Sample header:
// 4e  45  53  1a  10  10  40  00  00  00  00  00  00  00  00  00
data class INESFileHeader(
    // @formatter:off
    val magic: ByteArray,   // Constant $4E $45 $53 $1A ("NES" followed by MS-DOS end-of-file)
    val numPRG: Byte,       // Size of PRG ROM in 16 KB units
    val numCHR: Byte,       // Size of CHR ROM in 8 KB units (Value 0 means the board uses CHR RAM)
    val control1: Byte,     // Flags 6
    val control2: Byte,     // Flags 7
    val numRAM: Byte,       // Size of PRG RAM in 8 KB units
    val padding: ByteArray // 7 bytes, unused
    // @formatter:on
) {
  fun isValid() =
      Arrays.equals(magic, INES_FILE_MAGIC) && Arrays.equals(padding, PADDING)

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other?.javaClass != javaClass) return false

    other as INESFileHeader

    if (!Arrays.equals(magic, other.magic)) return false
    if (numPRG != other.numPRG) return false
    if (numCHR != other.numCHR) return false
    if (control1 != other.control1) return false
    if (control2 != other.control2) return false
    if (numRAM != other.numRAM) return false
    if (!Arrays.equals(padding, other.padding)) return false

    return true
  }

  override fun hashCode(): Int {
    var result = Arrays.hashCode(magic)
    result = 31 * result + numPRG
    result = 31 * result + numCHR
    result = 31 * result + control1
    result = 31 * result + control2
    result = 31 * result + numRAM
    result = 31 * result + Arrays.hashCode(padding)
    return result
  }
}