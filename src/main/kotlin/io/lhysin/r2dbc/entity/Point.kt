package io.lhysin.r2dbc.entity

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.ReadOnlyProperty
import org.springframework.data.domain.Persistable
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal


@Table(name="POINT", schema = "ADM")
class Point(
    @Id
    @Column("ID")
    @JvmField
    val id: Long? = null,

    @Column("CUST_NO")
    val custNo: String,

    @Column("AMOUNT")
    val amount: BigDecimal,

    @ReadOnlyProperty
    private var insertable: Boolean = false

)  : Persistable<Long> {
    override fun getId(): Long? {
        return this.id
    }

    override fun isNew(): Boolean {
        return this.insertable || this.id == null
    }

    fun insertable(): Point{
        this.insertable = true
        return this
    }

}