package ustudio.task.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn

@Entity(name = "country")
class CountryLocalization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null

    @Column(name = "name")
    var name: String? = null

    @JoinColumn(name = "iso_code")
    var iso_code: String? = null

    override fun toString(): String {
        return "Country(id=$id, name='$name', iso_code='$iso_code')"
    }
}
