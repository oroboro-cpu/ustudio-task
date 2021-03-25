package ustudio.task.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class CountryLocalization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long? = null
    @Column(name = "name")
    var name: String? = null

    override fun toString(): String {
        return "Country(id=$id, name='$name')"
    }
}
