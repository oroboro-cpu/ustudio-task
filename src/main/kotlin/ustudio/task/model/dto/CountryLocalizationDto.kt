package ustudio.task.model.dto

class CountryLocalizationDto {
    private var name: String? = null

    fun setName(name: String?) {
        this.name = name
    }

    fun getName(): String? {
        return name
    }
}