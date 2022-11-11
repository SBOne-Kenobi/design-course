package generator.info

import generator.Characteristics

interface GenerationInfoWithCharacteristics : GenerationInfo {
    val characteristics: Characteristics
}