package ustudio.task.service.mapper

import ustudio.task.model.CountryLocalization
import ustudio.task.model.dto.CountryResponse

fun CountryLocalization.toResponse(): CountryResponse = CountryResponse(this.name!!, this.iso_code!!)
